package com.estate.estate.dto;

import com.estate.estate.model.EstateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SearcEstate data transfer object
 */
@Data
@ApiModel(description = "Searc criteria for Estate search")
public class SearchEstateDto {

    private AdressDto adress;

    @ApiModelProperty(notes = "Building type based on which taxe size will be determined")
    private EstateType buildingType;

    @ApiModelProperty(notes = "Estate size in square meters")
    private Double size;
}
