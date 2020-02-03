package com.estate.estate.model;

import lombok.Data;
import javax.persistence.*;

/**
 * Estate entity
 */
@Entity
@Data
public class Estate {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    private Adress adress;

    @Column(length = 50, nullable = false)
    private String owner;

    @Column(nullable = false)
    private double propertySize;

    @Column(nullable = false)
    private int marketValue;

    @Column(nullable = false)
    private EstateType estateType;
}
