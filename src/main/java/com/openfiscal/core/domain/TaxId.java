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

import java.util.Objects;

/**
 * Universal Value Object representing a Taxpayer Identification Number.
 * Localization plugins will handle country-specific validations (e.g., RFC,
 * TIN).
 */
public final class TaxId {
    private final String value;

    private TaxId(String value) {
        this.value = Objects.requireNonNull(value, "Tax ID cannot be null").trim();
        if (this.value.isEmpty()) {
            throw new IllegalArgumentException("Tax ID cannot be empty");
        }
    }

    /**
     * Factory method to create a universal TaxId.
     * 
     * @param value The universal string representation of the tax identifier.
     * @return A safely instantiated TaxId Value Object.
     */
    public static TaxId of(String value) {
        return new TaxId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TaxId taxId = (TaxId) o;
        return value.equals(taxId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}