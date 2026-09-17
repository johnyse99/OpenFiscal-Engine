# Creation of Tax Value Object for Fiscal Charges

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): implement immutable Tax value object to securely represent fiscal charges
**ADR Number:** adr-2026-09-16-009
**Deciders:** Lead Architect, Juan S.

## Context

Following the robust definitions outlined in the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), a billing engine must be capable of tracking highly specific fiscal charges and withholdings (such as VAT or income tax)[cite: 3]. To support comprehensive itemized billing within the `LineItem` entity, we require a domain object that binds a tax nomenclature, its rate, and its mathematically precise calculated value without relying on primitives that could introduce rounding errors[cite: 1, 3].

## Decision

Created the `Tax` Value Object in the `com.openfiscal.core.domain` package. It utilizes `BigDecimal` for the exact percentage rate and strictly requires our `Money` Value Object for the evaluated financial amount. The object is immutable and self-validating, enforcing that tax rates cannot be negative and names cannot be empty.

## Consequences

- **Positive:** Fiscal charges are strongly typed and rely on the previously validated `Money` object for ultimate mathematical precision.
- **Positive:** Localization plugins can map country-specific taxes (e.g., IVA in Mexico, specific VATs in China) directly into this universal representation[cite: 3].
- **Negative:** Adding taxes to a `LineItem` will now require the Application layer to calculate the `Money` amount explicitly before instantiation.

## Compliance

This ADR complies with the following IFMP registry invariants:

| Invariant                        | Verification                                                                                                     |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-16-009` is assigned permanently. Never reused, reassigned, or removed.                   |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement immutable Tax value object to securely represent fiscal charges` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                 |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                      |

**Supersedes:** None
