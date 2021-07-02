package me.simran.service.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import me.simran.entity.models.EmployeePerformance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@Slf4j
public class ProducerServiceImpl {

    private final KafkaTemplate<String, EmployeePerformance> performanceKafkaTemplate;

    @Value("${kafka.topic.name}")
    private String ORDER_TOPIC;

    public ProducerServiceImpl(KafkaTemplate<String, EmployeePerformance> performanceKafkaTemplate) {
        this.performanceKafkaTemplate = performanceKafkaTemplate;
    }

    /**
     *
     * @param performance
     */
    public void sendPerformanceStats(EmployeePerformance performance) {
        log.info(String.format("$$$$ => Producing message: %s", performance));

        performanceKafkaTemplate.executeInTransaction(t -> {
            ListenableFuture<SendResult<String, EmployeePerformance>> future = t.send(ORDER_TOPIC, String.valueOf(performance.getEmpId()) + UUID.randomUUID(), performance);
            future.addCallback(new ListenableFutureCallback<SendResult<String, EmployeePerformance>>() {

                @Override
                public void onSuccess(SendResult<String, EmployeePerformance> result) {
                    log.info("Sent message=[ {} ] with offset=[ {} ]", performance, result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("Unable to produce message=[ {} ] due to : {}", performance, ex.getMessage());
                }
            });
            return true;
        });
    }
}
