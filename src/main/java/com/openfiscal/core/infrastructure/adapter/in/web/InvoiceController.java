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
package com.openfiscal.core.infrastructure.adapter.in.web;

import com.openfiscal.core.application.service.DraftInvoiceService;
import com.openfiscal.core.application.service.IssueInvoiceService;
import com.openfiscal.core.domain.Invoice;
import com.openfiscal.core.infrastructure.adapter.in.web.dto.DraftInvoiceRequestDto;
import com.openfiscal.core.infrastructure.adapter.in.web.dto.IssueInvoiceRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Driving (Inbound) Infrastructure Adapter handling HTTP requests for the Core
 * Billing Context.
 */
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final DraftInvoiceService draftInvoiceService;
    private final IssueInvoiceService issueInvoiceService;

    public InvoiceController(DraftInvoiceService draftInvoiceService, IssueInvoiceService issueInvoiceService) {
        this.draftInvoiceService = Objects.requireNonNull(draftInvoiceService, "DraftInvoiceService cannot be null");
        this.issueInvoiceService = Objects.requireNonNull(issueInvoiceService, "IssueInvoiceService cannot be null");
    }

    @PostMapping("/draft")
    public ResponseEntity<UUID> draftInvoice(@RequestBody DraftInvoiceRequestDto requestDto) {
        Invoice draftedInvoice = draftInvoiceService.draft(requestDto.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(draftedInvoice.getId());
    }

    @PostMapping("/{invoiceId}/issue")
    public ResponseEntity<Void> issueInvoice(@PathVariable String invoiceId) {
        IssueInvoiceRequestDto requestDto = new IssueInvoiceRequestDto(invoiceId);
        issueInvoiceService.issue(requestDto.toCommand());
        return ResponseEntity.ok().build();
    }
}
