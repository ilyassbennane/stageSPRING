package com.ramadan.api.dto.categorie.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ramadan.api.dto.company.model.CompanyResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.product.ProductCategory}
 */
@Data
@NoArgsConstructor
@Schema(name = "Categorie")
public class CategorieResponceDto {
	 @JsonProperty("uuid")
    @Schema(description = "uuid of the category updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6", required = true)
    String uuid;

	@JsonProperty("name")
	@Schema(description = "Name of the category", example = "clothing",required = true)
	private String name;

	@JsonProperty("code")
    @Schema(description = "Code of the category", example = "CAT001", required = true)
    private String code;

    
	@JsonProperty("description")
	@Schema(description = "Description of the category", example = "Electronics", required = true)
	private String description;

    
	 @JsonProperty("companyResponse")
	 @Schema(description = "Company ", example = "12345678-90ab-cdef-1234-567890abcdef", required = true)
     private CompanyResponseDto companyResponse;



}