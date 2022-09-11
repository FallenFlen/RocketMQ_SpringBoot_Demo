package com.flz.rm.sb.shared.convertor;

import com.flz.rm.sb.shared.dto.OrderResponseDTO;
import com.flz.rm.sb.shared.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderConvertor {
    OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

    OrderResponseDTO toDTO(Order order);
}
