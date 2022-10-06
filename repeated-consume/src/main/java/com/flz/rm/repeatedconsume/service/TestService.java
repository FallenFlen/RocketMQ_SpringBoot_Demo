package com.flz.rm.repeatedconsume.service;

import com.flz.rm.repeatedconsume.dto.CreateTransactionRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final MqService mqService;

    public void createTransactionRecord(CreateTransactionRecordDTO dto) {
        mqService.createTransactionRecord(dto);
    }
}
