package com.flz.rm.sb.dead.service;

import com.flz.rm.sb.dead.repository.OrderRepository;
import com.flz.rm.sb.shared.convertor.OrderConvertor;
import com.flz.rm.sb.shared.dto.CreateOrderRequestDTO;
import com.flz.rm.sb.shared.dto.OrderResponseDTO;
import com.flz.rm.sb.shared.entity.Order;
import com.flz.rm.sb.shared.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MqService mqService;
    private OrderConvertor convertor = OrderConvertor.INSTANCE;

    public OrderResponseDTO create(CreateOrderRequestDTO dto) {
        Order order = Order.builder()
                .description(dto.getDescription())
                .status(OrderStatus.PENDING)
                .build();
        Order savedOrder = orderRepository.save(order);
        mqService.sendOrderCancelDelayMessage(savedOrder.getId());
        return convertor.toDTO(savedOrder);
    }
}
