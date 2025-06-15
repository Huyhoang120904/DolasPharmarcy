package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.TargetRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.TargetResponse;
import com.hoanghocdev.dolaspharmacy.entity.Target;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TargetMapper {
    Target toTarget(TargetRequest request);
    TargetResponse toResponse(Target target);
}
