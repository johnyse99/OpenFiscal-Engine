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

import com.openfiscal.core.application.port.out.InvoiceRepository;
import com.openfiscal.core.domain.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service orchestrating the creation of a Draft Invoice.
 */
public class DraftInvoiceService {

    private final InvoiceRepository repository;

    public DraftInvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public Invoice draft(DraftInvoiceCommand command) {
        TaxId issuer = TaxId.of(command.issuerId());
        TaxId receiver = TaxId.of(command.receiverId());

        List<LineItem> items = command.items().stream().map(itemCmd -> {
            Money unitPrice = Money.ofMinorUnit(itemCmd.unitPriceMinor(), itemCmd.currencyCode());

            List<Tax> taxes = itemCmd.taxes().stream().map(taxCmd -> {
                Money taxAmount = Money.ofMinorUnit(taxCmd.taxAmountMinor(), itemCmd.currencyCode());
                return Tax.create(taxCmd.name(), taxCmd.rate(), taxAmount);
            }).collect(Collectors.toList());

            return LineItem.create(itemCmd.description(), itemCmd.quantity(), unitPrice, taxes);
        }).collect(Collectors.toList());

        Invoice invoice = Invoice.createDraft(issuer, receiver, items);

        repository.save(invoice);

        return invoice;
    }

    // --- Input DTOs (Records) ---

    public record DraftInvoiceCommand(
            String issuerId,
            String receiverId,
            List<LineItemCommand> items) {
    }

    public record LineItemCommand(
            String description,
            int quantity,
            BigInteger unitPriceMinor,
            String currencyCode,
            List<TaxCommand> taxes) {
    }

    public record TaxCommand(
            String name,
            BigDecimal rate,
            BigInteger taxAmountMinor) {
    }
}