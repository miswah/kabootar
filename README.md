# Kabootar Platform

Kabootar is a high-performance, multi-region load balancing platform. This repository is structured as a modular monorepo, separating concerns into distinct planes: Data, Control, Operations, and Management.

## Architecture Philosophy
Kabootar follows a strict modular architecture to ensure scalability and maintainability. By isolating modules, we prevent circular dependencies and allow independent development and deployment of platform components.

### Module Boundaries & Responsibilities

| Module                 | Responsibility |
|:-----------------------| :--- |
| `gateway`              | The entry point for incoming traffic; executes routing logic. |
| `configuration`        | Centralized management of regional configurations and routing rules. |
| `monitor`              | Continuous assessment of backend node status and availability. |
| `alert`                | Observability and incident reporting for node/regional failures. |
| `simulator`            | Mock service used for testing cross-region load balancing behavior. |
| `common`               | Common DTOs, API definitions, and shared domain logic. |
| `admin-ui`   (pending) | React/TypeScript dashboard for platform monitoring and management. |
| `infrastructure` (pending)      | IaC (Terraform/Pulumi) for provisioning cloud resources. |
| `performance-tests` (pending)   | Benchmarking tools for latency and throughput validation. |

## Dependency Rules
To maintain system integrity, all contributors must adhere to these rules:
1. **Gateway Isolation:** The `gateway` must **not** depend directly on `monitor` implementations.
2. **Contract Purity:** `common` must never depend on any deployable application modules.
3. **Service Layering:** Services may depend on `common` for shared interfaces.
4. **UI Access:** The `admin-ui` communicates solely through published APIs; it is forbidden from direct database access.
5. **Infrastructure Separation:** Application code and infrastructure configurations must remain strictly separate.

## Getting Started

### Prerequisites
* **Java 25** (OpenJDK)
* **Maven**
* **MariaDB** (for platform configuration storage)
* **Node.js/npm** (for the `admin-ui`)
* **Angular** (for the `admin-ui`)

### Build the Platform
Run the following from the project root to verify all modules build correctly:

```bash
# Build all modules
mvn clean install
```

### Starting Services
Each backend module is designed as an independent Spring Boot 4.1 application. To start a specific module:

```bash
# Example: Starting the Gateway
mvn -pl gateway spring-boot:run
```

## Testing
We maintain a performance-first approach. Run tests via the performance-tests module:
```bash
mvn -pl performance-tests test
```
