package com.estate.estate.repository;

import com.estate.estate.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Estate entity Repository
 */
@Repository
public interface EstateRepository extends JpaRepository<Estate, Long>, EstateRepositoryCustom {

    /**
     * Finds all property of specified owner
     */
    public List<Estate> findAllByOwner (String owner);
}
