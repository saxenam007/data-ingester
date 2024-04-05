package com.kong.connect.logingester.utils;

public class Constants {

    public static final String NUMBER_OF_SHARDS_KEY = "index.number_of_shards";
    public static final String NUMBER_OF_REPLICAS_KEY = "index.number_of_replicas";
    public static final String NUMBER_OF_FIELDS_KEY = "index.mapping.total_fields.limit";

    public static final int NUMBER_OF_FIELDS_VALUE = 5000;
    public static final int NUMBER_OF_SHARDS_VALUE = 1 ;
    public static final int NUMBER_OF_REPLICAS_VALUE = 0;
    public static final String INDEX_NAME = "cdc-events-2";

    public static final String INDEX_MAPPING = "{\n" +
            "    \"properties\": {\n" +
            "      \"op\": { \"type\": \"keyword\" },\n" +
            "      \"ts_ms\": { \"type\": \"date\" },\n" +
            "      \"key\": { \"type\": \"keyword\" },\n" +
            "      \"type\": { \"type\": \"integer\" },\n" +
            "      \"obj_id\": { \"type\": \"keyword\" },\n" +
            "      \"obj_version\": { \"type\": \"keyword\" },\n" +
            "      \"obj_host\": { \"type\": \"keyword\" },\n" +
            "      \"obj_jwk\": { \"type\": \"text\" },\n" +
            "      \"obj_kid\": { \"type\": \"keyword\" },\n" +
            "      \"obj_set\": {\n" +
            "        \"properties\": {\n" +
            "          \"id\": { \"type\": \"keyword\" }\n" +
            "        }\n" +
            "      },\n" +
            "      \"obj_name\": { \"type\": \"keyword\" },\n" +
            "      \"obj_tags\": { \"type\": \"keyword\" },\n" +
            "      \"obj_created_at\": { \"type\": \"date\" },\n" +
            "      \"obj_updated_at\": { \"type\": \"date\" }\n" +
            "    }\n" +
            "  }" ;

    /*public static final String INDEX_MAPPING = "{\n" +
            "    \"properties\": {\n" +
            "        \"before\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "        },\n" +
            "        \"after\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "                \"key\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                },\n" +
            "                \"value\": {\n" +
            "                    \"type\": \"nested\",\n" +
            "                    \"properties\": {\n" +
            "                        \"type\": {\n" +
            "                            \"type\": \"integer\"\n" +
            "                        },\n" +
            "                        \"object\": {\n" +
            "                            \"type\": \"nested\",\n" +
            "                            \"properties\": {\n" +
            "                                \"id\": {\n" +
            "                                    \"type\": \"keyword\"\n" +
            "                                },\n" +
            "                                \"name\": {\n" +
            "                                    \"type\": \"text\"\n" +
            "                                },\n" +
            "                                \"slots\": {\n" +
            "                                    \"type\": \"integer\"\n" +
            "                                },\n" +
            "                                \"hash_on\": {\n" +
            "                                    \"type\": \"keyword\"\n" +
            "                                },\n" +
            "                                \"algorithm\": {\n" +
            "                                    \"type\": \"keyword\"\n" +
            "                                },\n" +
            "                                \"created_at\": {\n" +
            "                                    \"type\": \"date\",\n" +
            "                                    \"format\": \"epoch_second\"\n" +
            "                                },\n" +
            "                                \"updated_at\": {\n" +
            "                                    \"type\": \"date\",\n" +
            "                                    \"format\": \"epoch_second\"\n" +
            "                                },\n" +
            "                                \"healthchecks\": {\n" +
            "                                    \"type\": \"nested\",\n" +
            "                                    \"properties\": {\n" +
            "                                        \"active\": {\n" +
            "                                            \"type\": \"nested\",\n" +
            "                                            \"properties\": {\n" +
            "                                                \"type\": {\n" +
            "                                                    \"type\": \"keyword\"\n" +
            "                                                },\n" +
            "                                                \"healthy\": {\n" +
            "                                                    \"type\": \"nested\",\n" +
            "                                                    \"properties\": {\n" +
            "                                                        \"interval\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"successes\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_statuses\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "                                                },\n" +
            "                                                \"timeout\": {\n" +
            "                                                    \"type\": \"integer\"\n" +
            "                                                },\n" +
            "                                                \"http_path\": {\n" +
            "                                                    \"type\": \"text\"\n" +
            "                                                },\n" +
            "                                                \"unhealthy\": {\n" +
            "                                                    \"type\": \"nested\",\n" +
            "                                                    \"properties\": {\n" +
            "                                                        \"interval\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"timeouts\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"tcp_failures\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_failures\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_statuses\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "                                                },\n" +
            "                                                \"concurrency\": {\n" +
            "                                                    \"type\": \"integer\"\n" +
            "                                                },\n" +
            "                                                \"https_verify_certificate\": {\n" +
            "                                                    \"type\": \"boolean\"\n" +
            "                                                }\n" +
            "                                            }\n" +
            "                                        },\n" +
            "                                        \"passive\": {\n" +
            "                                            \"type\": \"nested\",\n" +
            "                                            \"properties\": {\n" +
            "                                                \"type\": {\n" +
            "                                                    \"type\": \"keyword\"\n" +
            "                                                },\n" +
            "                                                \"healthy\": {\n" +
            "                                                    \"type\": \"nested\",\n" +
            "                                                    \"properties\": {\n" +
            "                                                        \"successes\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_statuses\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "                                                },\n" +
            "                                                \"unhealthy\": {\n" +
            "                                                    \"type\": \"nested\",\n" +
            "                                                    \"properties\": {\n" +
            "                                                        \"timeouts\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"tcp_failures\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_failures\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        },\n" +
            "                                                        \"http_statuses\": {\n" +
            "                                                            \"type\": \"integer\"\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "                                                }\n" +
            "                                            }\n" +
            "                                        },\n" +
            "                                        \"threshold\": {\n" +
            "                                            \"type\": \"integer\"\n" +
            "                                        }\n" +
            "                                    }\n" +
            "                                },\n" +
            "                                \"use_srv_name\": {\n" +
            "                                    \"type\": \"boolean\"\n" +
            "                                },\n" +
            "                                \"hash_fallback\": {\n" +
            "                                    \"type\": \"keyword\"\n" +
            "                                },\n" +
            "                                \"hash_on_cookie_path\": {\n" +
            "                                    \"type\": \"text\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                \"op\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                },\n" +
            "                \"ts_ms\": {\n" +
            "                    \"type\": \"date\",\n" +
            "                    \"format\": \"epoch_millis\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";*/
}
