# Implementation of Global Exception Handler

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** feat(shared): implement GlobalExceptionHandler to map domain exceptions to HTTP responses
**ADR Number:** adr-2026-09-23-025
**Deciders:** Lead Architect, Juan S.

## Context

The pure Domain and Application layers enforce business invariants by throwing standard Java runtime exceptions (e.g., `IllegalArgumentException` for invalid data, `IllegalStateException` for invalid state transitions like issuing an already issued invoice). When external clients interact with the system via REST adapters, allowing these exceptions to propagate unhandled results in unstructured HTTP 500 errors and exposes internal stack traces.

## Decision

Created the `GlobalExceptionHandler` using Spring's `@ControllerAdvice` in the `com.openfiscal.shared.infrastructure.adapter.in.web` package. This acts as an interceptor for all controllers across the application. It explicitly maps `IllegalArgumentException` to HTTP 400 (Bad Request) and `IllegalStateException` to HTTP 409 (Conflict), wrapping the original domain message in a standardized `ErrorResponseDto`.

## Consequences

- **Positive:** Ensures API consumers receive clear, standardized JSON error responses.
- **Positive:** Prevents internal stack traces from leaking to external clients, improving security.
- **Positive:** Keeps the REST controllers clean by removing `try-catch` blocks from the inbound adapters.
- **Negative:** Over-reliance on generic runtime exceptions could mask unintended bugs if not carefully monitored; future iterations may require custom domain exceptions (e.g., `InvoiceAlreadyIssuedException`).

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                         |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-025` is assigned permanently. Never reused, reassigned, or removed.                       |
| **Commit Referential Integrity** | Commit field references: `feat(shared): implement GlobalExceptionHandler to map domain exceptions to HTTP responses` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                     |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                          |

**Supersedes:** None
