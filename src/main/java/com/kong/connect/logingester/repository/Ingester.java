package com.kong.connect.logingester.repository;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.opensearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class Ingester {
    private final OpenSearchOperations openSearchOperations;

    @Autowired
    public Ingester(OpenSearchOperations openSearchOperations) {
        this.openSearchOperations = openSearchOperations;
    }
    public int count;

    public void ingestToDataStore(JSONObject content, Map<String, String> header) {
        /* TODO : A multi-tenenat service can write to multiple Indexes and index name can be implied from the inbound metadata
        For the current use cases index name and mapping is hardcoded
         */
        this.lookupOrcreateIndex();
        IndexResponse response;
        try {
            response = openSearchOperations.insertData(content);
            if (response.getResult().getLowercase().equals("created")) {
                this.count ++ ;
                log.debug("written data successfully to opensearch at shard {}", response.getShardId());
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //Crude validation, should have done this as functional test
        log.info("messages published to opensearch {}",  this.count);
    }


    public Boolean lookupOrcreateIndex() {
        if (openSearchOperations.getIndex()) {
            return true;
        } else {
            return openSearchOperations.createIndex();
        }
    }

}


