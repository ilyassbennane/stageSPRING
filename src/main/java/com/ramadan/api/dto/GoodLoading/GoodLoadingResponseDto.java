package com.ramadan.api.dto.GoodLoading;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GoodLoadingResponseDto")
public class GoodLoadingResponseDto {

    @JsonProperty("goodLoadingUuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the good loading",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

    @JsonProperty("companyUuid")
    @Schema(
        name = "companyUuid",
        type = "String",
        description = "ID of the company",
        example = "1getttttzehgrtgzetgzgetghiuxlarynaèoatgètoatzcz",
        required = true
    )
    private String companyUuid;

    @JsonProperty("userUuid")
    @Schema(
        name = "userUuid",
        type = "String",
        description = "ID of the user",
        example = "YT636U84UJ0PHP98HUIHIOBHIOBIUOBIOI7TV7VTUVJY",
        required = true
    )
    private String userUuid;;

    @JsonProperty("codeEtatGoodLoading")
    @Schema(
        name = "codeEtatGoodLoading",
        type = "string",
        description = "Code etat good loading",
        example = "XYZ123",
        required = true
    )
    private String codeEtatGoodLoading;

    @JsonProperty("codeGoodLoadingSystem")
    @Schema(
        name = "codeGoodLoadingSystem",
        type = "string",
        description = "Code good loading system",
        example = "ABC123",
        required = true
    )
    private String codeGoodLoadingSystem;

    @JsonProperty("codeGoodLoading")
    @Schema(
        name = "codeGoodLoading",
        type = "string",
        description = "Code good loading",
        example = "GL123",
        required = true
    )
    private String codeGoodLoading;

    @JsonProperty("gpsLatitude")
    @Schema(
        name = "gpsLatitude",
        type = "BigDecimal",
        description = "GPS latitude",
        example = "51.5074",
        required = false
    )
    private BigDecimal gpsLatitude;

    @JsonProperty("gpsLongitude")
    @Schema(
        name = "gpsLongitude",
        type = "BigDecimal",
        description = "GPS longitude",
        example = "-0.1278",
        required = false
    )
    private BigDecimal gpsLongitude;

    @JsonProperty("name")
    @Schema(
        name = "name",
        type = "string",
        description = "Name of the good loading",
        example = "Good Loading X",
        required = true
    )
    private String name;

    @JsonProperty("description")
    @Schema(
        name = "description",
        type = "string",
        description = "Description of the good loading",
        example = "Description of Good Loading X",
        required = false
    )
    private String description;

    // Other fields if any
}
