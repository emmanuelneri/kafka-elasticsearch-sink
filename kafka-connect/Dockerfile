FROM confluentinc/cp-kafka-connect-base

RUN confluent-hub install --no-prompt confluentinc/kafka-connect-elasticsearch:11.0.3
RUN mkdir /usr/share/java/kafkaconnect-field-timestamp-router-transforms
COPY kafkaconnect-field-timestamp-router-transforms-1.0.jar /usr/share/java/kafkaconnect-field-timestamp-router-transforms/kafkaconnect-field-timestamp-router-transforms-1.0.jar