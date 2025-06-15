package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TargetRequest {

    @NotBlank(message = "Target must not be blank")
    String targetName;

    String description;

}
