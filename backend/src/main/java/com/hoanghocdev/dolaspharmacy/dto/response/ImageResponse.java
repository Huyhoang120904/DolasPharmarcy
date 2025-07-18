package com.hoanghocdev.dolaspharmacy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    String id;
    String url;
    String alt;

    @JsonProperty(value = "isPrimary")
    boolean isPrimary;
}
