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
package com.openfiscal.core.infrastructure.adapter.out.persistence;

import com.openfiscal.core.application.port.out.InvoiceRepository;
import com.openfiscal.core.domain.Invoice;
import com.openfiscal.core.domain.LineItem;
import com.openfiscal.core.domain.Money;
import com.openfiscal.core.domain.TaxId;
import com.openfiscal.core.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.openfiscal.core.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Secondary adapter connecting the pure InvoiceRepository port to Spring Data
 * JPA.
 */
@Component
public class InvoicePersistenceAdapter implements InvoiceRepository {

    private final SpringDataInvoiceRepository repository;

    public InvoicePersistenceAdapter(SpringDataInvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Invoice invoice) {
        InvoiceJpaEntity entity = toJpaEntity(invoice);
        repository.save(entity);
    }

    @Override
    public Optional<Invoice> findById(UUID id) {
        return repository.findById(id).map(this::toDomainEntity);
    }

    // --- Private Mapping Logic (Prevents Domain contamination) ---

    private InvoiceJpaEntity toJpaEntity(Invoice invoice) {
        return new InvoiceJpaEntity(
                invoice.getId(),
                invoice.getStatus().name(),
                invoice.getTotalAmount().getAmount(),
                invoice.getTotalAmount().getCurrency(),
                invoice.getIssuer().getValue(),
                invoice.getReceiver().getValue());
    }

    private Invoice toDomainEntity(InvoiceJpaEntity entity) {
        Money totalAmount = Money.ofMinorUnit(entity.getTotalAmountMinor(), entity.getCurrencyCode());
        TaxId issuer = TaxId.of(entity.getIssuerTaxId());
        TaxId receiver = TaxId.of(entity.getReceiverTaxId());

        // FIX: Satisfy the domain invariants for LineItems during hydration.
        // Once LineItemJpaEntity is mapped, we will iterate over those instead.
        LineItem placeholderItem = LineItem.create(
                "Database Hydration Placeholder",
                1,
                totalAmount,
                Collections.emptyList());

        return Invoice.createDraft(issuer, receiver, List.of(placeholderItem));
    }
}