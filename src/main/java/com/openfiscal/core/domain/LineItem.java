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
import java.util.UUID;

/**
 * Entity representing a single row in an Invoice.
 */
public class LineItem {
    private final UUID id;
    private final String description;
    private final int quantity;
    private final Money unitPrice;
    private final Money totalAmount;

    private LineItem(UUID id, String description, int quantity, Money unitPrice) {
        this.id = Objects.requireNonNull(id, "LineItem ID cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null").trim();

        if (this.description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");
        // Calculates the total amount for this row natively using the Money Value
        // Object
        this.totalAmount = unitPrice.multiply(quantity);
    }

    /**
     * Factory method to create a new LineItem.
     * 
     * @param description The commercial description of the good or service.
     * @param quantity    The amount of units (must be greater than zero).
     * @param unitPrice   The strictly typed financial cost per unit.
     */
    public static LineItem create(String description, int quantity, Money unitPrice) {
        return new LineItem(UUID.randomUUID(), description, quantity, unitPrice);
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }
}