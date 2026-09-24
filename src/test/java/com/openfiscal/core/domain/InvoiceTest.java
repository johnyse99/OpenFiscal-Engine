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
package com.openfiscal.core.domain;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void shouldCreateDraftInvoiceAndCalculateTotalAccurately() {
        TaxId issuer = TaxId.of("ISSUER-123");
        TaxId receiver = TaxId.of("RECEIVER-456");

        Money unitPrice = Money.ofMinorUnit(BigInteger.valueOf(10050), "MXN"); // $100.50
        Tax tax = Tax.create("VAT", new java.math.BigDecimal("0.16"), Money.ofMinorUnit(1608, "MXN")); // $16.08
        LineItem item1 = LineItem.create("Consulting Services", 2, unitPrice, List.of(tax)); // Total item 1: 2 * 100.50
                                                                                             // + 16.08 = 217.08 (21708
                                                                                             // minor)

        LineItem item2 = LineItem.create("Software License", 1, Money.ofMinorUnit(50000, "MXN"),
                Collections.emptyList()); // Total item 2: 500.00 (50000 minor)

        Invoice invoice = Invoice.createDraft(issuer, receiver, List.of(item1, item2));

        assertNotNull(invoice.getId());
        assertEquals(Invoice.InvoiceStatus.DRAFT, invoice.getStatus());
        assertEquals("MXN", invoice.getTotalAmount().getCurrency());
        assertEquals(BigInteger.valueOf(71708), invoice.getTotalAmount().getAmount()); // $717.08
    }

    @Test
    void shouldThrowExceptionWhenIssuerAndReceiverAreIdentical() {
        TaxId identicalTaxId = TaxId.of("SAME-TAX-ID");
        Money unitPrice = Money.ofMinorUnit(1000, "MXN");
        LineItem item = LineItem.create("Test", 1, unitPrice, Collections.emptyList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Invoice.createDraft(identicalTaxId, identicalTaxId, List.of(item));
        });

        assertEquals("Issuer and Receiver cannot have the same Tax ID.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIssuingAlreadyIssuedInvoice() {
        TaxId issuer = TaxId.of("ISSUER-123");
        TaxId receiver = TaxId.of("RECEIVER-456");
        LineItem item = LineItem.create("Test", 1, Money.ofMinorUnit(1000, "MXN"), Collections.emptyList());

        Invoice invoice = Invoice.createDraft(issuer, receiver, List.of(item));
        invoice.issue();

        IllegalStateException exception = assertThrows(IllegalStateException.class, invoice::issue);

        assertEquals("Only a DRAFT invoice can be issued.", exception.getMessage());
    }
}
