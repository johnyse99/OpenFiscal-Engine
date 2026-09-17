# Creation of LineItem Entity for Itemized Billing

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): implement LineItem entity to support itemized billing
**ADR Number:** adr-2026-09-16-007
**Deciders:** Lead Architect, Juan S.

## Context

To comply with the comprehensive itemization requirements outlined in the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), the `Invoice` Aggregate Root must support individual transaction rows[cite: 5]. We need a domain entity to encapsulate the description, quantity, and financial calculations of a single item without relying on primitive floating-point types for pricing[cite: 2].

## Decision

Created the `LineItem` entity within the `com.openfiscal.core.domain` package. It utilizes the `Money` Value Object to guarantee immutable mathematical precision for the `unitPrice` and `totalAmount`[cite: 2]. The factory method strictly enforces that the quantity must be greater than zero and calculates the `totalAmount` automatically upon creation.

## Consequences

- **Positive:** Mathematical logic for row totals is safely encapsulated within the entity.
- **Positive:** The `Invoice` Aggregate Root can now be updated to hold a collection of `LineItem` entities.
- **Negative:** Adding a new item requires creating an instance of `Money` first, adding minor overhead to the application services.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                 |
| -------------------------------- | -------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number adr-2026-09-16-007 is assigned permanently. Never reused, reassigned, or removed. |
| **Commit Referential Integrity** | Commit field references: feat(core): implement LineItem entity to support itemized billing   |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.             |
| **Status Lifecycle**             | Current status: Accepted. Valid transitions: Proposed → Accepted / Rejected / Deprecated.    |

**Supersedes:** None
