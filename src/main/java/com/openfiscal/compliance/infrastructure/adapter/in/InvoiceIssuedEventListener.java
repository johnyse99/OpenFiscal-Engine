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
package com.openfiscal.compliance.infrastructure.adapter.in;

import com.openfiscal.compliance.application.service.ComplianceAuditService;
import com.openfiscal.compliance.application.service.ComplianceAuditService.AuditTransactionCommand;
import com.openfiscal.core.domain.events.InvoiceIssuedDomainEvent;

import java.util.Objects;

/**
 * Infrastructure Inbound Adapter that listens for Domain Events from the Core
 * Billing Context
 * and triggers the Compliance & Audit matching rules.
 */
public class InvoiceIssuedEventListener {

    private final ComplianceAuditService auditService;

    public InvoiceIssuedEventListener(ComplianceAuditService auditService) {
        this.auditService = Objects.requireNonNull(auditService, "ComplianceAuditService cannot be null");
    }

    /**
     * Handles the incoming event.
     * In a Spring Boot environment, this method would be annotated
     * with @EventListener
     * or @TransactionalEventListener.
     */
    public void on(InvoiceIssuedDomainEvent event) {
        // At the exact moment of issuance, the invoice is not yet financially
        // reconciled via Open Banking.
        AuditTransactionCommand command = new AuditTransactionCommand(
                event.issuerTaxId(),
                event.receiverTaxId(),
                event.totalAmountMinor().longValue(),
                false);

        auditService.evaluateTransaction(command);
    }
}
