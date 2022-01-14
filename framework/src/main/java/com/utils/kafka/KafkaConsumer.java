//package com.utils.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class KafkaConsumer {
//    private static final String TEST = "test";
//    @KafkaListener(topics = {TEST}, groupId = "aaaaa")
//    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {
//        log.debug(record.topic() + " -- " + record.partition() + " -- " + record.value().toString());
//        ack.acknowledge();
//    }
//}
