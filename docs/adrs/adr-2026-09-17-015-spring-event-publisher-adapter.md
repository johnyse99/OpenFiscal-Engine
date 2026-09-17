# Spring Event Publisher Adapter for Domain Events

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(infra): implement SpringEventPublisherAdapter to dispatch domain events and close out issuing use case
**ADR Number:** adr-2026-09-17-015
**Deciders:** Lead Architect, Juan S.

## Context

The `IssueInvoiceService` orchestrates the transition of an `Invoice` from DRAFT to ISSUED and relies on an `EventPublisher` outbound port to dispatch the `InvoiceIssuedDomainEvent`[cite: 2]. As outlined in our system vision for the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), we need a robust infrastructure adapter to bridge this pure domain port to an event bus so that secondary Bounded Contexts (Payment, Compliance) can react immediately[cite: 2].

## Decision

Implemented `SpringEventPublisherAdapter` strictly using Spring's `ApplicationEventPublisher`. We deliberately deferred implementing a Kafka-based adapter at this stage to avoid unnecessary infrastructure overhead while running in a monolithic boundary.

## Consequences

- **Positive:** The Domain and Application layers remain 100% agnostic to Spring's event architecture[cite: 2].
- **Positive:** Lays the immediate reactive foundation necessary for the financial and auditing modules of the system[cite: 2]. Other modules can now safely use `@EventListener`.
- **Negative:** Events are currently bound to a single JVM memory space. A Kafka or RabbitMQ adapter implementation will be required when the OpenFiscal Engine is scaled out to a distributed microservices environment.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                               |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-015` is assigned permanently. Never reused, reassigned, or removed.             |
| **Commit Referential Integrity** | Commit field references: `feat(infra): implement SpringEventPublisherAdapter to dispatch domain events...` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                           |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                |

**Supersedes:** None
