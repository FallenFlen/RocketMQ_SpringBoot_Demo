package com.flz.rm.repeatedconsume.consumers;

import com.flz.rm.repeatedconsume.dto.CreateTransactionRecordDTO;
import com.flz.rm.repeatedconsume.entity.TransactionRecord;
import com.flz.rm.repeatedconsume.repository.TransactionRecordRepository;
import com.flz.rm.repeatedconsume.service.MqService;
import com.flz.rm.repeatedconsume.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConsumerTest {
    @Autowired
    private MqService mqService;
    @Autowired
    private TestService testService;
    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @BeforeEach
    void setup() {
        transactionRecordRepository.deleteAll();
    }

    @Test
    void should_save_transaction_record_failed_when_message_is_consumed_repeatedly() throws InterruptedException {
        transactionRecordRepository.save(TransactionRecord.builder()
                .deliveryLineId("test-abc")
                .amount(new BigDecimal("20"))
                .recordTime(LocalDateTime.now())
                .build());

        CreateTransactionRecordDTO dto = CreateTransactionRecordDTO.builder()
                .amount(new BigDecimal("20"))
                .deliveryLineId("test-abc")
                .build();
        CountDownLatch latch = new CountDownLatch(20);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                testService.createTransactionRecord(dto);
                latch.countDown();
            });
        }

        latch.await();

        assertEquals(1, transactionRecordRepository.countByDeliveryLineId("test-abc"));
    }

    @Test
    void should_receive_message() {
        mqService.sendTestMessage("mark-abc");
    }
}
