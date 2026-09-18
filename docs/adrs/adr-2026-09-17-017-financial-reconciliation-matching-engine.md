# Financial Reconciliation Matching Engine Service

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(payment): implement FinancialReconciliationService and outbound ports for matching engine
**ADR Number:** adr-2026-09-17-017
**Deciders:** Lead Architect, Juan S.

## Context

Following the creation of the `PaymentEvent` aggregate root and the `FinancialReconciliationUseCase` inbound port in ADR-018, the system requires an application service to execute the actual matching logic. The engine must accept banking event inputs (e.g., SPEI/CoDi/QR tracking keys and amounts), query the Core Billing Context for corresponding issued invoices, update the payment status, and persist the outcome.

## Decision

Created `FinancialReconciliationService` implementing `FinancialReconciliationUseCase`. Introduced two outbound ports: `PaymentRepository` for storing `PaymentEvent` entities, and `InvoiceQueryPort` to abstract cross-context querying of issued invoices without directly coupling the Payment Context to Core Context JPA entities.

## Consequences

- **Positive:** Maintains strict hexagonal boundaries between the Core Billing Context and Payment Context.
- **Positive:** Encapsulates the algorithmic matching rules for cash flow reconciliation.
- **Negative:** Secondary infrastructure adapters will be required to implement `PaymentRepository` and `InvoiceQueryPort`.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                              |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-017` is assigned permanently. Never reused, reassigned, or removed.                            |
| **Commit Referential Integrity** | Commit field references: `feat(payment): implement FinancialReconciliationService and outbound ports for matching engine` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                          |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                               |

**Supersedes:** None
