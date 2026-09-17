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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate Root representing a universal fiscal document.
 */
public class Invoice {
    private final UUID id;
    private InvoiceStatus status;
    private final Money totalAmount;
    private final TaxId issuer;
    private final TaxId receiver;
    private final List<LineItem> items;

    private Invoice(UUID id, Money totalAmount, TaxId issuer, TaxId receiver, List<LineItem> items) {
        this.id = Objects.requireNonNull(id, "Invoice ID cannot be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total amount cannot be null");
        this.issuer = Objects.requireNonNull(issuer, "Issuer TaxId cannot be null");
        this.receiver = Objects.requireNonNull(receiver, "Receiver TaxId cannot be null");
        this.items = new ArrayList<>(Objects.requireNonNull(items, "Items list cannot be null"));
        this.status = InvoiceStatus.DRAFT;
    }

    /**
     * Factory method to create a new Invoice in DRAFT status.
     * 
     * @param issuer   The TaxId of the issuing entity.
     * @param receiver The TaxId of the receiving entity.
     * @param items    The list of LineItems. Must contain at least one item.
     */
    public static Invoice createDraft(TaxId issuer, TaxId receiver, List<LineItem> items) {
        if (issuer.equals(receiver)) {
            throw new IllegalArgumentException("Issuer and Receiver cannot have the same Tax ID.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Invoice must contain at least one line item.");
        }

        // Dynamically calculates the total amount using the precise Money Value Object
        // arithmetic
        Money calculatedTotal = items.get(0).getTotalAmount();
        for (int i = 1; i < items.size(); i++) {
            calculatedTotal = calculatedTotal.add(items.get(i).getTotalAmount());
        }

        if (calculatedTotal.getAmount().compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Invoice total amount cannot be negative.");
        }

        return new Invoice(UUID.randomUUID(), calculatedTotal, issuer, receiver, items);
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

    public TaxId getIssuer() {
        return issuer;
    }

    public TaxId getReceiver() {
        return receiver;
    }

    public List<LineItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public enum InvoiceStatus {
        DRAFT,
        ISSUED,
        CANCELLED
    }
}