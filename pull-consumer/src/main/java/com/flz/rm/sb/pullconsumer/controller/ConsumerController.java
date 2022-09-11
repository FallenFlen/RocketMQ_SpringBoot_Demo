package com.flz.rm.sb.pullconsumer.controller;

import com.flz.rm.sb.pullconsumer.service.ConsumerService;
import com.flz.rm.sb.shared.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumerController {
    private final ConsumerService consumerService;

    @GetMapping("/pull")
    public Result consume() {
        consumerService.pull();
        return Result.of("success");
    }
}
