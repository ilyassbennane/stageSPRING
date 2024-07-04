package com.ramadan.api.dto.categorie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.product.ProductCategory}
 */

@Data
@NoArgsConstructor
public class CategoryChildDto {

    @JsonProperty("descrription")
    @Schema(description = "Description of the category", example = "Electronics",required = true)
    private String description;

    @JsonProperty("parentUuid")
    @Schema(description = "Parent UUID", example = "12345678-90ab-cdef-1234-567890abcdef",required = true)
     private String parentUuid;

    @JsonProperty("name")
    @Schema(description = "Name of the category", example = "clothing",required = true)
    private String name;

    @JsonProperty("companyUuid")
    @Schema(description = "Company UUID", example = "12345678-90ab-cdef-1234-567890abcdef",required = true)
    private String companyUuid;
}
