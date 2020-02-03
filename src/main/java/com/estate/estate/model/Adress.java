package com.estate.estate.model;

import lombok.Data;
import javax.persistence.*;

/**
 * Adress entity
 */
@Entity
@Data
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"city", "street", "number"})})
public class Adress {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String street;

    @Column(nullable = false)
    private int number;
}
