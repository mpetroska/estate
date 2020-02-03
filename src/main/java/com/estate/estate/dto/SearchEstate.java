package com.estate.estate.dto;

import com.estate.estate.model.EstateType;
import lombok.Data;


/**
 * Estate custom search object
 */
@Data
public class SearchEstate {

    private AdressDto adress;
    private EstateType buildingType;
    private Double size;
    private String owner;
}
