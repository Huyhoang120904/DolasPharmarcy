package com.hoanghocdev.dolaspharmacy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoanghocdev.dolaspharmacy.validation.ValidVietnamesePhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.MappingTarget;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {

    String name;
    String zipcode;

    @NotBlank(message = "Address must not be blank")
    @Size(max = 100, message = "Address must not exceed 100 characters")
    String address;

    @NotBlank(message = "Ward must not be blank")
    @Size(max = 100, message = "Ward must not exceed 100 characters")
    String ward;

    @NotBlank(message = "District must not be blank")
    @Size(max = 100, message = "District must not exceed 100 characters")
    String district;

    @NotBlank(message = "Province must not be blank")
    @Size(max = 100, message = "Province must not exceed 100 characters")
    String province;

    @NotBlank(message = "Phone number must not be blank")
    @ValidVietnamesePhone
    String phoneNumber;

    @JsonProperty("isPrimary")
    boolean primary;
}