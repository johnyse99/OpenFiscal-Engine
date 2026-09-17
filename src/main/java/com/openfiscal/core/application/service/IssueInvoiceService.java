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
package com.openfiscal.core.application.service;

import com.openfiscal.core.application.port.out.EventPublisher;
import com.openfiscal.core.application.port.out.InvoiceRepository;
import com.openfiscal.core.domain.Invoice;
import com.openfiscal.core.domain.events.InvoiceIssuedDomainEvent;

import java.util.UUID;

/**
 * Application Service orchestrating the transition of an Invoice from DRAFT to
 * ISSUED.
 */
public class IssueInvoiceService {

    private final InvoiceRepository repository;
    private final EventPublisher eventPublisher;

    public IssueInvoiceService(InvoiceRepository repository, EventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public void issue(IssueInvoiceCommand command) {
        // 1. Retrieve the Aggregate Root (assuming an implicitly added findById in the
        // repository)
        Invoice invoice = repository.findById(UUID.fromString(command.invoiceId()))
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // 2. Execute Domain Logic
        invoice.issue();

        // 3. Persist State Change
        repository.save(invoice);

        // 4. Create and Dispatch Domain Event for secondary Bounded Contexts
        InvoiceIssuedDomainEvent event = InvoiceIssuedDomainEvent.create(
                invoice.getId(),
                invoice.getIssuer().getValue(),
                invoice.getReceiver().getValue(),
                invoice.getTotalAmount().getAmount(),
                invoice.getTotalAmount().getCurrency());

        eventPublisher.publish(event);
    }

    // --- Input DTO (Record) ---
    public record IssueInvoiceCommand(String invoiceId) {
    }
}