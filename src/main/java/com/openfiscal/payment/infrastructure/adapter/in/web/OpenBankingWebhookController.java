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
package com.openfiscal.payment.infrastructure.adapter.in.web;

import com.openfiscal.payment.application.port.in.FinancialReconciliationUseCase;
import com.openfiscal.payment.infrastructure.adapter.in.web.dto.BankWebhookPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Driving (Inbound) Infrastructure Adapter handling Open Banking Webhooks.
 * Receives external bank payment notifications and delegates to the Financial
 * Reconciliation Use Case.
 */
@RestController
@RequestMapping("/api/v1/payments/webhooks")
public class OpenBankingWebhookController {

    private final FinancialReconciliationUseCase reconciliationUseCase;

    public OpenBankingWebhookController(FinancialReconciliationUseCase reconciliationUseCase) {
        this.reconciliationUseCase = Objects.requireNonNull(reconciliationUseCase,
                "FinancialReconciliationUseCase cannot be null");
    }

    @PostMapping("/bank-transfer")
    public ResponseEntity<Void> receiveBankWebhook(@RequestBody BankWebhookPayload payload) {
        reconciliationUseCase.reconcile(payload.toCommand());
        return ResponseEntity.accepted().build();
    }
}