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
package com.openfiscal.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Core Billing Ports & Services
import com.openfiscal.core.application.port.out.EventPublisher;
import com.openfiscal.core.application.port.out.InvoiceRepository;
import com.openfiscal.core.application.service.DraftInvoiceService;
import com.openfiscal.core.application.service.IssueInvoiceService;

// Payment Ports & Services
import com.openfiscal.payment.application.port.in.FinancialReconciliationUseCase;
import com.openfiscal.payment.application.port.out.InvoiceQueryPort;
import com.openfiscal.payment.application.port.out.PaymentRepository;
import com.openfiscal.payment.application.service.FinancialReconciliationService;

// Compliance Ports & Services
import com.openfiscal.compliance.application.port.out.BlacklistQueryPort;
import com.openfiscal.compliance.application.port.out.ImmutableAuditLogPort;
import com.openfiscal.compliance.application.service.ComplianceAuditService;

/**
 * Centralized Spring configuration to inject infrastructure adapters
 * into framework-agnostic Application Services.
 */
@Configuration
public class DomainConfig {

    @Bean
    public DraftInvoiceService draftInvoiceService(InvoiceRepository invoiceRepository) {
        return new DraftInvoiceService(invoiceRepository);
    }

    @Bean
    public IssueInvoiceService issueInvoiceService(InvoiceRepository invoiceRepository, EventPublisher eventPublisher) {
        return new IssueInvoiceService(invoiceRepository, eventPublisher);
    }

    @Bean
    public FinancialReconciliationUseCase financialReconciliationUseCase(
            PaymentRepository paymentRepository,
            InvoiceQueryPort invoiceQueryPort) {
        return new FinancialReconciliationService(paymentRepository, invoiceQueryPort);
    }

    @Bean
    public ComplianceAuditService complianceAuditService(
            BlacklistQueryPort blacklistQueryPort,
            ImmutableAuditLogPort auditLogPort) {
        return new ComplianceAuditService(blacklistQueryPort, auditLogPort);
    }
}