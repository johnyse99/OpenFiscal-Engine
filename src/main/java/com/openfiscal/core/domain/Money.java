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

import java.math.BigInteger;
import java.util.Objects;

/**
 * Value Object representing a precise financial amount to prevent rounding
 * errors.
 */
public final class Money {
    private final BigInteger amountInMinorUnit;
    private final String currencyCode; // e.g., "MXN", "USD", "CNY"

    private Money(BigInteger amountInMinorUnit, String currencyCode) {
        this.amountInMinorUnit = Objects.requireNonNull(amountInMinorUnit, "Amount cannot be null");
        this.currencyCode = Objects.requireNonNull(currencyCode, "Currency code cannot be null");
    }

    /**
     * Creates a Money instance from minor units (e.g., cents).
     * For $100.50 MXN, pass 10050 and "MXN".
     */
    public static Money ofMinorUnit(long amount, String currencyCode) {
        return new Money(BigInteger.valueOf(amount), currencyCode);
    }

    public static Money ofMinorUnit(BigInteger amount, String currencyCode) {
        return new Money(amount, currencyCode);
    }

    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(this.amountInMinorUnit.add(other.amountInMinorUnit), this.currencyCode);
    }

    public Money subtract(Money other) {
        assertSameCurrency(other);
        return new Money(this.amountInMinorUnit.subtract(other.amountInMinorUnit), this.currencyCode);
    }

    public Money multiply(long multiplier) {
        return new Money(this.amountInMinorUnit.multiply(BigInteger.valueOf(multiplier)), this.currencyCode);
    }

    private void assertSameCurrency(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException(
                    String.format("Currency mismatch: Cannot operate on %s and %s", this.currencyCode,
                            other.currencyCode));
        }
    }

    public BigInteger getAmount() {
        return amountInMinorUnit;
    }

    public String getCurrency() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return amountInMinorUnit.equals(money.amountInMinorUnit) &&
                currencyCode.equals(money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInMinorUnit, currencyCode);
    }
}