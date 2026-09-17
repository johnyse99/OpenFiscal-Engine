# Refactor Invoice Aggregate Root to Include Issuer and Receiver TaxIds

**Date:** 2026-09-16
**Status:** Accepted
**Commit:** feat(core): refactor Invoice aggregate root to inject TaxId for issuer and receiver
**ADR Number:** adr-2026-09-16-006
**Deciders:** Lead Architect, Juan S.

## Context

Following the creation of the universal `TaxId` Value Object, the `Invoice` Aggregate Root must be updated to securely map the identities of the participating taxpayers. A fiscal document fundamentally requires both an origin (Issuer) and a destination (Receiver) to be legally and structurally valid within the OpenFiscal Engine.

## Decision

Refactored the `Invoice` class in the `com.openfiscal.core.domain` package to require `TaxId` instances for both `issuer` and `receiver` upon creation. Added domain logic within the `createDraft` factory method to validate that the `issuer` and `receiver` are strictly not the same entity (preventing self-billing anomalies at the core level).

## Consequences

- **Positive:** The `Invoice` Aggregate Root is now structurally complete regarding its core financial (`Money`) and identity (`TaxId`) constraints.
- **Positive:** Prevents logical errors like issuing a fiscal document to oneself before the document can even enter the `DRAFT` state.
- **Negative:** Application services must now orchestrate the retrieval and instantiation of two `TaxId` Value Objects before drafting an invoice.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                   |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-16-006` is assigned permanently. Never reused, reassigned, or removed.                 |
| **Commit Referential Integrity** | Commit field references: `feat(core): refactor Invoice aggregate root to inject TaxId for issuer and receiver` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                               |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                    |

**Supersedes:** None
