# Refactor LineItem Entity to Encapsulate Tax Objects

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): refactor LineItem entity to encapsulate a collection of Tax value objects
**ADR Number:** adr-2026-09-16-010
**Deciders:** Lead Architect, Juan S.

## Context

A billing engine must be capable of tracking highly specific fiscal charges and withholdings[cite: 2]. To fully comply with the comprehensive itemized billing requirements of the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), the `LineItem` entity must be able to hold the `Tax` Value Objects associated with its specific goods or services[cite: 3, 5].

## Decision

Refactored the `LineItem` entity in the `com.openfiscal.core.domain` package to encapsulate a `List<Tax>`[cite: 3]. The `create` factory method now strictly requires this list upon instantiation, ensuring that no line item can be added to an invoice without its tax configuration explicitly declared (even if the list is empty for tax-exempt items).

## Consequences

- **Positive:** Absolute clarity in itemized billing; taxes are bound directly to the items that generate them.
- **Positive:** The system is now structurally capable of accommodating complex localization rules where different items on the same invoice have different tax rates.
- **Negative:** Application services must now calculate and instantiate `Tax` objects for every single line item before creating the `LineItem` entity.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                     |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-16-010` is assigned permanently. Never reused, reassigned, or removed.                   |
| **Commit Referential Integrity** | Commit field references: `feat(core): refactor LineItem entity to encapsulate a collection of Tax value objects` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                 |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                      |

**Supersedes:** None
