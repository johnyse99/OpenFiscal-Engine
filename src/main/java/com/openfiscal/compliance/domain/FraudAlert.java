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
package com.openfiscal.compliance.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain Entity representing a detected fraud or evasion attempt.
 */
public class FraudAlert {
    private final UUID id;
    private final AlertType type;
    private final String description;
    private final String targetTaxId;
    private final Instant detectedOn;

    private FraudAlert(UUID id, AlertType type, String description, String targetTaxId) {
        this.id = Objects.requireNonNull(id, "Alert ID cannot be null");
        this.type = Objects.requireNonNull(type, "Alert type cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.targetTaxId = Objects.requireNonNull(targetTaxId, "Target TaxId cannot be null");
        this.detectedOn = Instant.now();
    }

    public static FraudAlert createSimulatedOperation(String taxId) {
        return new FraudAlert(
                UUID.randomUUID(),
                AlertType.SIMULATED_OPERATION,
                "Critical: Taxpayer found on official Blacklist (EFOS/EDOS)",
                taxId);
    }

    public static FraudAlert createFiscalDiscrepancy(String taxId, String details) {
        return new FraudAlert(
                UUID.randomUUID(),
                AlertType.FISCAL_DISCREPANCY,
                "Warning: Unmatched financial flow vs issued invoice - " + details,
                taxId);
    }

    public enum AlertType {
        SIMULATED_OPERATION,
        FISCAL_DISCREPANCY
    }

    public UUID getId() {
        return id;
    }

    public AlertType getType() {
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
