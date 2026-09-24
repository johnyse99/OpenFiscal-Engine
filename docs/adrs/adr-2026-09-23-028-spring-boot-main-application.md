# Creation of Spring Boot Main Application Class

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** feat(app): add OpenFiscalEngineApplication main class to bootstrap Spring Boot context
**ADR Number:** adr-2026-09-23-028
**Deciders:** Lead Architect, Juan S.

## Context

The `@SpringBootTest` annotation relies on a root `@SpringBootConfiguration` (typically provided by `@SpringBootApplication`) to load the application context. Because the repository was built domain-first without the standard Spring Boot initializer skeleton, the integration tests crashed with an `IllegalStateException` due to the missing bootstrap class.

## Decision

Created the `OpenFiscalEngineApplication` class annotated with `@SpringBootApplication` at the root package level (`com.openfiscal`). This enables Spring's component scanning to detect our infrastructure adapters and the centralized `DomainConfig`.

## Consequences

- **Positive:** Resolves the context loading failure for all integration tests.
- **Positive:** Provides the executable entry point required to compile the final `.jar` and run the application in a production environment.
- **Negative:** None. This is a mandatory component for the chosen infrastructure framework.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                      |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-028` is assigned permanently. Never reused, reassigned, or removed.                    |
| **Commit Referential Integrity** | Commit field references: `feat(app): add OpenFiscalEngineApplication main class to bootstrap Spring Boot context` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                  |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                       |

**Supersedes:** None
