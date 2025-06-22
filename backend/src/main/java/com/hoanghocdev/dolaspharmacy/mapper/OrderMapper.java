package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.OrderCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.OrderUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.OrderResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        UserDetailMapper.class
})
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);
    OrderResponse toResponse(Order order);

    void updateOrder(OrderUpdateRequest request,@MappingTarget Order order);
}
