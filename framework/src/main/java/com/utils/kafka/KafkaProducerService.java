package com.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xs
 * @date 2022/1/14 14:27
 */
@Slf4j
@Service
public class KafkaProducerService {
    @Resource
    KafkaTemplate kafkaTemplate;

    public void kafkaSend() {
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("test", "test", "qwsdfgasdf");
            System.out.println("111111111111111111");
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
