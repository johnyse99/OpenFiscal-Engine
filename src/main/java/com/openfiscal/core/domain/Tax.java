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
package com.openfiscal.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object representing a fiscal charge or retention applied to a LineItem
 * or Invoice.
 */
public final class Tax {
    private final String name;
    private final BigDecimal rate;
    private final Money amount;

    private Tax(String name, BigDecimal rate, Money amount) {
        this.name = Objects.requireNonNull(name, "Tax name cannot be null").trim();
        this.rate = Objects.requireNonNull(rate, "Tax rate cannot be null");
        this.amount = Objects.requireNonNull(amount, "Tax amount cannot be null");

        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("Tax name cannot be empty");
        }
        if (this.rate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }
    }

    /**
     * Factory method to create a new Tax Value Object.
     * 
     * @param name   The nomenclature of the tax (e.g., "VAT", "IVA", "ISR").
     * @param rate   The percentage or decimal rate applied (e.g., 0.16 for 16%).
     * @param amount The strictly calculated financial amount of the tax using the
     *               Money object.
     */
    public static Tax create(String name, BigDecimal rate, Money amount) {
        return new Tax(name, rate, amount);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Money getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tax tax = (Tax) o;
        return name.equals(tax.name) &&
                rate.equals(tax.rate) &&
                amount.equals(tax.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate, amount);
    }
}