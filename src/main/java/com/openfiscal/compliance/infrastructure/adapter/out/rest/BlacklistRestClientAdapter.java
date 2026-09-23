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
package com.openfiscal.compliance.infrastructure.adapter.out.rest;

import com.openfiscal.compliance.application.port.out.BlacklistQueryPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Outbound Infrastructure Adapter for querying external government blacklists
 * via REST.
 */
@Component
public class BlacklistRestClientAdapter implements BlacklistQueryPort {

    private final RestTemplate restTemplate;
    private final String blacklistApiUrl = "https://api.sat.gob.mx/fiscal/v1/blacklist/"; // Example URL

    public BlacklistRestClientAdapter(RestTemplate restTemplate) {
        this.restTemplate = Objects.requireNonNull(restTemplate, "RestTemplate cannot be null");
    }

    @Override
    public boolean isBlacklisted(String taxId) {
        try {
            // Simplified logic: queries external API to check if TaxId is on EFOS/EDOS list
            String response = restTemplate.getForObject(blacklistApiUrl + taxId, String.class);
            return response != null && response.contains("\"status\":\"BLACKLISTED\"");
        } catch (Exception e) {
            // In a production scenario, implement resilience (circuit breaker, retries)
            return false;
        }
    }
}