package com.estate.estate.service;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;

import java.util.List;

/**
 * Service to perform operation with Estate
 */
public interface EstateService {

    /**
     * Finds Estate by Adress
     *
     * @param adress the adress
     * @return the estate dto
     */
    public EstateDto findEstateByAdress(AdressDto adress);

    /**
     * Saves received Estate object
     *
     * @param estateDto the estate dto
     * @return the estate dto
     */
    public EstateDto saveEstate(EstateDto estateDto);

    /**
     * Updates received Estate object
     *
     * @param estateDto the estate dto
     * @return the estate dto
     */
    public EstateDto updateEstate(EstateDto estateDto);

    /**
     * Deletes Estate object based on received adress
     *
     * @param adressDto the adress dto
     * @return the string
     */
    public String deleteEstate(AdressDto adressDto);

    /**
     * Calculates tax based on owner property stored in database
     *
     * @param owner String parameter
     * @return the double
     */
    public Double calculateOwnerTax(String owner);

    /**
     * Finds most similar Estate objects from database
     *
     * @param searchEstateDto the search estate dto
     * @return the list
     */
    public List<EstateDto> findSimilarEstateList(SearchEstateDto searchEstateDto);
}
