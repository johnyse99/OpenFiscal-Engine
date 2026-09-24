# Implementation of Core Billing Integration Tests

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** test(core): implement MockMvc integration tests for InvoiceController and exception mapping
**ADR Number:** adr-2026-09-23-027
**Deciders:** Lead Architect, Juan S.

## Context

While pure domain unit tests verify our strict fiscal mathematics, we must ensure that our infrastructure adapters correctly bridge the outside world to the core domain. Specifically, we need to verify that JSON payloads correctly deserialize into internal commands and that the `GlobalExceptionHandler` intercepts pure domain exceptions (like `IllegalArgumentException`) to yield standardized HTTP 400 responses.

## Decision

Created `InvoiceControllerIntegrationTest` using `@SpringBootTest` and `@AutoConfigureMockMvc`. This test spins up the full Spring context with the in-memory H2 database, executing real HTTP requests against the DispatcherServlet. It verifies the complete vertical slice: from the REST boundary, through the application service, down to the domain aggregate constraints.

## Consequences

- **Positive:** Confirms the `GlobalExceptionHandler` operates seamlessly with the `InvoiceController`.
- **Positive:** Validates the complete wiring of the dependency injection container (`DomainConfig`).
- **Negative:** Integration tests are slower than domain unit tests, slightly increasing CI pipeline build times.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                           |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-027` is assigned permanently. Never reused, reassigned, or removed.                         |
| **Commit Referential Integrity** | Commit field references: `test(core): implement MockMvc integration tests for InvoiceController and exception mapping` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                       |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                            |

**Supersedes:** None
