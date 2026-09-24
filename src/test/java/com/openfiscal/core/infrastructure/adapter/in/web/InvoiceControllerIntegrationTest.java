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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openfiscal.core.infrastructure.adapter.in.web.dto.DraftInvoiceRequestDto;
import com.openfiscal.core.infrastructure.adapter.in.web.dto.DraftInvoiceRequestDto.LineItemDto;
import com.openfiscal.core.infrastructure.adapter.in.web.dto.DraftInvoiceRequestDto.TaxDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDraftInvoiceAndReturnCreatedStatus() throws Exception {
        TaxDto tax = new TaxDto("VAT", new BigDecimal("0.16"), BigInteger.valueOf(1608));
        LineItemDto item = new LineItemDto("Consulting Services", 1, BigInteger.valueOf(10050), "MXN", List.of(tax));
        DraftInvoiceRequestDto request = new DraftInvoiceRequestDto("ISSUER-123", "RECEIVER-456", List.of(item));

        mockMvc.perform(post("/api/v1/invoices/draft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isString());
    }

    @Test
    void shouldReturnBadRequestWhenDomainInvariantFails() throws Exception {
        // Triggering the self-billing invariant constraint (Issuer == Receiver)
        LineItemDto item = new LineItemDto("Consulting Services", 1, BigInteger.valueOf(10050), "MXN", List.of());
        DraftInvoiceRequestDto request = new DraftInvoiceRequestDto("SAME-TAX-ID", "SAME-TAX-ID", List.of(item));

        mockMvc.perform(post("/api/v1/invoices/draft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Issuer and Receiver cannot have the same Tax ID."));
    }
}
