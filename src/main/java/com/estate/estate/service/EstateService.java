package com.estate.estate.service;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;

import java.util.List;

/**
 *  Service to perform operation with Estate
 */
public interface EstateService {

    /**
     * Finds Estate by Adress
     */
    public EstateDto findEstateByAdress(AdressDto adress);

    /**
     * Saves received Estate object
     */
    public EstateDto saveEstate(EstateDto estateDto);

    /**
     * Updates received Estate object
     */
    public EstateDto updateEstate(EstateDto estateDto);

    /**
     * Deletes Estate object based on received adress
     */
    public String deleteEstate(AdressDto adressDto);

    /**
     * Calculates tax based on owner property stored in database
     */
    public Double calculateOwnerTax(String owner);

    /**
     * Finds most similar Estate objects from database
     */
    public List<EstateDto> findSimilarEstateList(SearchEstateDto searchEstateDto);
}
