package com.flz.rm.sb.pullconsumer.controller;

import com.flz.rm.sb.pullconsumer.service.ProducerService;
import com.flz.rm.sb.shared.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producers")
public class ProducerController {
    private final ProducerService producerService;

    @PostMapping("/produce/{message}")
    public Result send(@PathVariable String message) {
        producerService.send(message);
        return Result.of("success");
    }
}
