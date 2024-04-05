package com.kong.connect.logingester.repository;

import org.json.JSONObject;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.kong.connect.logingester.utils.Constants.INDEX_NAME;

@Component
public class OpenSearchOperations {

    private final CreateIndexRequest createIndexRequest;

    private final GetIndexRequest getIndexRequest;

    private final RestHighLevelClient client;

    private final IndexRequest request ;
    private ConcurrentHashMap indexMap = new ConcurrentHashMap();

    @Autowired
    public OpenSearchOperations(RestHighLevelClient restHighLevelClient, GetIndexRequest getIndexRequest, CreateIndexRequest createIndexRequest, IndexRequest request){
        this.client = restHighLevelClient;
        this.getIndexRequest = getIndexRequest;
        this.createIndexRequest = createIndexRequest;
        this.request = request;
    }

    public Boolean createIndex(){
        Boolean indexCreated = false ;
        try{
            indexCreated =  this.client.indices().create(this.createIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
            indexMap.put(INDEX_NAME, indexCreated);
        }catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return indexCreated;
    }

    public Boolean getIndex(){
        Boolean indexExist = indexMap.containsKey(INDEX_NAME) ;
        if(indexExist){
            return indexExist;
        }
        try{
           indexExist =  this.client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
           indexMap.put(INDEX_NAME, indexExist);
        }catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return indexExist;
    }

    public IndexResponse insertData(JSONObject content) throws IOException{

        request.source(content, XContentType.JSON);
        return  client.index(request , RequestOptions.DEFAULT);

    }
}
