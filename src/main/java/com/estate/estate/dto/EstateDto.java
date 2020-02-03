package com.estate.estate.dto;

import com.estate.estate.model.Estate;
import com.estate.estate.model.EstateType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Estate data transfer object
 */
@Data
@ApiModel(description = "Estate properties wihch will be saved")
public class EstateDto {

    @ApiModelProperty(notes = "unique Estate id stored in database")
    private Long id;

    @ApiModelProperty(notes = "unique Estate adress where it is located")
    private AdressDto adress;

    @ApiModelProperty(notes = "Estate property owner name")
    private String owner;

    @ApiModelProperty(notes = "Estate size in square meters")
    private Double propertySize;

    @ApiModelProperty(notes = "Estate market value in EUR")
    private Integer marketValue;

    @ApiModelProperty(notes = "Estate type which used for yearly taxes calculation")
    private EstateType estateType;
}
