package com.hoanghocdev.dolaspharmacy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageRequest {
    @NotBlank(message = "Image URL is required")
    @Pattern(
            regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$",
            message = "URL must be a valid image URL (jpg, jpeg, png, gif, webp)",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    @Size(max = 500, message = "URL cannot exceed 500 characters")
    String url;

    @Size(max = 255, message = "Alt text cannot exceed 255 characters")
    String alt;

    @JsonProperty(value = "isPrimary")
    boolean isPrimary;
}
