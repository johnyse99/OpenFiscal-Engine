# OpenFiscal Engine

OpenFiscal Engine is an enterprise-grade fiscal and billing engine designed as a universal backbone for tax collection, open-banking reconciliation, and real-time anti-evasion auditing.

## Architecture Overview

Built with **Java 21** and **Spring Boot**, the system strictly adheres to **Domain-Driven Design (DDD)** and **Hexagonal Architecture** to guarantee that core business logic remains independent of external frameworks and databases.

- **Core Billing Context:** A mathematically precise invoicing domain that enforces strict lifecycle invariants and eliminates floating-point rounding errors through custom Value Objects.
- **Payment Context:** An automated matching engine that reconciles issued fiscal documents with incoming Open Banking cash flow webhooks (e.g., SPEI, CoDi).
- **Compliance Context:** Real-time algorithmic detection of simulated operations and fiscal discrepancies, persisting alerts to an Immutable Audit Log.

## Frequently Asked Questions (FAQ)

- **Why use Hexagonal Architecture (Ports and Adapters) for this project?**
  It isolates the core fiscal domain from external dependencies. This ensures that the highly sensitive tax and billing rules remain pristine, strictly testable without a framework context, and easily adaptable to different regional tax legislations without rewriting the core.

- **How does the system handle financial precision to prevent tax miscalculations?**
  Financial amounts are never stored as primitives (`double` or `float`). They are encapsulated within a custom immutable `Money` Value Object utilizing `BigInteger` for minor-unit arithmetic, completely preventing IEEE 754 precision loss.

- **How are the distinct Bounded Contexts integrated?**
  The contexts are completely decoupled using Domain Events. For example, when the Core Billing context issues an invoice, it dispatches an `InvoiceIssuedDomainEvent`. The Compliance and Payment contexts listen to these events to react asynchronously, ensuring robust boundaries.

## License & Contact

📄 **License**
This project is free software: you can redistribute it and/or modify it under the terms of the **GNU Affero General Public License (AGPLv3)** as published by the Free Software Foundation.

**Note for recruiters:** This repository demonstrates advanced software engineering patterns applied to high-precision financial systems. It showcases clean code practices, strict Domain-Driven Design boundaries, event-driven integrations, and enterprise-grade testing strategies.

**Author:** Juan S.  
**Contact:** https://github.com/johnyse99
