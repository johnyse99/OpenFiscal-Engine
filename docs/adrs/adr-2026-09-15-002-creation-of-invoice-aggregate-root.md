# Creation of Invoice Aggregate Root

**Date:** 2026-09-15
**Status:** Accepted
**Commit:** feat(core): implement Invoice aggregate root skeleton in Java domain layer
**ADR Number:** adr-2026-09-15-002
**Deciders:** Lead Architect, Juan S.

## Context

The core billing engine requires a central entity to manage the lifecycle and validation of a fiscal document independently of any country's specific tax legislation. This entity must enforce strict state transitions (e.g., an invoice cannot be issued twice) and serve as the consistency boundary for all internal line items and calculations.

## Decision

Created the `Invoice` class as an Aggregate Root within the `com.openfiscal.core.domain` package. The class enforces immutability for its identifier (`UUID`) and restricts state transitions through domain-specific methods (e.g., `issue()`) rather than public setters. To guarantee mathematical precision, financial fields (totals) are intentionally deferred until a robust `Money` Value Object is implemented.

## Consequences

- **Positive:** The domain rules for an invoice's lifecycle are encapsulated and easily testable without loading Spring Boot or a database context.
- **Positive:** Prevents invalid state transitions at compile/runtime.
- **Negative:** Requires developers to use Factory Methods (`createDraft()`) instead of standard constructors, adding slight verbosity.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                          |
| -------------------------------- | ----------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-15-002` is assigned permanently. Never reused, reassigned, or removed.        |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement Invoice aggregate root skeleton in Java domain layer` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                      |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.           |

**Supersedes:** None
