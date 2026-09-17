# Application Service for Issuing Invoices and Domain Events

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(core): implement IssueInvoiceService, EventPublisher port, and InvoiceIssuedDomainEvent to seal document lifecycle add missing findById method to InvoiceRepository port
**ADR Number:** adr-2026-09-17-012
**Deciders:** Lead Architect, Juan S.

## Context

A draft invoice is legally useless until it is officially issued[cite: 1]. The `Invoice` Aggregate Root requires a Use Case to orchestrate the state transition from DRAFT to ISSUED[cite: 1]. Furthermore, establishing an event-driven foundation is critical: we must fire an `InvoiceIssuedDomainEvent` to trigger secondary Bounded Contexts—such as notifying the Payment Context to generate a QR code, or the Compliance Context to run anti-evasion checks[cite: 1].

## Decision

Created the `IssueInvoiceService` application use case and the `InvoiceIssuedDomainEvent` in the core domain. To maintain architectural purity, introduced an `EventPublisher` outbound port to handle event dispatching without coupling the core to a specific message broker (like Kafka or RabbitMQ).

## Consequences

- **Positive:** The primary lifecycle of the `Invoice` Aggregate Root is fully closed[cite: 1].
- **Positive:** Lays the reactive, event-driven foundation necessary for the financial and auditing modules of the system[cite: 1].
- **Negative:** Requires external infrastructure to implement the `EventPublisher` interface, adding complexity to the final deployment.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                                                                                             |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-012` is assigned permanently. Never reused, reassigned, or removed.                                                                                                           |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement IssueInvoiceService, EventPublisher port, and InvoiceIssuedDomainEvent to seal document lifecycle add missing findById method to InvoiceRepository port` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                                                                                         |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                                                                                              |

**Supersedes:** None
