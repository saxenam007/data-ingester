package com.kong.connect.logingester.configuration;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.OpenSearchException;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;

@Configuration
public class RestHighLevelClientConfig extends AbstractFactoryBean<RestHighLevelClient> {

    @Value("admin")
    private String username;

    @Value("admin")
    private char[] password;

    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.port}")
    private String port;

    @Value("${opensearch.scheme}")
    private String scheme;

    @Value("${opensearch.connect-timeout}")
    private int connectionTimeout;

    @Value("${opensearch.socket-timeout}")
    private int socketTimeout;


    private RestHighLevelClient client = null;


    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    protected RestHighLevelClient createInstance() throws Exception {
        if(this.client == null){
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(this.username, new String(this.password)));

            this.client = new RestHighLevelClient(RestClient.builder(
                            new HttpHost(this.host, Integer.parseInt(this.port), this.scheme))
                    .setRequestConfigCallback(
                            requestConfigBuilder -> requestConfigBuilder
                                    .setConnectTimeout(connectionTimeout)
                                    .setSocketTimeout(socketTimeout)
                    ).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider)));
        }
        return this.client;
    }

    @Override
    public void destroy() throws OpenSearchException {
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            throw new OpenSearchException(
                    String.format("Failed to close Opensearch connection : %s", e.getMessage()),
                    e);
        }
    }

    public void cleanUp() {
        Arrays.fill(password, '0');
    }
}
