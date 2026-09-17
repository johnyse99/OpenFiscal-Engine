# Infrastructure Adapter for PostgreSQL and JPA Persistence

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(infra): implement PostgreSQL JPA persistence adapter for InvoiceRepository port
**ADR Number:** adr-2026-09-17-013
**Deciders:** Lead Architect, Juan S.

## Context

Following the Hexagonal Architecture principles established in the "PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO)", the core domain must remain completely unaware of external database technologies. However, the `IssueInvoiceService` and `DraftInvoiceService` use cases rely on the `InvoiceRepository` outbound port to save and retrieve the `Invoice` Aggregate Root[cite: 1, 2]. We must implement an infrastructure-level adapter to connect this port to our target database (PostgreSQL) using Spring Data JPA.

## Decision

Created the `InvoicePersistenceAdapter` which implements the `InvoiceRepository` interface. This adapter maps the pure `Invoice` domain object to a framework-specific `InvoiceJpaEntity` and delegates persistence to `SpringDataInvoiceRepository`.

## Consequences

- **Positive:** The core domain (`com.openfiscal.core.domain`) and application layer remain 100% free of Spring (`@Entity`, `@Table`) or database dependencies.
- **Positive:** The persistence technology can be swapped (e.g., to MongoDB) in the future simply by creating a new adapter.
- **Negative:** Introduces mapping overhead. Data must be translated between Domain Entities and JPA Entities every time it crosses the infrastructure boundary.
- **Note:** Hydrating a complex Aggregate Root (with collections of `LineItem` and `Tax`) from relational tables requires careful mapping logic, which will be expanded in the mapper.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                    |
| -------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-013` is assigned permanently. Never reused, reassigned, or removed.                  |
| **Commit Referential Integrity** | Commit field references: `feat(infra): implement PostgreSQL JPA persistence adapter for InvoiceRepository port` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                     |

**Supersedes:** None
