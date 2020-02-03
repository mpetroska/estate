package com.estate.estate.repository;

import com.estate.estate.dto.SearchEstate;
import com.estate.estate.model.Estate;

import java.util.List;

public interface EstateRepositoryCustom {

    /**
     * Custmizable search for Estate
     */
    public List<Estate> customSearch(SearchEstate searchEstate);
}
