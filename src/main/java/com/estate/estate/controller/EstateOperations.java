package com.estate.estate.controller;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API operation on Estate
 */
@RequestMapping("/estate")
public interface EstateOperations {

    /**
     * Retrieve one Estate based on Adress property
     */
    @GetMapping("")
    ResponseEntity<EstateDto> getEstate(AdressDto adress);

    /**
     * Saves Estate to database
     */
    @PostMapping("")
    ResponseEntity<EstateDto> saveEstate(@RequestBody EstateDto estate);

    /**
     * Updates existing Estate in Database
     */
    @PutMapping("")
    ResponseEntity<EstateDto> updateEstate(@RequestBody EstateDto estate);

    /**
     * Deletes Estate from database
     */
    @DeleteMapping("")
    ResponseEntity<String> deleteEstate(@RequestBody AdressDto adress);

    /**
     * Caslculates tax based on owner property stored in database
     */
    @GetMapping("/details/{owner}")
    ResponseEntity<Double> calculateOwnerTax(@PathVariable  String owner);

    /**
     * Finds most similar Estate objects from database
     */
    @PostMapping("/details")
    ResponseEntity<List<EstateDto>> findSimilarEstate(@RequestBody SearchEstateDto searchEstateDto);
}
