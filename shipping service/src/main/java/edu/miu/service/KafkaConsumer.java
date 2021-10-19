package edu.miu.service;

import com.google.gson.Gson;
import edu.miu.model.ResponseFormat;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class KafkaConsumer {
    @Autowired
    private MyService myService;

    @Autowired
    private KafkaProducer kafkaProducer;


    @KafkaListener(topics= "kafka_topic", groupId = "mygroup")
    public void getMessage(String message){
        Gson gson = new Gson();
        HashMap<String, Object> response = gson.fromJson(message, HashMap.class);
        Boolean isPaid = (Boolean) response.get("isPaid");
        UUID orderId = (UUID) response.get("orderId");
        if(isPaid) {
            ResponseFormat responseShipped = myService.processResponse(orderId);
        }

        System.out.println(response.get("isPaid"));


    }
}