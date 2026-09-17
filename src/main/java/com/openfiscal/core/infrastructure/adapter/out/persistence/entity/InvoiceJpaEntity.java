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
import java.util.UUID;

/**
 * JPA Entity representing the database schema for the Invoice Aggregate Root.
 * Mapped to PostgreSQL.
 */
@Entity
@Table(name = "invoices")
public class InvoiceJpaEntity {

    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_amount_minor", nullable = false)
    private BigInteger totalAmountMinor;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "issuer_tax_id", nullable = false)
    private String issuerTaxId;

    @Column(name = "receiver_tax_id", nullable = false)
    private String receiverTaxId;

    // Default constructor for JPA
    protected InvoiceJpaEntity() {
    }

    public InvoiceJpaEntity(UUID id, String status, BigInteger totalAmountMinor, String currencyCode,
            String issuerTaxId, String receiverTaxId) {
        this.id = id;
        this.status = status;
        this.totalAmountMinor = totalAmountMinor;
        this.currencyCode = currencyCode;
        this.issuerTaxId = issuerTaxId;
        this.receiverTaxId = receiverTaxId;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public BigInteger getTotalAmountMinor() {
        return totalAmountMinor;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getIssuerTaxId() {
        return issuerTaxId;
    }

    public String getReceiverTaxId() {
        return receiverTaxId;
    }
}