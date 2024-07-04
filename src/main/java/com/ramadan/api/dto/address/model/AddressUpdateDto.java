package com.ramadan.api.dto.address.model;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressUpdateDto {
    String description;
    private String adresse1;
    private String adresse2;
    private String adresse3;
    String codeVille;
    
}
