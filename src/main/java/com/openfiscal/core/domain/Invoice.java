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
import java.util.UUID;

/**
 * Aggregate Root representing a universal fiscal document.
 */
public class Invoice {
    private final UUID id;
    private InvoiceStatus status;
    private final Money totalAmount; // Refactored to use Money Value Object

    private Invoice(UUID id, Money totalAmount) {
        this.id = Objects.requireNonNull(id, "Invoice ID cannot be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total amount cannot be null");
        this.status = InvoiceStatus.DRAFT;
    }

    /**
     * Factory method to create a new Invoice in DRAFT status.
     * 
     * @param totalAmount The strictly typed financial total.
     */
    public static Invoice createDraft(Money totalAmount) {
        if (totalAmount.getAmount().compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Invoice total amount cannot be negative.");
        }
        return new Invoice(UUID.randomUUID(), totalAmount);
    }

    /**
     * Transitions the Invoice to ISSUED status.
     * 
     * @throws IllegalStateException if the invoice is not in DRAFT status.
     */
    public void issue() {
        if (this.status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Only a DRAFT invoice can be issued.");
        }
        // Future integration: Dispatch InvoiceIssuedDomainEvent here
        this.status = InvoiceStatus.ISSUED;
    }

    public UUID getId() {
        return id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public enum InvoiceStatus {
        DRAFT,
        ISSUED,
        CANCELLED
    }
}