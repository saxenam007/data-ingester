package com.kong.connect.logingester.service.consumer;

import com.kong.connect.logingester.repository.Ingester;
import com.kong.connect.logingester.utils.DataMapping;
import com.kong.connect.logingester.utils.Operations;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class EventProcessor {

    @Autowired
    private Ingester opensearchIngester;

    private int consumedMessageCount;
    public void processEvent(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
            consumedMessageCount++;
            // process Headers (if required)
            Map<String, String> inboundHeader = new HashMap<>();
            // Handle Compression if required
            consumerRecord.headers().forEach(header -> inboundHeader.put(header.key(), header.value().toString()));
            String value = consumerRecord.value();
            JSONObject record = new JSONObject(value);
            Boolean isValid = validateRecord(record);
            if(isValid){
                record = processRecord(record);
                opensearchIngester.ingestToDataStore(record, inboundHeader);
                acknowledgment.acknowledge();
            }
            // Do Validation handling here, plugin Exception Framework
            acknowledgment.acknowledge();
    }

    /*
    As per Debezium documentation operations is going to be an enum
    Can add more validations depending on the requirement
    or remove all if validations are not required
    */
    private Boolean validateRecord(JSONObject source) {
        String operation = source.get(DataMapping.OPERATION).toString();
        return isValidOperation(operation);
    }

    public boolean isValidOperation(String operation) {
        for (Operations entry : Operations.values()) {
            if (entry.getValue().equals(operation)) {
                return true;
            }
        }
        return false;
    }

    public JSONObject processRecord(JSONObject record) {
        /*
        JSON Data modelling for better search performance, it might require a bigger data set to correctly model it
        current modelling is done based on Debezium documentation and the provided data set
        Added a prefix to avoid duplication, these strategies will vary depending on the data set and API requirements
         */
        JSONObject flattenedJson = new JSONObject();
        flattenedJson.put(DataMapping.OPERATION, record.get(DataMapping.OPERATION));
        flattenedJson.put(DataMapping.TIME_MS, record.get(DataMapping.TIME_MS));
        JSONObject  after = record.getJSONObject(DataMapping.AFTER);
        flattenedJson.put(DataMapping.KEY, after.get(DataMapping.KEY));
        JSONObject  afterValue = after.getJSONObject(DataMapping.AFTER_VALUE);
        flattenedJson.put(DataMapping.TYPE, afterValue.get(DataMapping.TYPE));
        JSONObject  object = afterValue.getJSONObject(DataMapping.OBJECT);
        Set<String> objectKeys = object.keySet();
        for (String key : objectKeys){
            flattenedJson.put(DataMapping.OBJECT_PREFIX+key, object.get(key));
        }
        return flattenedJson;
    }


}
