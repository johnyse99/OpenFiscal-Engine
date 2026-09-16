# Ubiquitous Language (Glossary)

This document defines the strictly standardized vocabulary used across the OpenFiscal Engine. This language must be used consistently in conversations, ADRs, variable names, and class definitions to ensure zero ambiguity across the Core Domain and Localization plugins.

## 1. Core Billing Context (Universal)

These terms describe the agnostic mathematical and structural reality of a fiscal document, independent of any country's specific legislation.

- **Invoice (Aggregate Root):** The primary, universally valid fiscal document representing a commercial transaction. Its state is heavily guarded and transitions strictly (e.g., DRAFT -> ISSUED -> CANCELLED).
- **Taxpayer:** Any legal or physical entity participating in a transaction. In the core domain, a Taxpayer has a universal Tax ID; localization plugins will map this to specific IDs (e.g., RFC in Mexico, TIN globally).
- **Issuer:** The Taxpayer generating the Invoice and collecting the revenue.
- **Receiver:** The Taxpayer receiving the goods/services and paying the revenue.
- **LineItem:** A single row within an Invoice detailing a specific good or service, its unit price, quantity, and applied taxes.
- **Tax:** A universal representation of a fiscal charge (e.g., VAT) or withholding applied to a LineItem or the Invoice total.
- **Money:** An immutable Value Object representing a precise financial amount and its associated currency, utilizing smallest-unit arithmetic (e.g., BigInt) to eliminate floating-point rounding errors.

## 2. Payment & Reconciliation Context

These terms bridge the gap between the purely fiscal world (Invoices) and the real financial world (Banking).

- **PaymentEvent:** An external, verifiable notification of cash flow originating from banking systems (e.g., SPEI transfers, CoDi QR payments)[cite: 1].
- **Reconciliation:** The automated, algorithmic process of matching a `PaymentEvent` directly to one or more `Invoice` entities, transforming a pending fiscal document into a financially backed truth.
- **PaymentReceipt:** A secondary fiscal document generated automatically upon successful `Reconciliation`, proving that an issued Invoice has been financially settled.

## 3. Compliance & Audit Context (Anti-Evasion)

These terms define the engine's capability to detect fraud, block illicit operations, and monitor systemic integrity.

- **Blacklist:** A dynamically updated registry provided by government authorities containing non-compliant or fraudulent Taxpayers used to instantly block transaction attempts.
- **SimulatedOperation:** A highly critical domain alert triggered when a transaction involves an entity flagged as a shell company (such as EFOS and EDOS under Article 69-B in Mexico) or lacks backing cash flow.
- **FiscalDiscrepancy:** An algorithmic alert triggered when the system detects a mismatch between the revenue billed via `Invoice` issuance and the actual capital reconciled via `PaymentEvent` cross-referencing.
- **Immutable Audit Log:** An append-only, tamper-evident data structure that persistently records all critical administrative interventions. It ensures that any attempt by system administrators to bypass rules or dismiss fraud alerts leaves a permanent, traceable footprint.
