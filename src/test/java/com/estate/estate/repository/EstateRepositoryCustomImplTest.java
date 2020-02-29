package com.estate.estate.repository;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.SearchEstate;
import com.estate.estate.model.Adress;
import com.estate.estate.model.Estate;
import com.estate.estate.model.EstateType;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class EstateRepositoryCustomImplTest {

    private static final String OWNER = "Owner";
    private static final String CITY = "Vilnius";
    private static final String STREET = "Cvirkos";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EstateRepository estateRepository;

    @BeforeEach
    void setUp() {
        List<Estate> list = new ArrayList<>();
        list.add(prepareEstateObjects(prepareAdress(2), "OtherOwner"));
        list.add(prepareEstateObjects(prepareAdress(3), OWNER));
        list.add(prepareEstateObjects(prepareAdress(99), "ThirdOwner"));
        estateRepository.saveAll(list);
    }

    @Test
    void customSearchByOwner() {
        //given
        SearchEstate search = new SearchEstate();
        search.setOwner(OWNER);
        //when
        List<Estate> result = estateRepository.customSearch(search);
        //then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getOwner(), equalTo(OWNER));
    }

    @Test
    void customSearcByAdress() {
        //given
        SearchEstate search = new SearchEstate();
        AdressDto adressDto = new AdressDto();
        adressDto.setNumber(99);
        adressDto.setCity(CITY);
        adressDto.setStreet(STREET);
        search.setAdress(adressDto);
        //when
        List<Estate> result = estateRepository.customSearch(search);
        //then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getOwner(), equalTo("ThirdOwner"));
    }

    private Estate prepareEstateObjects(Adress adress, String owner) {
        Estate estate = new Estate();
        estate.setEstateType(EstateType.APARTMENT);
        estate.setOwner(owner);
        estate.setMarketValue(1000);
        estate.setAdress(adress);
        return estate;
    }

    private Adress prepareAdress(int number) {
        Adress adress = new Adress();
        adress.setStreet(STREET);
        adress.setCity(CITY);
        adress.setNumber(number);
        return adress;
    }
}