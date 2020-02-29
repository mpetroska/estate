package com.estate.estate.repository;

import com.estate.estate.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Estate entity Repository
 */
public interface EstateRepository extends JpaRepository<Estate, Long>, EstateRepositoryCustom {

    /**
     * Finds all property of specified owner
     *
     * @param owner the owner
     * @return the list
     */
    public List<Estate> findAllByOwner (String owner);
}
