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
package com.openfiscal.payment.application.port.in;

import java.math.BigInteger;
import java.time.Instant;

/**
 * Inbound port orchestrating the algorithmic matching of a PaymentEvent
 * to a fiscal document (Invoice).
 */
public interface FinancialReconciliationUseCase {

    void reconcile(ProcessPaymentCommand command);

    record ProcessPaymentCommand(
            String trackingKey,
            BigInteger amountMinor,
            String currencyCode,
            Instant occurredOn) {
    }
}