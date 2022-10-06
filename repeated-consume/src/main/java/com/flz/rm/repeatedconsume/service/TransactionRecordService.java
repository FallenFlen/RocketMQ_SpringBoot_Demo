package com.flz.rm.repeatedconsume.service;

import com.flz.rm.repeatedconsume.dto.CreateTransactionRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRecordService {
    private final MqService mqService;

    public void create(CreateTransactionRecordDTO dto) {
        mqService.createTransactionRecord(dto);
    }
}
