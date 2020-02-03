package com.estate.estate.service;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstate;
import com.estate.estate.dto.SearchEstateDto;
import com.estate.estate.model.Adress;
import com.estate.estate.model.Estate;
import com.estate.estate.repository.EstateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("estateService")
public class EstateServiceImpl implements EstateService {

    private final EstateRepository estateRepository;

    @Override
    public EstateDto findEstateByAdress(AdressDto adress) {
        validateAdressDtoFields(adress);
        SearchEstate search = new SearchEstate();
        search.setAdress(adress);
        List<Estate> listOfEstate = estateRepository.customSearch(search);
        Assert.isTrue(listOfEstate.size() == 1, "Such Estate does not exist in records");
        return mapToDto(listOfEstate.get(0));
    }

    @Override
    public EstateDto saveEstate(EstateDto estateDto) {
        validateEstateDtoFields(estateDto);
        Estate estate = mapToEntity(estateDto, null);
        return mapToDto(estateRepository.save(estate));
    }

    @Override
    public EstateDto updateEstate(EstateDto estateDto) {
        validateEstateDtoFields(estateDto);
        Assert.isTrue(estateDto.getId()!= null, "cannot update estate without Id");
        Optional<Estate> estate = estateRepository.findById(estateDto.getId());
        if (estate.isPresent()) {
            Estate estateUpdated = mapToEntity(estateDto, estate.get());
            return mapToDto(estateRepository.save(estateUpdated));
        }
        throw new IllegalArgumentException("requered property were not found");
    }

    @Override
    public String deleteEstate(AdressDto adressDto) {
        validateAdressDtoFields(adressDto);
        SearchEstate search = new SearchEstate();
        search.setAdress(adressDto);
        List<Estate> deletionCandidate = estateRepository.customSearch(search);
        Assert.isTrue(deletionCandidate.size() == 1, "none record was deleted");
        estateRepository.delete(deletionCandidate.get(0));
        return "successfully deleted record";
    }

    @Override
    public Double calculateOwnerTax(@NonNull String owner) {
        List<Estate> ownerProperty = estateRepository.findAllByOwner(owner);
        Assert.isTrue(!ownerProperty.isEmpty(), "This owner has no property registered");
        BigDecimal taxSize = new BigDecimal(0);
        for (Estate estate : ownerProperty) {
            BigDecimal singlePropertyFee = BigDecimal.valueOf(estate.getMarketValue()).multiply(estate.getEstateType().getTaxRate());
            taxSize = taxSize.add(singlePropertyFee);
        }
        return taxSize.doubleValue();
    }

    @Override
    public List<EstateDto> findSimilarEstateList(SearchEstateDto searchEstateDto){
        validateAndClearParameters(searchEstateDto);

        SearchEstate searchEstate = mapToSearch(searchEstateDto);
        List<Estate> allSimilarEstates = estateRepository.customSearch(searchEstate);
        List<Estate> limitedEstates = allSimilarEstates.size()<=3 ? allSimilarEstates : allSimilarEstates.subList(0, 3);
        return limitedEstates.stream().map(this::mapToDto).sorted(((e1, e2) -> e2.getMarketValue().compareTo(e1.getMarketValue()))).collect(Collectors.toList());
    }

    private SearchEstate mapToSearch(SearchEstateDto searchEstateDto) {
        SearchEstate searchEstate = new SearchEstate();
        searchEstate.setAdress(searchEstate.getAdress());
        searchEstate.setBuildingType(searchEstateDto.getBuildingType());
        searchEstate.setSize(searchEstateDto.getSize());
        return searchEstate;
    }

    private void validateAndClearParameters(SearchEstateDto searchEstateDto) {
        if (searchEstateDto == null || searchEstateDto.getBuildingType() == null
                || searchEstateDto.getAdress() == null || searchEstateDto.getSize() == null) {
            throw new IllegalArgumentException("Provided request parameters are not valid");
        }
        validateAdressDtoFields(searchEstateDto.getAdress());
        searchEstateDto.getAdress().setNumber(null);
    }

    private Estate mapToEntity(EstateDto estateDto, Estate estate) {
        Estate result = null;
        if (estate != null) {
            result = estate;
        } else {
            result = new Estate();
            result.setAdress(new Adress());
        }
        mapAdressDtoToEntity(result.getAdress(), estateDto.getAdress());
        result.setEstateType(estateDto.getEstateType());
        result.setMarketValue(estateDto.getMarketValue());
        result.setOwner(estateDto.getOwner());
        result.setPropertySize(estateDto.getPropertySize());
        return result;
    }

    private EstateDto mapToDto(Estate estate) {
        EstateDto result = new EstateDto();
        result.setId(estate.getId());
        result.setAdress(mapToDto(estate.getAdress()));
        result.setOwner(estate.getOwner());
        result.setMarketValue(estate.getMarketValue());
        result.setPropertySize(estate.getPropertySize());
        result.setEstateType(estate.getEstateType());
        return result;
    }

    private AdressDto mapToDto(Adress adress) {
        AdressDto result = new AdressDto();
        result.setCity(adress.getCity());
        result.setStreet(adress.getStreet());
        result.setNumber(adress.getNumber());
        return result;
    }

    private void mapAdressDtoToEntity(Adress adress, AdressDto adressDto) {
        adress.setCity(adressDto.getCity());
        adress.setStreet(adressDto.getStreet());
        adress.setNumber(adressDto.getNumber());

    }

    private void validateEstateDtoFields(EstateDto estateDto) {
        if (estateDto == null || estateDto.getEstateType() == null || estateDto.getMarketValue() == null || estateDto.getOwner() == null ||
                estateDto.getPropertySize() == null || estateDto.getAdress() == null) {
            throw new IllegalArgumentException("Estate or its fields cannot be null");
        }
        validateAdressDtoFields(estateDto.getAdress());
    }

    private void validateAdressDtoFields(AdressDto adress) {
        if (adress == null || adress.getCity()==null || adress.getStreet() == null || adress.getNumber() == null) {
            throw new IllegalArgumentException("Adress or its fields cannot be null");
        }
    }
}
