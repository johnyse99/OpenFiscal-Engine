# Application Service for Drafting Invoices

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): implement DraftInvoiceService application use case and InvoiceRepository outbound port
**ADR Number:** adr-2026-09-16-011
**Deciders:** Lead Architect, Juan S.

## Context

The domain entities are now structurally complete. The Application Service responsible for drafting an invoice must now fully orchestrate and instantiate all `LineItem` entities before creating the `Invoice` Aggregate Root[cite: 2]. Furthermore, application services must now calculate and instantiate `Tax` objects for every single line item before creating the `LineItem` entity[cite: 8]. We require a Use Case orchestrator to accept primitive external requests (DTOs), map them into our rigorous Value Objects (`Money`, `TaxId`), construct the Aggregate Root, and delegate persistence to an outbound port.

## Decision

Created the `DraftInvoiceService` and its nested DTO records (`DraftInvoiceCommand`) in the `com.openfiscal.core.application.service` package. This service maps primitive inputs to domain objects and executes `Invoice.createDraft()`. Concurrently, defined the `InvoiceRepository` interface in `com.openfiscal.core.application.port.out` to establish the strict persistence contract without coupling the core to a database framework.

## Consequences

- **Positive:** The core domain remains pure; the application layer successfully shields it from primitive strings and numbers.
- **Positive:** Enforces the rule that the total amount of an invoice should not be passed arbitrarily; it must strictly equal the sum of its internal line items[cite: 2].
- **Negative:** Introduces data mapping boilerplate (translating `LineItemCommand` to `LineItem`, etc.) which slightly increases the layer's verbosity.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                  |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-16-011` is assigned permanently. Never reused, reassigned, or removed.                                |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement DraftInvoiceService application use case and InvoiceRepository outbound port` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                              |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                   |

**Supersedes:** None
