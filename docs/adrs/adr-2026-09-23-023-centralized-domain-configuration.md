# Centralized Domain Configuration for Dependency Injection

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** feat(shared): implement DomainConfig to wire pure application services with Spring infrastructure adapters
**ADR Number:** adr-2026-09-23-023
**Deciders:** Lead Architect, Juan S.

## Context

To maintain the purity of the Hexagonal Architecture, our application services (`DraftInvoiceService`, `IssueInvoiceService`, `FinancialReconciliationService`, `ComplianceAuditService`) do not contain Spring Boot annotations (`@Service`, `@Autowired`). However, at runtime, we need the framework to instantiate these classes and inject the actual infrastructure implementations (such as JPA repositories and REST clients) into their corresponding output ports.

## Decision

The `DomainConfig` class, annotated with `@Configuration`, was created in the `com.openfiscal.shared.infrastructure.config` package. This class acts as the central dependency injection orchestrator, declaring `@Bean` methods for each domain use case and injecting the required port interfaces.

## Consequences

- **Positive:** Ensures that the `core`, `payment`, and `compliance` layers remain 100% isolated from the dependency injection framework.
- **Positive:** Provides a single point of truth for understanding how system components are assembled.
- **Negative:** Every time a new use case is created, developers must remember to manually register the `@Bean` in this configuration class.

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                                          |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Number Immutability**          | ADR number `adr-2026-09-23-023` is assigned permanently. Never reused, reassigned, or removed.                                        |
| **Commit Referential Integrity** | Commit field references: `feat(shared): implement DomainConfig to wire pure application services with Spring infrastructure adapters` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                                                      |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                                           |

**Supersedes:** None
