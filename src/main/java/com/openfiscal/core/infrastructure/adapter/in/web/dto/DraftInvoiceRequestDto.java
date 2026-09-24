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
package com.openfiscal.core.infrastructure.adapter.in.web.dto;

import com.openfiscal.core.application.service.DraftInvoiceService.DraftInvoiceCommand;
import com.openfiscal.core.application.service.DraftInvoiceService.LineItemCommand;
import com.openfiscal.core.application.service.DraftInvoiceService.TaxCommand;

import java.util.List;
import java.util.stream.Collectors;

public record DraftInvoiceRequestDto(
        String issuerId,
        String receiverId,
        List<LineItemDto> items) {
    public DraftInvoiceCommand toCommand() {
        List<LineItemCommand> itemCommands = items.stream()
                .map(item -> new LineItemCommand(
                        item.description(),
                        item.quantity(),
                        item.unitPriceMinor(),
                        item.currencyCode(),
                        item.taxes().stream()
                                .map(tax -> new TaxCommand(tax.name(), tax.rate(), tax.taxAmountMinor()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        return new DraftInvoiceCommand(issuerId, receiverId, itemCommands);
    }

    public record LineItemDto(
            String description,
            int quantity,
            java.math.BigInteger unitPriceMinor,
            String currencyCode,
            List<TaxDto> taxes) {
    }

    public record TaxDto(
            String name,
            java.math.BigDecimal rate,
            java.math.BigInteger taxAmountMinor) {
    }
}