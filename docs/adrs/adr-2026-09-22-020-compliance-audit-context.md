# Implementation of Compliance & Audit Service and Anti-Evasion Outbound Ports

**Date:** 2026-09-22
**Status:** Accepted
**Commit:** feat(compliance): implement ComplianceAuditService with Blacklist and ImmutableAuditLog outbound ports for anti-evasion tracking
**ADR Number:** adr-2026-09-22-020
**Deciders:** Lead Architect, Juan S.

## Context

Following the implementation of the matching engine, the system requires an automated mechanism to prevent fraud and track evasion attempts, transforming it into a real-time auditor as specified in Arquitectura Hexagonal para openfiscal-engine.docx. We must detect `SimulatedOperation` instances using government blacklists and log `FiscalDiscrepancy` alerts if financial reconciliation fails. Crucially, any generated alerts must be written to an `Immutable Audit Log` to guarantee non-repudiation.

## Decision

Created the `ComplianceAuditService` to orchestrate fraud detection rules. Introduced two critical outbound ports: `BlacklistQueryPort` to dynamically check government registries (e.g., Article 69-B EFOS/EDOS lists in Mexico) and `ImmutableAuditLogPort` to persist the `FraudAlert` domain entity.

## Consequences

- **Positive:** The system can now programmatically halt or flag suspicious transactions by crossing fiscal data with banking reconciliation results.
- **Positive:** All compliance events are funneled through a strict abstraction (`ImmutableAuditLogPort`), paving the way for cryptographic or blockchain-based adapters.
- **Negative:** Introduces external network dependencies on government APIs for the blacklist checks, requiring resilient retry mechanisms in the infrastructure layer.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                                                |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-22-020` is assigned permanently. Never reused, reassigned, or removed.                                                              |
| **Commit Referential Integrity** | Commit field references: `feat(compliance): implement ComplianceAuditService with Blacklist and ImmutableAuditLog outbound ports for anti-evasion tracking` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                                            |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                                                 |

**Supersedes:** None
