package com.flz.rm.sb.pullconsumer.controller;

import com.flz.rm.sb.pullconsumer.service.ConsumerService;
import com.flz.rm.sb.shared.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consumers")
public class ConsumerController {
    private final ConsumerService consumerService;

    @GetMapping("/pull")
    public Result consume() {
        List<String> messages = consumerService.pull();
        return Result.of("success", messages);
    }
}
