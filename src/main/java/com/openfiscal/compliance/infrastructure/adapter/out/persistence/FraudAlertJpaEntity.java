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
package com.openfiscal.compliance.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity representing the immutable audit log record in the database.
 */
@Entity
@Table(name = "fraud_alerts")
public class FraudAlertJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "alert_type", updatable = false, nullable = false)
    private String type;

    @Column(name = "description", updatable = false, nullable = false)
    private String description;

    @Column(name = "target_tax_id", updatable = false, nullable = false)
    private String targetTaxId;

    @Column(name = "detected_on", updatable = false, nullable = false)
    private Instant detectedOn;

    // Default constructor for JPA
    protected FraudAlertJpaEntity() {
    }

    public FraudAlertJpaEntity(UUID id, String type, String description, String targetTaxId, Instant detectedOn) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.targetTaxId = targetTaxId;
        this.detectedOn = detectedOn;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetTaxId() {
        return targetTaxId;
    }

    public Instant getDetectedOn() {
        return detectedOn;
    }
}
