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
package com.openfiscal.compliance.application.service;

import com.openfiscal.compliance.application.port.out.BlacklistQueryPort;
import com.openfiscal.compliance.application.port.out.ImmutableAuditLogPort;
import com.openfiscal.compliance.domain.FraudAlert;

/**
 * Application Service implementing the algorithmic detection of simulated
 * operations
 * and fiscal discrepancies.
 */
public class ComplianceAuditService {

    private final BlacklistQueryPort blacklistQueryPort;
    private final ImmutableAuditLogPort auditLogPort;

    public ComplianceAuditService(BlacklistQueryPort blacklistQueryPort, ImmutableAuditLogPort auditLogPort) {
        this.blacklistQueryPort = blacklistQueryPort;
        this.auditLogPort = auditLogPort;
    }

    public void evaluateTransaction(AuditTransactionCommand command) {
        // 1. Detect Simulated Operations (Blacklist Check)
        if (blacklistQueryPort.isBlacklisted(command.issuerTaxId())) {
            FraudAlert alert = FraudAlert.createSimulatedOperation(command.issuerTaxId());
            auditLogPort.append(alert);
            return; // Block further processing for severe simulated operations
        }

        if (blacklistQueryPort.isBlacklisted(command.receiverTaxId())) {
            FraudAlert alert = FraudAlert.createSimulatedOperation(command.receiverTaxId());
            auditLogPort.append(alert);
            return;
        }

        // 2. Detect Fiscal Discrepancy (Reconciliation Failure)
        if (!command.isFinanciallyReconciled()) {
            FraudAlert alert = FraudAlert.createFiscalDiscrepancy(
                    command.issuerTaxId(),
                    "Transaction amount " + command.amountMinor() + " was not reconciled with banking events.");
            auditLogPort.append(alert);
        }
    }

    public record AuditTransactionCommand(
            String issuerTaxId,
            String receiverTaxId,
            long amountMinor,
            boolean isFinanciallyReconciled) {
    }
}
