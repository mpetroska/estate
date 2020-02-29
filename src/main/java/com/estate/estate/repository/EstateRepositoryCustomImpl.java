package com.estate.estate.repository;

import com.estate.estate.dto.AdressDto;
import com.estate.estate.dto.SearchEstate;
import com.estate.estate.model.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Custom Estate Repository Implementation
 */
@Repository
public class EstateRepositoryCustomImpl implements EstateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override

    public List<Estate> customSearch(SearchEstate searchEstate) {

        String createdSearchQuerryString = createSearchString(searchEstate);
        TypedQuery<Estate> querry = entityManager.createQuery(createdSearchQuerryString, Estate.class);
        setParmeters(querry, searchEstate);
        if (searchEstate.isLimitTo3()) {
            //limit result max 3 estates
            querry.setMaxResults(3);
        }
        return querry.getResultList();
    }
    private String createSearchString(SearchEstate searchEstate) {
        Assert.isTrue(searchEstate != null, "search object cannot be null");

        StringBuilder querryString = new StringBuilder("SELECT e FROM Estate e WHERE ");
        if (searchEstate.getBuildingType() != null) {
            querryString.append(" e.estateType = :buildingType and ");
        }
        if (searchEstate.getOwner() != null) {
            querryString.append(" e.owner = :owner and ");
        }
        if (searchEstate.getAdress() != null) {
            cretateAdressSearch(searchEstate.getAdress(), querryString);
        }
        querryString.append(" 1=1 ");

        if (searchEstate.getSize() != null) {
            querryString.append(" order by ABS(e.propertySize - :size) ");
        }

        return querryString.toString();
    }

    private void cretateAdressSearch(AdressDto adress, StringBuilder querryString) {
        if (adress.getCity() != null) {
            querryString.append(" e.adress.city = :city and ");
        }
        if (adress.getStreet() != null) {
            querryString.append(" e.adress.street = :street and ");
        }
        if (adress.getNumber() != null) {
            querryString.append(" e.adress.number = :number and ");
        }
    }

    private void setParmeters(TypedQuery querry, SearchEstate searchEstate) {
        if (searchEstate.getBuildingType() != null) {
            querry.setParameter("buildingType", searchEstate.getBuildingType());
        }
        if (searchEstate.getOwner() != null) {
            querry.setParameter("owner", searchEstate.getOwner());
        }
        if (searchEstate.getSize() != null) {
            querry.setParameter("size", searchEstate.getSize());
        }
        if (searchEstate.getAdress() != null) {
            AdressDto adressDto = searchEstate.getAdress();
            if (adressDto.getCity() != null) {
                querry.setParameter("city", adressDto.getCity());
            }
            if (adressDto.getStreet() != null) {
                querry.setParameter("street", adressDto.getStreet());
            }
            if (adressDto.getNumber() != null) {
                querry.setParameter("number", adressDto.getNumber());
            }
        }
    }
}
