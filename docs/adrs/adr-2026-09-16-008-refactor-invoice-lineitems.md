# Refactor Invoice Aggregate Root to Encapsulate LineItems

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): refactor Invoice aggregate root to encapsulate LineItem collection and auto-calculate totals
**ADR Number:** adr-2026-09-16-008
**Deciders:** Lead Architect, Juan S.

## Context

Following the creation of the `LineItem` entity, the `Invoice` Aggregate Root must be refactored to support full itemization[cite: 2]. A core principle of the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO) is that an Aggregate Root must guarantee its internal consistency[cite: 4]. Therefore, the total amount of an invoice should not be passed arbitrarily; it must strictly equal the sum of its internal line items[cite: 1, 2].

## Decision

Refactored the `Invoice` class in the `com.openfiscal.core.domain` package to encapsulate a `List<LineItem>`[cite: 5]. The `createDraft` factory method now requires a populated list of items and natively iterates over them, using the immutable `Money` Value Object's `.add()` method to mathematically compute the final `totalAmount`[cite: 2, 3].

## Consequences

- **Positive:** Absolute domain consistency. It is mathematically impossible to create a Draft Invoice where the total amount does not perfectly match the sum of its items.
- **Positive:** Enforcement of business invariants (an invoice can never be generated with zero items).
- **Negative:** The Application Service responsible for drafting an invoice must now fully orchestrate and instantiate all `LineItem` entities before creating the `Invoice` Aggregate Root.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                        |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-16-008` is assigned permanently. Never reused, reassigned, or removed.                                      |
| **Commit Referential Integrity** | Commit field references: `feat(core): refactor Invoice aggregate root to encapsulate LineItem collection and auto-calculate totals` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                    |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                         |

**Supersedes:** None
