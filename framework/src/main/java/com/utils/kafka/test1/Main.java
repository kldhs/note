package com.utils.kafka.test1;
public class Main {

    public static void main(String[] args) {
        String brokerList = "localhost:9092,localhost:9093,localhost:9094";
        String groupId = "group2";
        String topic = "test-topic";
        int workerNum = 5;

        ConsumerHandler consumers = new ConsumerHandler(brokerList, groupId, topic);
        consumers.execute(workerNum);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException ignored) {}
        consumers.shutdown();
    }
}