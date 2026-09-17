# Map LineItem and Tax JPA Entities

**Date:** 2026-09-17
**Status:** Accepted
**Commit:** feat(infra): map LineItem and Tax JPA entities and remove hydration technical debt from persistence adapter
**ADR Number:** adr-2026-09-17-014
**Deciders:** Lead Architect, Juan S.

## Context

The infrastructure layer previously introduced a placeholder technical debt during `Invoice` hydration to bypass compilation errors without fully mapping the nested collections[cite: 1]. To persist the completely itemized billing model[cite: 4] and associated fiscal charges[cite: 5], we must introduce complete relational database mappings for the `LineItem` entity and `Tax` Value Object.

## Decision

Created `LineItemJpaEntity` to represent the individual rows and mapped it as a unidirectional `@OneToMany` collection within `InvoiceJpaEntity`. Created `TaxJpaEmbeddable` and mapped it as an `@ElementCollection` inside the line item, correctly recognizing that taxes act as strict Value Objects in our domain[cite: 5]. The `InvoicePersistenceAdapter` has been completely refactored to iterate and map these hierarchies seamlessly between the Domain and JPA layers.

## Consequences

- **Positive:** The placeholder logic is entirely removed[cite: 1]. Database persistence now perfectly mirrors the pure domain state.
- **Positive:** Cascading saves and eager fetching accurately restore complex Aggregate Roots instantly.
- **Negative:** Mapping overhead has significantly increased within the persistence adapter, introducing heavier memory transformation footprint.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                                           |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-17-014` is assigned permanently. Never reused, reassigned, or removed.                                         |
| **Commit Referential Integrity** | Commit field references: `feat(infra): map LineItem and Tax JPA entities and remove hydration technical debt from persistence adapter` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                       |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                            |

**Supersedes:** None
