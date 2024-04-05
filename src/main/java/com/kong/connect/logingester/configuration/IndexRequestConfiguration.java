package com.kong.connect.logingester.configuration;

import com.kong.connect.logingester.utils.Constants;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kong.connect.logingester.utils.Constants.*;

@Configuration
public class IndexRequestConfiguration {

    @Bean
    public CreateIndexRequest createIndexRequest(){

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);

        createIndexRequest.settings(Settings.builder() //Specify in the settings how many shards you want in the index.
                .put(Constants.NUMBER_OF_SHARDS_KEY, NUMBER_OF_SHARDS_VALUE)
                .put(Constants.NUMBER_OF_REPLICAS_KEY, NUMBER_OF_REPLICAS_VALUE)
                .put(Constants.NUMBER_OF_FIELDS_KEY, NUMBER_OF_FIELDS_VALUE)
        );
        createIndexRequest.mapping(INDEX_MAPPING, XContentType.JSON);
        return createIndexRequest;
    }

    @Bean
    public GetIndexRequest getIndexRequest(){
        return new GetIndexRequest(INDEX_NAME);
    }

    @Bean
    public IndexRequest insertDocuments(){
        return new IndexRequest(INDEX_NAME);
    }

}
