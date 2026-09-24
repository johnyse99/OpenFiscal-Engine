# Implementation of Core Billing REST Controller

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** feat(core): implement REST controller and DTOs for drafting and issuing invoices
**ADR Number:** adr-2026-09-23-024
**Deciders:** Lead Architect, Juan S.

## Context

The Core Billing Context possesses pure application services (`DraftInvoiceService`, `IssueInvoiceService`) capable of orchestrating the lifecycle of an `Invoice`. To expose this functionality to external clients (e.g., front-end applications, API consumers) and trigger the entire system flow (including subsequent Domain Events), we require an HTTP inbound adapter.

## Decision

Created the `InvoiceController` annotated with Spring's `@RestController` in the `com.openfiscal.core.infrastructure.adapter.in.web` package. Defined specific Request DTOs (`DraftInvoiceRequestDto`, `IssueInvoiceRequestDto`) to encapsulate incoming JSON payloads and map them to the corresponding Application Service Commands.

## Consequences

- **Positive:** The billing engine is now fully accessible via standard REST endpoints (`/api/v1/invoices/draft` and `/api/v1/invoices/{id}/issue`).
- **Positive:** Input parsing and framework-specific web logic are strictly isolated from the core domain.
- **Negative:** Exposing the issue endpoint requires robust authentication and authorization layers (e.g., OAuth2/JWT) in future iterations to prevent unauthorized fiscal document generation.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-024` is assigned permanently. Never reused, reassigned, or removed.              |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement REST controller and DTOs for drafting and issuing invoices` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                            |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                 |

**Supersedes:** None
