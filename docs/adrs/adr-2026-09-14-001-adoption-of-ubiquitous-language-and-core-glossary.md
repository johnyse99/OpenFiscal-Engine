# Adoption of Ubiquitous Language and Core Glossary

**Date:** 2026-09-14
**Status:** Accepted
**Commit:** docs: add ubiquitous language glossary for core domain and compliance terms
**ADR Number:** adr-2026-09-14-001
**Deciders:** Lead Architect, Johny Se

## Context

The OpenFiscal Engine requires a strictly standardized vocabulary to prevent ambiguity across the core domain and localization plugins. Without a shared language, country-specific terms could leak into the universal engine, compromising the system's agnostic nature. This foundational alignment is critical for translating complex fiscal rules, as initially outlined in the PROYECTO ARQUITECTURA HEXAGONAL DDD (E-FAPIAO), into clean Domain-Driven Design components.

## Decision

Created the `docs/ubiquitous-language.md` file to serve as the definitive glossary for the project. The language establishes clear boundaries by categorizing terms into three distinct bounded contexts: Core Billing (e.g., `Invoice`, `Money`), Payment & Reconciliation (e.g., `PaymentEvent`), and Compliance & Audit (e.g., `Immutable Audit Log`).

## Consequences

- **Positive:** Ensures all developers, domain experts, and future localization contributors speak the exact same language.
- **Positive:** Variables, classes, and database schemas will strictly map to this document, preventing translation errors.
- **Negative:** Requires rigorous discipline during code reviews to reject any Pull Requests that introduce non-standard terminology into the core domain.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                           |
| -------------------------------- | ------------------------------------------------------------------------------------------------------ |
| **Number Immutability**          | ADR number `adr-2026-09-14-001` is assigned permanently. Never reused, reassigned, or removed.         |
| **Commit Referential Integrity** | Commit field references: `docs: add ubiquitous language glossary for core domain and compliance terms` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                       |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.            |

**Supersedes:** None
