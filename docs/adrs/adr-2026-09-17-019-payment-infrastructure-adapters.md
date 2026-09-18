# Implementation of Payment Infrastructure Adapters

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(payment): implement secondary infrastructure adapters for payment persistence and cross-context invoice querying
**ADR Number:** adr-2026-09-17-019
**Deciders:** Lead Architect, Juan S.

## Context

Following the creation of the `FinancialReconciliationService` and its inbound/outbound ports, the Payment Context requires concrete infrastructure adapters to function. It needs an adapter to persist the state of a `PaymentEvent` (whether PENDING, RECONCILED, or UNMATCHED), and a mechanism to read from the Core Billing Context to verify if an issued invoice mathematically matches the incoming bank payload.

## Decision

Implemented `PaymentPersistenceAdapter` connecting the `PaymentRepository` port to Spring Data JPA using `PaymentJpaEntity`. Additionally, implemented `InvoiceQueryAdapter` using raw `JdbcTemplate`. This explicitly avoids importing the core `InvoiceJpaEntity` into the payment module, preserving Hexagonal boundaries between bounded contexts while operating within a monolithic database structure.

## Consequences

- **Positive:** The `FinancialReconciliationService` matching engine can now execute completely and verify transactions against the database.
- **Positive:** `InvoiceQueryAdapter` queries via standard JDBC, ensuring that the Payment Context does not become permanently coupled to the Core Context's specific ORM mappings.
- **Negative:** The hydration process in `PaymentPersistenceAdapter` requires localized reflection to inject the correct `UUID` without breaking the strict domain rules of `PaymentEvent.create()`.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                                                     |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Number Immutability**          | ADR number `adr-2026-09-17-019` is assigned permanently. Never reused, reassigned, or removed.                                                   |
| **Commit Referential Integrity** | Commit field references: `feat(payment): implement secondary infrastructure adapters for payment persistence and cross-context invoice querying` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                                 |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                                      |

**Supersedes:** None
