/*
 * OpenFiscal-Engine
 *
 * Author: Juan S.
 * Copyright (C) 2026 Juan S.
 *
 * This file is part of OpenFiscal-Engine.
 *
 * IFMP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * IFMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenFiscal-Engine. If not, see <https://www.gnu.org/licenses/>.
 */
package com.openfiscal.core.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * JPA Embeddable representing the Tax Value Object.
 */
@Embeddable
public class TaxJpaEmbeddable {

    @Column(name = "tax_name", nullable = false)
    private String name;

    @Column(name = "tax_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal rate;

    @Column(name = "tax_amount_minor", nullable = false)
    private BigInteger amountMinor;

    protected TaxJpaEmbeddable() {
    }

    public TaxJpaEmbeddable(String name, BigDecimal rate, BigInteger amountMinor) {
        this.name = name;
        this.rate = rate;
        this.amountMinor = amountMinor;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigInteger getAmountMinor() {
        return amountMinor;
    }
}