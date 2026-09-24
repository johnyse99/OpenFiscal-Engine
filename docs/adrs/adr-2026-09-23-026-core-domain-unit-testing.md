# Core Domain Unit Testing Strategy

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** test(core): implement JUnit 5 tests to verify Invoice aggregate invariants and financial calculations
**ADR Number:** adr-2026-09-23-026
**Deciders:** Lead Architect, Juan S.

## Context

With the core fiscal engine mathematically bounded by strict invariants (preventing IEEE 754 floating-point errors, self-billing, and invalid state transitions), these business rules must be continually verified. Relying solely on manual testing or full Spring context integration tests is slow and brittle.

## Decision

Implemented pure unit tests using JUnit 5 in the `src/test/java/.../core/domain/` directory. These tests instantiate the `Invoice`, `Money`, and `LineItem` aggregates directly in memory without loading the Spring framework, databases, or mock web servers.

## Consequences

- **Positive:** Ensures immediate feedback during development. Business rules (like accurate minor-unit arithmetic) are cryptographically enforced by the test suite.
- **Positive:** The tests execute in milliseconds, acting as live documentation of the domain's ubiquitous language and expected behavior.
- **Negative:** Domain unit tests do not verify infrastructure adapter correctness (e.g., JPA mappings or REST payload serialization), requiring separate Integration Tests later.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                                     |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-026` is assigned permanently. Never reused, reassigned, or removed.                                   |
| **Commit Referential Integrity** | Commit field references: `test(core): implement JUnit 5 tests to verify Invoice aggregate invariants and financial calculations` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                 |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                      |

**Supersedes:** None
