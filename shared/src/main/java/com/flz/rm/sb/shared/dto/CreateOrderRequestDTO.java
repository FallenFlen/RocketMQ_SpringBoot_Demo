package com.flz.rm.sb.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {
    @NotBlank(message = "订单描述不能为空")
    private String description;
}
