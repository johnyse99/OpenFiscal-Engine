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
import com.openfiscal.core.domain.*;
import com.openfiscal.core.infrastructure.adapter.out.persistence.entity.*;
import com.openfiscal.core.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    // --- Private Mapping Logic ---

    private InvoiceJpaEntity toJpaEntity(Invoice invoice) {
        List<LineItemJpaEntity> itemEntities = invoice.getItems().stream().map(item -> {
            List<TaxJpaEmbeddable> taxEntities = item.getTaxes().stream().map(tax -> new TaxJpaEmbeddable(
                    tax.getName(),
                    tax.getRate(),
                    tax.getAmount().getAmount())).collect(Collectors.toList());

            return new LineItemJpaEntity(
                    item.getId(),
                    item.getDescription(),
                    item.getQuantity(),
                    item.getUnitPrice().getAmount(),
                    item.getTotalAmount().getAmount(),
                    item.getUnitPrice().getCurrency(),
                    taxEntities);
        }).collect(Collectors.toList());

        return new InvoiceJpaEntity(
                invoice.getId(),
                invoice.getStatus().name(),
                invoice.getTotalAmount().getAmount(),
                invoice.getTotalAmount().getCurrency(),
                invoice.getIssuer().getValue(),
                invoice.getReceiver().getValue(),
                itemEntities);
    }

    private Invoice toDomainEntity(InvoiceJpaEntity entity) {
        TaxId issuer = TaxId.of(entity.getIssuerTaxId());
        TaxId receiver = TaxId.of(entity.getReceiverTaxId());

        List<LineItem> domainItems = entity.getItems().stream().map(itemEntity -> {
            Money unitPrice = Money.ofMinorUnit(itemEntity.getUnitPriceMinor(), itemEntity.getCurrencyCode());

            List<Tax> domainTaxes = itemEntity.getTaxes().stream().map(taxEntity -> {
                Money taxAmount = Money.ofMinorUnit(taxEntity.getAmountMinor(), itemEntity.getCurrencyCode());
                return Tax.create(taxEntity.getName(), taxEntity.getRate(), taxAmount);
            }).collect(Collectors.toList());

            return LineItem.create(itemEntity.getDescription(), itemEntity.getQuantity(), unitPrice, domainTaxes);
        }).collect(Collectors.toList());

        return Invoice.createDraft(issuer, receiver, domainItems); // Domain handles internal validation
    }
}