# Creation of TaxId Value Object

**Date:** 2026-09-15
**Status:** Accepted
**Commit:** feat(core): implement immutable TaxId value object to securely identify taxpayers globally
**ADR Number:** adr-2026-09-15-005
**Deciders:** Lead Architect, Juan S.

## Context

The `Invoice` Aggregate Root requires a secure, immutable way to identify the Issuer and the Receiver. Because the OpenFiscal Engine core domain must remain agnostic to any specific country's legislation, we cannot hardcode structures like the Mexican "RFC" into the core. We need a universal identifier that guarantees basic structural safety (non-null, non-empty) while allowing outer localization layers to enforce specific regex patterns.

## Decision

Created the `TaxId` Value Object in the `com.openfiscal.core.domain` package. It encapsulates a basic `String` value with immediate validation against null or empty states. It overrides `equals` and `hashCode` to ensure identity by value, not by memory reference.

## Consequences

- **Positive:** The `Invoice` can now securely link an Issuer and a Receiver using a strongly typed domain object instead of a primitive string.
- **Positive:** Adapters and localization plugins can safely map their specific IDs (e.g., RFC) into this universal `TaxId` representation.
- **Negative:** Outer layers must handle parsing and mapping before passing data into the core domain.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                        |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number adr-2026-09-15-005 is assigned permanently. Never reused, reassigned, or removed.                        |
| **Commit Referential Integrity** | Commit field references: feat(core): implement immutable TaxId value object to securely identify taxpayers globally |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                    |
| **Status Lifecycle**             | Current status: Accepted. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                           |

**Supersedes:** None
