# Implementation of API Endpoint Security Perimeter

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** feat(shared): implement Spring Security perimeter for API endpoints
**ADR Number:** adr-2026-09-23-030
**Deciders:** Lead Architect, Juan S.

## Context

Exposing the Core Billing APIs (`/api/v1/invoices/**`) without authentication presents a critical risk, allowing anyone to issue fiscal documents[cite: 1]. The system requires a production-grade security perimeter that enforces authentication for core operations while allowing specific external integrations (like Open Banking Webhooks) to remain accessible for payload reception.

## Decision

Integrated `spring-boot-starter-security` and created the `SecurityConfig` class in the `com.openfiscal.shared.infrastructure.config` package. Configured the application strictly as stateless, disabling CSRF (as it is not browser-based), and enforced HTTP Basic authentication for all `/api/v1/**` endpoints except the webhooks path.

## Consequences

- **Positive:** The fiscal engine's core operations are immediately protected from unauthorized execution.
- **Positive:** The stateless configuration prevents memory leaks related to HTTP sessions, making the system highly scalable and ready for cloud deployment.
- **Negative:** All integration tests for the `InvoiceController` will now return HTTP 401 (Unauthorized) until the test suites are updated to inject mock users or bypass security.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                   |
| -------------------------------- | ---------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-030` is assigned permanently. Never reused, reassigned, or removed. |
| **Commit Referential Integrity** | Commit field references: `feat(shared): implement Spring Security perimeter for API endpoints` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.               |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.    |

**Supersedes:** None
