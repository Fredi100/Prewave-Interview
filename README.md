# Prewave-Interview

Spring Boot + Kotlin project with PostgreSQL and jOOQ.

## Prerequisites

- Java JDK 24
- Docker (Docker Desktop on Windows)

## 1) Clone the project

Either clone the repository using Git, or download the ZIP file and extract it.

```bash
git clone <your-repository-url>
cd Prewave-Interview
```

## 2) Start PostgreSQL with Docker

The project uses `docker-compose.yml` to start a PostgreSQL container on `localhost:5432`.

```bash
docker compose up -d
```

Schema SQL is loaded from `src/main/resources/db` via Docker init scripts.

*Initialized with [spring initializr](https://start.spring.io/)*