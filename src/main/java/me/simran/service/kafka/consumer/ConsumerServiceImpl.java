package me.simran.service.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import me.simran.entity.models.EmployeePerformance;
import me.simran.service.Employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerServiceImpl {

    public EmployeeService employeeService;

    @Autowired
    public ConsumerServiceImpl(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @KafkaListener(containerFactory = "jsonKafkaListenerContainerFactory",
            topics = "${kafka.topic.name}",
            groupId = "${kafka.topic.groupId}")
    public void consumeOrderData(@Header(KafkaHeaders.OFFSET) Long offset,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                 EmployeePerformance performance) {
        log.info("Consumed Order:{} for customerId {} from Partition {} at offset {}", key, performance.getEmpId(), partition, offset);
        //orderService.createOrder(order);
    }
}