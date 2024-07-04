package com.ramadan.api.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodUnloadingUpdateDto {
        @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the GoodUnloading",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;
}
