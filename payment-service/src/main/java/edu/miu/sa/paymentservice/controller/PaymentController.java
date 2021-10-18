package edu.miu.sa.paymentservice.controller;


import edu.miu.sa.paymentservice.dto.BasicResponse;
import edu.miu.sa.paymentservice.dto.PaymentDTO;
import edu.miu.sa.paymentservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private TransactionService service;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String KAFKA_TOPIC = "payment_response_tpc";
    //private static final String KAFKA_TOPIC = "TestingMsgs";

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void makePayment(@RequestBody PaymentDTO request) {
        BasicResponse response = service.makePayment(request);
        //publish message to kafka
        kafkaTemplate.send(KAFKA_TOPIC, response);
        System.out.println("Response message sent for order number: " + request.getOrderNumber());
    }

}
