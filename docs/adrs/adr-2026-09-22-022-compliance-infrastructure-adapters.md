# Implementation of Compliance Infrastructure Adapters

**Date:** 2026-09-22
**Status:** Accepted
**Commit:** feat(compliance): implement BlacklistRestClient and AuditLogJpaAdapter for anti-evasion infrastructure
**ADR Number:** adr-2026-09-22-022
**Deciders:** Lead Architect, Juan S.

## Context

With the `ComplianceAuditService` defined to execute business rules for fraud detection, the core domain depends on two outbound ports: `BlacklistQueryPort` and `ImmutableAuditLogPort`. These ports require concrete infrastructure implementations to connect to real government REST APIs and a persistent database, ensuring the audit alerts are accurately evaluated and safely stored.

## Decision

Implemented `BlacklistRestClientAdapter` using Spring's `RestTemplate` to handle external API HTTP requests to government endpoints (e.g., Article 69-B lists). Implemented `AuditLogJpaAdapter` using Spring Data JPA to persist `FraudAlert` entities into a relational database, acting as the foundational layer for our WORM (Write-Once-Read-Many) storage strategy.

## Consequences

- **Positive:** The Compliance bounded context is now fully connected to external data sources and persistence mechanisms.
- **Positive:** The core domain remains agnostic to Spring Boot, REST protocols, and SQL dialects.
- **Negative:** The REST client introduces a network dependency. If the external government API is down, the billing engine could face timeouts unless Circuit Breaker patterns are introduced.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                                      |
| -------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-22-022` is assigned permanently. Never reused, reassigned, or removed.                                    |
| **Commit Referential Integrity** | Commit field references: `feat(compliance): implement BlacklistRestClient and AuditLogJpaAdapter for anti-evasion infrastructure` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                  |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                       |

**Supersedes:** None
