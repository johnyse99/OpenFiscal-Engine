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
package com.openfiscal.payment.domain;

import com.openfiscal.core.domain.Money;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate Root for the Payment & Collection Context.
 * Represents an external, verifiable notification of cash flow originating from
 * banking systems.
 */
public class PaymentEvent {
    private final UUID id;
    private final Money amount;
    private final String trackingKey; // e.g., SPEI Clave de Rastreo or CoDi Folio
    private final Instant occurredOn;
    private PaymentStatus status;

    private PaymentEvent(UUID id, Money amount, String trackingKey, Instant occurredOn) {
        this.id = Objects.requireNonNull(id, "PaymentEvent ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.trackingKey = Objects.requireNonNull(trackingKey, "Tracking key cannot be null").trim();
        this.occurredOn = Objects.requireNonNull(occurredOn, "Occurrence timestamp cannot be null");

        if (this.trackingKey.isEmpty()) {
            throw new IllegalArgumentException("Tracking key cannot be empty");
        }

        this.status = PaymentStatus.PENDING_RECONCILIATION;
    }

    public static PaymentEvent create(Money amount, String trackingKey, Instant occurredOn) {
        return new PaymentEvent(UUID.randomUUID(), amount, trackingKey, occurredOn);
    }

    public void markAsReconciled() {
        if (this.status == PaymentStatus.RECONCILED) {
            throw new IllegalStateException("Payment is already reconciled.");
        }
        this.status = PaymentStatus.RECONCILED;
    }

    public void markAsUnmatched() {
        this.status = PaymentStatus.UNMATCHED;
    }

    public UUID getId() {
        return id;
    }

    public Money getAmount() {
        return amount;
    }

    public String getTrackingKey() {
        return trackingKey;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public enum PaymentStatus {
        PENDING_RECONCILIATION,
        RECONCILED,
        UNMATCHED
    }
}