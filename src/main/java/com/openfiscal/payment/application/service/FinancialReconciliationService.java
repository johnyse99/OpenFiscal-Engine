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
package com.openfiscal.payment.application.service;

import com.openfiscal.core.domain.Money;
import com.openfiscal.payment.application.port.in.FinancialReconciliationUseCase;
import com.openfiscal.payment.application.port.out.InvoiceQueryPort;
import com.openfiscal.payment.application.port.out.PaymentRepository;
import com.openfiscal.payment.domain.PaymentEvent;

import java.util.Objects;

/**
 * Application Service implementing the Financial Reconciliation Matching
 * Engine.
 * Orchestrates the verification and status transition of incoming payment
 * events.
 */
public class FinancialReconciliationService implements FinancialReconciliationUseCase {

    private final PaymentRepository paymentRepository;
    private final InvoiceQueryPort invoiceQueryPort;

    public FinancialReconciliationService(PaymentRepository paymentRepository, InvoiceQueryPort invoiceQueryPort) {
        this.paymentRepository = Objects.requireNonNull(paymentRepository, "PaymentRepository cannot be null");
        this.invoiceQueryPort = Objects.requireNonNull(invoiceQueryPort, "InvoiceQueryPort cannot be null");
    }

    @Override
    public void reconcile(ProcessPaymentCommand command) {
        Money paymentAmount = Money.ofMinorUnit(command.amountMinor(), command.currencyCode());
        PaymentEvent paymentEvent = PaymentEvent.create(paymentAmount, command.trackingKey(), command.occurredOn());

        boolean isMatched = invoiceQueryPort.hasMatchingIssuedInvoice(paymentAmount, command.trackingKey());

        if (isMatched) {
            paymentEvent.markAsReconciled();
        } else {
            paymentEvent.markAsUnmatched();
        }

        paymentRepository.save(paymentEvent);
    }
}
