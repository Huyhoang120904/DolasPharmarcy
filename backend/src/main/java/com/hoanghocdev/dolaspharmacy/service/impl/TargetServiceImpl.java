package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.TargetRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.TargetResponse;
import com.hoanghocdev.dolaspharmacy.entity.Target;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.TargetMapper;
import com.hoanghocdev.dolaspharmacy.repository.TargetRepository;
import com.hoanghocdev.dolaspharmacy.service.TargetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {
    TargetRepository targetRepository;
    TargetMapper targetMapper;

    @Override
    public void delete(String id) {
        if (!targetRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        targetRepository.deleteById(id);
    }

    @Override
    public TargetResponse create(TargetRequest request) {
        Target target = targetMapper.toTarget(request);
        Target savedTarget = targetRepository.save(target);
        return targetMapper.toResponse(savedTarget);
    }

    @Override
    public Page<TargetResponse> findByPage(Pageable pageable) {
        Page<Target> targetPage = targetRepository.findAll(pageable);
        return targetPage.map(targetMapper::toResponse);
    }

    @Override
    public TargetResponse update(String id, TargetRequest request) {
        Target target = targetRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        target.setTargetName(request.getTargetName());
        target.setDescription(request.getDescription());
        Target updatedTarget = targetRepository.save(target);
        return targetMapper.toResponse(updatedTarget);
    }

    @Override
    public TargetResponse findByID(String id) {
        Target target = targetRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return targetMapper.toResponse(target);
    }
}
