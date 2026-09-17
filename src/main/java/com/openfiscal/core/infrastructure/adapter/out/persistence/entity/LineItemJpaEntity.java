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

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * JPA Entity representing the LineItem domain entity.
 */
@Entity
@Table(name = "line_items")
public class LineItemJpaEntity {

    @Id
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price_minor", nullable = false)
    private BigInteger unitPriceMinor;

    @Column(name = "total_amount_minor", nullable = false)
    private BigInteger totalAmountMinor;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "line_item_taxes", joinColumns = @JoinColumn(name = "line_item_id"))
    private List<TaxJpaEmbeddable> taxes;

    protected LineItemJpaEntity() {
    }

    public LineItemJpaEntity(UUID id, String description, int quantity, BigInteger unitPriceMinor,
            BigInteger totalAmountMinor, String currencyCode, List<TaxJpaEmbeddable> taxes) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.unitPriceMinor = unitPriceMinor;
        this.totalAmountMinor = totalAmountMinor;
        this.currencyCode = currencyCode;
        this.taxes = taxes;
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

    public BigInteger getUnitPriceMinor() {
        return unitPriceMinor;
    }

    public BigInteger getTotalAmountMinor() {
        return totalAmountMinor;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public List<TaxJpaEmbeddable> getTaxes() {
        return taxes;
    }
}