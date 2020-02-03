package com.estate.estate.model;

import java.math.BigDecimal;

/**
 * Estate type enum. Contains taxing information
 */
public enum EstateType {

    APARTMENT(BigDecimal.valueOf(0.8)),
    HOUSE(BigDecimal.valueOf(0.4)),
    INDUSTRIAL(BigDecimal.valueOf(0.7));

    private final BigDecimal taxRate;

    EstateType(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
