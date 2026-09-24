# Configuration of RestTemplate Bean

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** fix(shared): add RestClientConfig to provide RestTemplate bean for outbound adapters
**ADR Number:** adr-2026-09-23-029
**Deciders:** Lead Architect, Juan S.

## Context

The `BlacklistRestClientAdapter` in the Compliance context requires a synchronous HTTP client to query external government APIs. It specifies `RestTemplate` as a constructor dependency. However, Spring Boot does not provide a default `RestTemplate` bean, causing the application context to crash during startup and failing all `@SpringBootTest` integration tests.

## Decision

Created the `RestClientConfig` class annotated with `@Configuration` in the `com.openfiscal.shared.infrastructure.config` package. Declared a `@Bean` method to instantiate and provide a default `RestTemplate` to the application context.

## Consequences

- **Positive:** Resolves the `UnsatisfiedDependencyException`, allowing the application context to bootstrap successfully for both integration tests and runtime.
- **Positive:** Centralizes HTTP client configuration, making it easy to add timeouts, interceptors, or SSL configurations globally in the future.
- **Negative:** None.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                    |
| -------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-028` is assigned permanently. Never reused, reassigned, or removed.                  |
| **Commit Referential Integrity** | Commit field references: `fix(shared): add RestClientConfig to provide RestTemplate bean for outbound adapters` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                     |

**Supersedes:** None
