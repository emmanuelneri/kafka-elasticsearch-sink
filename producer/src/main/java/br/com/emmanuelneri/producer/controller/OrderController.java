package br.com.emmanuelneri.producer.controller;

import br.com.emmanuelneri.producer.OrderRequest;
import br.com.emmanuelneri.producer.component.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> send(@RequestBody OrderRequest order) {
        try {
            orderProducer.send(order);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            LOGGER.error("fail on send orderRequest", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}