# Prewave-Interview

Spring Boot + Kotlin project with PostgreSQL and jOOQ.

## Prerequisites

- Java JDK 24
- Docker (Docker Desktop on Windows)

## 1) Clone the project

Either clone the repository using Git, or download the ZIP file and extract it.

```bash
git clone https://github.com/Fredi100/Prewave-Interview.git
cd Prewave-Interview
```

## 2) Start PostgreSQL with Docker

The project uses `docker-compose.yml` to start a PostgreSQL container on `localhost:5432`.

```bash
docker compose up -d
```

Schema SQL is loaded from `src/main/resources/db` via Docker init scripts.

## 3) Run the application

```bash
./gradlew bootRun
```

Spring datasource configuration is in `src/main/resources/application.properties`:

## 4) Interact with the API

Use the included Postman collection `Prewave-Interview.postman_collection.json` to test the API endpoints.

---
### `POST /edge/create`
Creates a new directed edge.

Request body:
```json
{
  "fromId": 1,
  "toId": 2
}
```

* If successful will return `201 Created`
* In case the edge already exists, will return `409 Conflict`
* Adding an edge that would create a cycle will also return `409 Conflict`
* If `toId` is already a target node of another edge, will return `409 Conflict` as each node can only have one parent.
___
### `POST /edge/delete`
Deletes an existing directed edge.

Request body:
```json
{
  "fromId": 1,
  "toId": 2
}
```

* On successful deletion returns `204 No Content`
* If the edge does not exist, will return `404 Not Found`
___
### `GET /edge/{nodeId}`
Returns the tree starting from `nodeId`.
Has a maximum depth of 100 levels as JSON serialization will fail for even deeper trees.


On success will return a json similar to this:
```json
{
  "id": 1,
  "children": [
    {
      "id": 2,
      "children": [
        { 
          "id": 4, 
          "children": []
        },
        { 
          "id": 5, 
          "children": []
        }
      ]
    },
    {
      "id": 3,
      "children": [
        { 
          "id": 6, 
          "children": []
        }
      ]
    }
  ]
}
```

* Will return `404 Not Found` if the node with `nodeId` does not exist.
___
*Initialized with [spring initializr](https://start.spring.io/)*
