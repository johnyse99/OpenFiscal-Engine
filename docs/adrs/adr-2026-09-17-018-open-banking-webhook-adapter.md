# Open Banking Webhook Infrastructure Adapter

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(payment): implement OpenBankingWebhookController adapter to receive payment webhooks
**ADR Number:** adr-2026-09-17-018
**Deciders:** Lead Architect, Juan S.

## Context

Following the implementation of `FinancialReconciliationService` in ADR-019, the Payment & Collection Context requires an inbound (driving) infrastructure adapter to receive banking cash flow notifications (webhooks from SPEI, CoDi, or Open Banking financial APIs).

## Decision

Implemented `OpenBankingWebhookController` as a Spring `@RestController` within the `com.openfiscal.payment.infrastructure.adapter.in.web` package. The controller accepts HTTP POST webhooks, maps `BankWebhookPayload` DTOs to `ProcessPaymentCommand` records, and invokes the `FinancialReconciliationUseCase` inbound port.

## Consequences

- **Positive:** Keeps HTTP framework concerns isolated in the infrastructure layer without leaking into the `FinancialReconciliationUseCase` port.
- **Positive:** Completes the end-to-end execution path from external bank Webhooks to domain state updates.
- **Negative:** Production deployments will require security filters (e.g., HMAC signature validation or Mutual TLS) added at the web layer.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                         |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-018` is assigned permanently. Never reused, reassigned, or removed.                       |
| **Commit Referential Integrity** | Commit field references: `feat(payment): implement OpenBankingWebhookController adapter to receive payment webhooks` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                     |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                          |

**Supersedes:** None
