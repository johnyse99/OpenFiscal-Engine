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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity representing a single row within an Invoice.
 */
public class LineItem {
    private final UUID id;
    private final String description;
    private final int quantity;
    private final Money unitPrice;
    private final List<Tax> taxes;
    private final Money totalAmount;

    private LineItem(UUID id, String description, int quantity, Money unitPrice, List<Tax> taxes, Money totalAmount) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");
        this.taxes = new ArrayList<>(Objects.requireNonNull(taxes, "Taxes list cannot be null"));
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total amount cannot be null");
    }

    public static LineItem create(String description, int quantity, Money unitPrice, List<Tax> taxes) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be strictly positive.");
        }

        // 1. Calculate base amount (Quantity * Unit Price)
        Money calculatedTotal = unitPrice.multiply(quantity);

        // 2. Add all tax amounts to the total
        if (taxes != null) {
            for (Tax tax : taxes) {
                calculatedTotal = calculatedTotal.add(tax.getAmount());
            }
        } else {
            taxes = Collections.emptyList();
        }

        return new LineItem(UUID.randomUUID(), description, quantity, unitPrice, taxes, calculatedTotal);
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

    public List<Tax> getTaxes() {
        return Collections.unmodifiableList(taxes);
    }

    public Money getTotalAmount() {
        return totalAmount;
    }
}
