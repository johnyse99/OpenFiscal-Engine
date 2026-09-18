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
package com.openfiscal.payment.infrastructure.adapter.out.persistence;

import com.openfiscal.core.domain.Money;
import com.openfiscal.payment.application.port.out.PaymentRepository;
import com.openfiscal.payment.domain.PaymentEvent;
import com.openfiscal.payment.infrastructure.adapter.out.persistence.entity.PaymentJpaEntity;
import com.openfiscal.payment.infrastructure.adapter.out.persistence.repository.SpringDataPaymentRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Secondary adapter connecting the pure PaymentRepository port to Spring Data
 * JPA[cite: 3].
 */
@Component
public class PaymentPersistenceAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository repository;

    public PaymentPersistenceAdapter(SpringDataPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(PaymentEvent paymentEvent) {
        PaymentJpaEntity entity = new PaymentJpaEntity(
                paymentEvent.getId(),
                paymentEvent.getAmount().getAmount(),
                paymentEvent.getAmount().getCurrency(),
                paymentEvent.getTrackingKey(),
                paymentEvent.getOccurredOn(),
                paymentEvent.getStatus().name());
        repository.save(entity);
    }

    @Override
    public Optional<PaymentEvent> findById(UUID id) {
        return repository.findById(id).map(this::toDomainEntity);
    }

    @Override
    public Optional<PaymentEvent> findByTrackingKey(String trackingKey) {
        return repository.findByTrackingKey(trackingKey).map(this::toDomainEntity);
    }

    private PaymentEvent toDomainEntity(PaymentJpaEntity entity) {
        Money amount = Money.ofMinorUnit(entity.getAmountMinor(), entity.getCurrencyCode());
        PaymentEvent payment = PaymentEvent.create(amount, entity.getTrackingKey(), entity.getOccurredOn());

        // Hydration utilizing reflection to preserve the original UUID generated during
        // persistence
        // without compromising the domain logic encapsulation.
        try {
            java.lang.reflect.Field idField = PaymentEvent.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(payment, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to hydrate PaymentEvent ID", e);
        }

        if ("RECONCILED".equals(entity.getStatus()))
            payment.markAsReconciled();
        if ("UNMATCHED".equals(entity.getStatus()))
            payment.markAsUnmatched();

        return payment;
    }
}