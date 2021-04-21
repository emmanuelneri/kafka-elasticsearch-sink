# kafka-elasticsearch-sink

----

An example of use ElasticsearchSinkConnector to insert published records on Kafka topics into Elasticsearch.


To send all records from a topics without a schema definition, the connector can be created with the configuration below.
```
{
  "name": "elasticsearch-sink",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "orders",
    "key.ignore": "true",
    "type.name": "order",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "schema.ignore": "true",
    "connection.url": "http://elasticsearch:9200",
    "name": "elasticsearch-sink"
  }
}
```

For a large data volume, we can use transforms resources from Kafka Connect to split index by date as the example below.
```
{
  "name": "elasticsearch-sink",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "orders",
    "key.ignore": "true",
    "type.name": "order",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "schema.ignore": "true",
    "connection.url": "http://elasticsearch:9200",
    "name": "elasticsearch-sink",
    "transforms":"routeTS",
    "transforms.routeTS.type":"org.apache.kafka.connect.transforms.TimestampRouter",
    "transforms.routeTS.topic.format":"${topic}-${timestamp}",
    "transforms.routeTS.timestamp.format":"YYYYMM"
  }
}
```
It's also possible to create our own transforms as in the example below that use the [kafkaconnect-field-timestamp-router-transforms](https://github.com/emmanuelneri/kafkaconnect-field-timestamp-router-transforms) to customize target index to `topicName+fieldValue-timestamp` format.
```
{
  "name": "elasticsearch-sink",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "orders",
    "key.ignore": "true",
    "type.name": "order",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "schema.ignore": "true",
    "connection.url": "http://elasticsearch:9200",
    "name": "elasticsearch-sink",
    "transforms":"fieldTimestampRouter",
    "transforms.fieldTimestampRouter.type":"br.com.emmanuelneri.kafka.connect.smt.FieldTimestampRouter",
    "transforms.fieldTimestampRouter.topic.format":"${topic}-${field}-${timestamp}",
    "transforms.fieldTimestampRouter.timestamp.format":"YYYYMM",
    "transforms.fieldTimestampRouter.field.name":"type"
  }
}
```

## Running

#### requirements

- Maven 3+
- Java 16+
- Docker 18.02.0+ 

#### start environment

1. Start Kafka, Kafka Connect and Elasticsearch with docker compose `docker-compose up`
2. Deploy ElasticsearchSinkConnector `bash deploy-elasticsearch-connector.sh`
3. Publish messages in topic `bash publish.sh`
4. List events from Elasticsearch `bash list-events.sh`
