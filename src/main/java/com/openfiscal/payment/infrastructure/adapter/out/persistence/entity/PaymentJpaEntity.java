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
package com.openfiscal.payment.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity mapping the PaymentEvent Aggregate Root to the database schema.
 */
@Entity
@Table(name = "payments")
public class PaymentJpaEntity {

    @Id
    private UUID id;

    @Column(name = "amount_minor", nullable = false)
    private BigInteger amountMinor;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "tracking_key", nullable = false, unique = true)
    private String trackingKey;

    @Column(name = "occurred_on", nullable = false)
    private Instant occurredOn;

    @Column(name = "status", nullable = false)
    private String status;

    protected PaymentJpaEntity() {
    }

    public PaymentJpaEntity(UUID id, BigInteger amountMinor, String currencyCode,
            String trackingKey, Instant occurredOn, String status) {
        this.id = id;
        this.amountMinor = amountMinor;
        this.currencyCode = currencyCode;
        this.trackingKey = trackingKey;
        this.occurredOn = occurredOn;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public BigInteger getAmountMinor() {
        return amountMinor;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getTrackingKey() {
        return trackingKey;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public String getStatus() {
        return status;
    }
}