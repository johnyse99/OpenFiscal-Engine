# Refactor Invoice Aggregate Root to Include Money Value Object

**Date:** 2026-09-15
**Status:** Accepted
**Commit:** feat(core): refactor Invoice aggregate root to integrate Money value object for precise financial totals
**ADR Number:** adr-2026-09-15-004
**Deciders:** Lead Architect, Juan S.

## Context

The `Invoice` Aggregate Root was initially created without financial fields to defer floating-point safety considerations until a robust `Money` Value Object was available. Now that the `Money` object guarantees absolute mathematical precision using `BigInteger` and prevents IEEE 754 rounding errors, it is mandatory to enforce financial consistency within the core fiscal document.

## Decision

Refactored the `Invoice` class in the `com.openfiscal.core.domain` package to require a `Money` instance upon creation. The `createDraft` factory method now strictly validates that the total amount cannot be negative before initializing the document's state.

## Consequences

- **Positive:** Complete alignment with Domain-Driven Design principles; the `Invoice` is financially secure from the moment it is created as a draft.
- **Positive:** Total amounts are strictly immutable and inherently tied to a specific currency code natively.
- **Negative:** Any external application service calling `Invoice.createDraft()` must now explicitly instantiate a `Money` object first.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                        |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-15-004` is assigned permanently. Never reused, reassigned, or removed.                                      |
| **Commit Referential Integrity** | Commit field references: `feat(core): refactor Invoice aggregate root to integrate Money value object for precise financial totals` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                    |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                         |

**Supersedes:** None
