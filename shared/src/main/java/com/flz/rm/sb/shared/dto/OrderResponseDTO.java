package com.flz.rm.sb.shared.dto;

import com.flz.rm.sb.shared.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private String id;
    private String description;
    private OrderStatus status;
}
