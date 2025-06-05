package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.OrderUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.OrderResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);
    OrderResponse toOrderResponse(Order order);

    void updateOrder(OrderUpdateRequest request,@MappingTarget Order order);
}
