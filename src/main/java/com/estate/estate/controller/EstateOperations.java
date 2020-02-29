package com.estate.estate.controller;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * API operation on Estate
 */
public interface EstateOperations {

    /**
     * Retrieve one Estate based on Adress property
     *
     * @param adress the adress
     * @return the estate
     */
    ResponseEntity<EstateDto> getEstate(AdressDto adress);

    /**
     * Saves Estate to database
     *
     * @param estate the estate
     * @return the response entity
     */
    ResponseEntity<EstateDto> saveEstate(@RequestBody EstateDto estate);

    /**
     * Updates existing Estate in Database
     *
     * @param estate the estate
     * @return the response entity
     */
    ResponseEntity<EstateDto> updateEstate(@RequestBody EstateDto estate);

    /**
     * Deletes Estate from database
     *
     * @param adress the adress
     * @return the response entity
     */
    ResponseEntity<String> deleteEstate(@RequestBody AdressDto adress);

    /**
     * Caslculates tax based on owner property stored in database
     *
     * @param owner the owner
     * @return the response entity
     */
    ResponseEntity<Double> calculateOwnerTax(@PathVariable String owner);

    /**
     * Finds most similar Estate objects from database
     *
     * @param searchEstateDto the search estate dto
     * @return the response entity
     */
    ResponseEntity<List<EstateDto>> findSimilarEstate(@RequestBody SearchEstateDto searchEstateDto);
}
