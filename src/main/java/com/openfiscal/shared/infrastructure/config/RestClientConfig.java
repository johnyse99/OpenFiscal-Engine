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

/*
 * OpenFiscal-Engine
 *
 * Author: Johny Se
 * Contact: https://github.com/johnyse99
 *
 * 📄 License This project is distributed under the MIT license. 
 * Its purpose is strictly educational and research-based, developed as an Applied Data Science solution.
 *
 * Note for recruiters: This repository demonstrates advanced software architecture patterns 
 * (DDD, Hexagonal) applied to high-precision financial systems.
 */
package com.openfiscal.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring configuration providing HTTP client beans for outbound infrastructure
 * adapters.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
