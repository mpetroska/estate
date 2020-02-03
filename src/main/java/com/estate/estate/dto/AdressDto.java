package com.estate.estate.dto;

import com.estate.estate.model.Adress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Adress data transfer object
 */
@Data
@ApiModel(description = "Estate property unique adress")
public class AdressDto {
    @ApiModelProperty(notes = "city where Estate located")
    private String city;

    @ApiModelProperty(notes = "street where Estate is lovcated")
    private String street;

    @ApiModelProperty(notes = "Esteta number on street")
    private Integer number;
}