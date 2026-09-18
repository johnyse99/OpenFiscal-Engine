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
package com.openfiscal.payment.infrastructure.adapter.out.query;

import com.openfiscal.core.domain.Money;
import com.openfiscal.payment.application.port.out.InvoiceQueryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter to query the Core Billing Context without entity
 * coupling[cite: 3].
 * Utilizes JdbcTemplate to cross context boundaries cleanly.
 */
@Component
public class InvoiceQueryAdapter implements InvoiceQueryPort {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceQueryAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean hasMatchingIssuedInvoice(Money amount, String trackingKey) {
        // In this iteration, the banking trackingKey is mapped directly to the Invoice
        // UUID.
        String sql = "SELECT COUNT(id) FROM invoices WHERE id = ?::uuid AND total_amount_minor = ? AND status = 'ISSUED'";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, trackingKey, amount.getAmount());
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }
}