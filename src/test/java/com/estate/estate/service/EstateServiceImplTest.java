package com.estate.estate.service;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.EstateDto;
import com.estate.estate.dto.SearchEstateDto;
import com.estate.estate.model.Adress;
import com.estate.estate.model.Estate;
import com.estate.estate.model.EstateType;
import com.estate.estate.repository.EstateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EstateServiceImplTest {

    @InjectMocks
    private EstateServiceImpl service;

    @Mock
    private EstateRepository estateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test()
    void findEstateByAdress() {
        //given
        AdressDto adressDto = createAdressDto();
        List<Estate> returnList = new ArrayList();
        returnList.add(createEstate());

        when(estateRepository.customSearch(any())).thenReturn(returnList);

        //when
        EstateDto estateReturn = service.findEstateByAdress(adressDto);

        //then
        assertThat(estateReturn, notNullValue());
        assertThat(estateReturn.getId(), equalTo(Long.valueOf(1)));
        assertThat(estateReturn.getAdress().getStreet(), equalTo("Didzioji"));
        assertThat(estateReturn.getAdress().getCity(), equalTo("Vilnius"));
        assertThat(estateReturn.getAdress().getNumber(), equalTo(5));
        assertThat(estateReturn.getEstateType(), equalTo(EstateType.HOUSE));
    }

    @Test
    void failValidationWithNullAdressFields() {
        //given
        AdressDto adressDto = createAdressDto();
        adressDto.setNumber(null);

        //when
        assertThrows(IllegalArgumentException.class,
                () -> {
                    service.findEstateByAdress(adressDto);
                }
        );
    }

    @Test
    void saveEstate() {
        //given
        EstateDto estateDto = createEstateDto();
        when(estateRepository.save(anyObject())).thenReturn(createEstate());

        //when
        EstateDto estateReturn = service.saveEstate(estateDto);

        //then
        assertThat(estateReturn, notNullValue());
        assertThat(estateReturn.getId(), equalTo(Long.valueOf(1)));
        assertThat(estateReturn.getAdress().getStreet(), equalTo("Didzioji"));
        assertThat(estateReturn.getAdress().getCity(), equalTo("Vilnius"));
        assertThat(estateReturn.getAdress().getNumber(), equalTo(5));
        assertThat(estateReturn.getEstateType(), equalTo(EstateType.HOUSE));
    }

    @Test
    void failValidationAndGetException() {
        //given
        EstateDto estateDto = createEstateDto();
        estateDto.setMarketValue(null);

        //when
        assertThrows(IllegalArgumentException.class,
                () -> {
                    service.saveEstate(estateDto);
                });
    }

    @Test
    void updateEstate() {
        //given
        EstateDto estateDto = createEstateDto();
        estateDto.setId(1L);
        when(estateRepository.findById(anyLong())).thenReturn(Optional.of(createEstate()));
        when(estateRepository.save(createEstate())).thenReturn(createEstate());

        //when
        EstateDto estateReturn = service.updateEstate(estateDto);

        //then
        assertThat(estateReturn, notNullValue());
        assertThat(estateReturn.getId(), equalTo(Long.valueOf(1)));
        assertThat(estateReturn.getAdress().getStreet(), equalTo("Didzioji"));
        assertThat(estateReturn.getAdress().getCity(), equalTo("Vilnius"));
        assertThat(estateReturn.getAdress().getNumber(), equalTo(5));
        assertThat(estateReturn.getEstateType(), equalTo(EstateType.HOUSE));
    }

    @Test
    void deleteEstate() {
        //given
        AdressDto adressDto = createAdressDto();
        List<Estate> resultList = new ArrayList<>();
        Estate estate = createEstate();
        resultList.add(estate);

        when(estateRepository.customSearch(any())).thenReturn(resultList);
        doNothing().when(estateRepository).delete(estate);

        //when
        String returnMessage = service.deleteEstate(adressDto);

        //then
        assertThat(returnMessage, notNullValue());
        assertThat(returnMessage, equalTo((String) "successfully deleted record"));
    }

    @Test
    void calculateOwnerTax() {
        //given
        List<Estate> resultList = new ArrayList<>();
        Estate estate = createEstate();
        resultList.add(estate);
        BigDecimal tax = BigDecimal.valueOf(estate.getMarketValue()).multiply(estate.getEstateType().getTaxRate());
        when(estateRepository.findAllByOwner(anyString())).thenReturn(resultList);

        //when
        Double taxSize = service.calculateOwnerTax("Owner");

        //then
        assertThat(taxSize, notNullValue());
        assertThat(taxSize, equalTo(tax.doubleValue()));
    }

    @Test
    void noOwnersFoundException() {
        //given
        when(estateRepository.findAllByOwner(anyString())).thenReturn(Arrays.asList());

        //when
        assertThrows(IllegalArgumentException.class,
                () -> {
                    service.calculateOwnerTax("Owner");
                });
    }

    @Test
    void findSimilarEstateList() {
        //given
        SearchEstateDto searchEstateDto = new SearchEstateDto();
        searchEstateDto.setAdress(createAdressDto());
        searchEstateDto.setBuildingType(EstateType.HOUSE);
        searchEstateDto.setSize(10D);

        Estate estate1 = createEstate();
        Estate estate2 = createEstate();
        estate2.setId(2);
        estate2.setMarketValue(10000);
        estate2.setPropertySize(1000);
        Estate estate3 = createEstate();
        estate3.setId(3);
        estate3.setMarketValue(200);
        estate3.setPropertySize(1500);
        Estate estate4 = createEstate();
        estate4.setId(4);
        estate4.setMarketValue(250);
        estate4.setPropertySize(1600);
        List<Estate> resultList = Arrays.asList(estate1, estate2, estate3, estate4);

        when(estateRepository.customSearch(any())).thenReturn(resultList);

        //when
        List<EstateDto> sortedList = service.findSimilarEstateList(searchEstateDto);

        //then
        assertThat(sortedList, notNullValue());
        assertThat(sortedList.size(), equalTo(3));
        assertThat(sortedList.get(0).getId(), equalTo(Long.valueOf(2)));
    }

    private EstateDto createEstateDto() {
        EstateDto estateDto = new EstateDto();
        estateDto.setAdress(createAdressDto());
        estateDto.setOwner("Owner");
        estateDto.setEstateType(EstateType.HOUSE);
        estateDto.setMarketValue(9900);
        estateDto.setPropertySize(100D);
        return estateDto;
    }

    private Estate createEstate() {
        Estate estate = new Estate();
        Adress adress = new Adress();
        adress.setNumber(5);
        adress.setCity("Vilnius");
        adress.setStreet("Didzioji");
        adress.setId(1);
        estate.setAdress(adress);
        estate.setId(1);
        estate.setPropertySize(100D);
        estate.setOwner("Owner");
        estate.setMarketValue(9900);
        estate.setEstateType(EstateType.HOUSE);
        return estate;
    }

    private AdressDto createAdressDto() {
        AdressDto adressDto = new AdressDto();
        adressDto.setCity("Vilnius");
        adressDto.setStreet("Didzioji");
        adressDto.setNumber(5);
        return adressDto;
    }
}