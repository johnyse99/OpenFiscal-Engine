# Environment Dockerization and Container Orchestration

**Date:** 2026-09-23
**Status:** Accepted
**Commit:** chore(infra): add multi-stage Dockerfile and docker-compose stack with PostgreSQL
**ADR Number:** adr-2026-09-23-031
**Deciders:** Lead Architect, Juan S.

## Context

For OpenFiscal Engine to be easily deployed, tested, and reviewed by external stakeholders or community contributors, it requires a reproducible runtime environment. Relying on host-machine configurations for Java 21, Maven, and PostgreSQL creates friction and the "it works on my machine" anti-pattern.

## Decision

Implemented a multi-stage `Dockerfile` to encapsulate the Maven build process and produce a lightweight `eclipse-temurin:21-jre-alpine` runtime image. Additionally, created a `docker-compose.yml` file to orchestrate the application container alongside a `postgres:16-alpine` database. Environment variables are used to inject the PostgreSQL credentials into the Spring Boot context, overriding the default H2 database used during local testing.

## Consequences

- **Positive:** The entire infrastructure (database and application) can be provisioned locally using a single command: `docker-compose up --build`.
- **Positive:** The multi-stage build prevents source code and build tools from bloating the final production image.
- **Positive:** The `depends_on` health check ensures the application waits for the PostgreSQL database to be ready before attempting to apply JPA schemas.
- **Negative:** Containerization adds an abstraction layer that developers must understand for advanced debugging (e.g., viewing logs via `docker logs`).

## Compliance

This ADR complies with the following OpenFiscal-Engine registry invariants:

| Invariant                        | Verification                                                                                                 |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| **Number Immutability**          | ADR number `adr-2026-09-23-031` is assigned permanently. Never reused, reassigned, or removed.               |
| **Commit Referential Integrity** | Commit field references: `chore(infra): add multi-stage Dockerfile and docker-compose stack with PostgreSQL` |
| **Structural Completeness**      | All required sections (Context, Decision, Consequences, Compliance) are present.                             |
| **Status Lifecycle**             | Current status: `Accepted`. Valid transitions: Proposed → Accepted / Rejected / Deprecated.                  |

**Supersedes:** None
