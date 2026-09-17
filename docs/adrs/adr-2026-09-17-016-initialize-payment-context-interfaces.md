# Initialize Payment & Collection Context Interfaces

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(payment): initialize PaymentEvent aggregate root and FinancialReconciliationUseCase to handle banking inflows
**ADR Number:** adr-2026-09-17-016
**Deciders:** Lead Architect, Juan S.

## Context

As outlined in the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), the OpenFiscal Engine must transcend simple document generation and act as an automated financial auditor[cite: 3]. To support traceability, bank reconciliation, and mandatory payment methods like QR, CoDi, and SPEI, we must introduce the Payment & Collection Bounded Context[cite: 3]. We require a domain representation of a banking cash inflow and a use case contract to process it.

## Decision

Created the `PaymentEvent` Aggregate Root in the `com.openfiscal.payment.domain` package to represent an external, verifiable notification of cash flow[cite: 3]. It utilizes the universal `Money` Value Object from the Core Context for precision. Simultaneously, defined the `FinancialReconciliationUseCase` inbound port to establish the contract for the matching engine that will eventually associate these payments with issued invoices[cite: 3].

## Consequences

- **Positive:** Establishes clear structural boundaries between the Core Billing Context and the Payment Context.
- **Positive:** Provides the foundation for consuming Webhooks or MT940 files from Open Banking APIs[cite: 3].
- **Negative:** Requires cross-context coordination. The Payment Context must eventually query the Core Context to retrieve and update the corresponding `Invoice` status.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                                                  |
| -------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-016` is assigned permanently. Never reused, reassigned, or removed.                                                |
| **Commit Referential Integrity** | Commit field references: `feat(payment): initialize PaymentEvent aggregate root and FinancialReconciliationUseCase to handle banking inflows` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                              |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                                   |

**Supersedes:** None
