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
@Schema(name = "categorieUpdate")
public class CategorieUpdateDto  {


    @JsonProperty("uuid")
    @Schema(description = "uuid of the catégorie updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    String uuid;

    @JsonProperty("name")
    @Schema(description = "Name of the category", example = "clothing",required = true)
    private String name;
    
    @JsonProperty("description")       
     @Schema(description = "Description of the category", example = "Electronics", required = true )
      private String description;



}