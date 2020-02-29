package com.estate.estate.controller;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;
import com.estate.estate.service.EstateService;
import com.estate.estate.utils.ValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Api(value = "Estate management system")
@RequiredArgsConstructor
@RestController
@RequestMapping("/estate")
public class EstateController implements EstateOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateController.class);
    private final EstateService estateService;

    @ApiOperation(value = "Find Estate data from adress", response = EstateDto.class)
    @ApiParam(value = "Adress fields must be provided as query parameters")
    @GetMapping("")
    @Override
    public ResponseEntity<EstateDto> getEstate(@NonNull AdressDto adress) {
        try {
            EstateDto estateDto = estateService.findEstateByAdress(adress);
            return new ResponseEntity<>(estateDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Save Estate to database", response = EstateDto.class)
    @ApiParam(value = "EstateDto object must be provided into request body")
    @PostMapping("")
    @Override
    public ResponseEntity<EstateDto> saveEstate(@NonNull EstateDto estate) {
        try {
            EstateDto estateDto = estateService.saveEstate(estate);
            return new ResponseEntity<>(estateDto, HttpStatus.OK);
        } catch (IllegalArgumentException | ValidationException | DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
            String message = e instanceof DataIntegrityViolationException ? "Such adress already exist in database": null;
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message != null ? message : e.getMessage());
        }
    }
    @ApiOperation(value = "Update Estate to database", response = EstateDto.class)
    @PutMapping("")
    @Override
    public ResponseEntity<EstateDto> updateEstate(@NonNull EstateDto estate) {
        try {
            EstateDto estateDto = estateService.updateEstate(estate);
            return new ResponseEntity<>(estateDto, HttpStatus.OK);
        } catch (IllegalArgumentException | ValidationException | DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
            String message = e instanceof DataIntegrityViolationException ? "Such adress already exist in database": null;
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message != null ? message : e.getMessage());
        }
    }
    @ApiOperation(value = "Deletes Estate from database", response = String.class)
    @DeleteMapping("")
    @Override
    public ResponseEntity<String> deleteEstate(@NonNull AdressDto adress) {
        try {
            String result = estateService.deleteEstate(adress);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @ApiOperation(value = "Calculates tax which property owner must pay", response = Double.class)
    @GetMapping("/details/{owner}")
    @Override
    public ResponseEntity<Double> calculateOwnerTax(@NonNull String owner) {
        try {
            double taxSize = estateService.calculateOwnerTax(owner);
            return new ResponseEntity<>(taxSize, HttpStatus.OK);
        } catch (IllegalArgumentException |ValidationException e ) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @ApiOperation(value = "Based on Entered search criteria returns 3 closest options of Estate", response = List.class)
    @PostMapping("/details")
    @Override
    public ResponseEntity<List<EstateDto>> findSimilarEstate(@NonNull SearchEstateDto searchEstateDto) {
        try {
            List<EstateDto> similarEstates = estateService.findSimilarEstateList(searchEstateDto);
            return new ResponseEntity<>(similarEstates, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
