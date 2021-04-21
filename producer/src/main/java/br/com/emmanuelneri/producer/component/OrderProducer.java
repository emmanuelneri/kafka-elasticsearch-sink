package br.com.emmanuelneri.producer.component;

import br.com.emmanuelneri.producer.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${order.topic}")
    private String orderTopic;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public OrderProducer(final KafkaTemplate<Object, Object> kafkaTemplate, final ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(final OrderRequest order) {
        try {
            final String body = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(orderTopic, order.getIdentifier(), body).addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(final Throwable throwable) {
                    LOGGER.error("fail on send kafka message " + order.getIdentifier(), throwable);
                }

                @Override
                public void onSuccess(final SendResult<Object, Object> objectObjectSendResult) {
                    LOGGER.info(String.format("message %s sent to %s topic", order.getIdentifier(), orderTopic));
                }
            });
        } catch (Exception ex) {
            LOGGER.error("fail on convert order to string " + order.getIdentifier(), ex);
        }
    }

}

