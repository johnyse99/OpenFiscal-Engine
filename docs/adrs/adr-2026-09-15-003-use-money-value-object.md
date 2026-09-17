# Use of Money Value Object for Financial Amounts

**Date:** 2026-09-15
**Status:** Accepted
**Commit:** feat(core): implement immutable Money value object using BigInteger to prevent rounding errors
**ADR Number:** adr-2026-09-15-003
**Deciders:** Lead Architect, Juan S.

## Context

The billing engine will process millions of transactions involving strict tax calculations and currency conversions. Using primitive floating-point types (`float`, `double`) introduces IEEE 754 rounding errors, which are unacceptable in fiscal and financial compliance systems. We need absolute mathematical precision.

## Decision

Created the `Money` Value Object in the `com.openfiscal.core.domain` package. All financial amounts across the system MUST use this object. The internal state represents the amount in its smallest currency unit (e.g., cents) using `BigInteger` to ensure exact arithmetic. `Money` objects are strictly immutable; operations yield a new instance and strictly enforce currency matching.

## Consequences

- **Positive:** Complete elimination of floating-point arithmetic errors.
- **Positive:** Centralized validation (prevents adding MXN to USD natively).
- **Negative:** Developers must use methods like `.add()` instead of standard operators, increasing code verbosity slightly.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                              |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-15-003` is assigned permanently. Never reused, reassigned, or removed.                            |
| **Commit Referential Integrity** | Commit field references: `feat(core): implement immutable Money value object using BigInteger to prevent rounding errors` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                          |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                               |

**Supersedes:** None
