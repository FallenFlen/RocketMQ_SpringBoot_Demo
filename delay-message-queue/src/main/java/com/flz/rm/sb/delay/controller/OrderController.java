package com.flz.rm.sb.delay.controller;

import com.flz.rm.sb.delay.service.OrderService;
import com.flz.rm.sb.shared.dto.CreateOrderRequestDTO;
import com.flz.rm.sb.shared.dto.OrderResponseDTO;
import com.flz.rm.sb.shared.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Result create(@Valid @RequestBody CreateOrderRequestDTO dto) {
        OrderResponseDTO responseDTO = orderService.create(dto);
        return Result.of("success", responseDTO);
    }
}
