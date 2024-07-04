package com.ramadan.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DeviceResponseDto")
public class DeviceResponseDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "String",
        description = "ID of the device",
        example = "ghtrk8715C67GE73374RYTUZETDEUETE7",
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

    @JsonProperty("codeMarquePhone")
    @Schema(
        name = "codeMarquePhone",
        type = "string",
        description = "Code marque phone",
        example = "ABC123",
        required = true
    )
    private String codeMarquePhone;

    @JsonProperty("codeModulePhone")
    @Schema(
        name = "codeModulePhone",
        type = "string",
        description = "Code module phone",
        example = "XYZ789",
        required = true
    )
    private String codeModulePhone;

    @JsonProperty("cordova")
    @Schema(
        name = "cordova",
        type = "string",
        description = "Cordova version",
        example = "10.0.0",
        required = true
    )
    private String cordova;

    @JsonProperty("codePlateform")
    @Schema(
        name = "codePlateform",
        type = "string",
        description = "Code plateform",
        example = "Android",
        required = true
    )
    private String codePlateform;

    @JsonProperty("version")
    @Schema(
        name = "version",
        type = "string",
        description = "Version",
        example = "1.0",
        required = true
    )
    private String version;

    @JsonProperty("emei")
    @Schema(
        name = "emei",
        type = "string",
        description = "Emei",
        example = "123456789",
        required = true
    )
    private String emei;

    @JsonProperty("rang")
    @Schema(
        name = "rang",
        type = "integer",
        description = "Rang",
        example = "1",
        required = false
    )
    private Integer rang;

    @JsonProperty("name")
    @Schema(
        name = "name",
        type = "string",
        description = "Name of the brand",
        example = "Brand X",
        required = true
    )
    private String name;

    @JsonProperty("description")
    @Schema(
        name = "description",
        type = "string",
        description = "Description of the brand",
        example = "Description of Brand X",
        required = false
    )
    private String description;

    // Other fields if any
}
