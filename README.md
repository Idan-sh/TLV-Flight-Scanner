# REST API Documentation for Flight Scanner Server

## Overview
This REST API server provides various endpoints to retrieve flight information for TLV airport. It supports fetching details on inbound, outbound, and delayed flights, as well as specific flight data based on country and popular destinations. The API also provides a quick-getaway feature to find convenient flights for a short trip.

The server uses an external flights API as described in:   
`https://data.gov.il/dataset/flydata/resource/e83f763b-b7d7-479e-b172-ae981ddc6de5`   
   
Base URL for data fetching:   
`https://data.gov.il/api/3/action/datastore_search?resource_id=e83f763b-b7d7-479e-b172- ae981ddc6de5&limit=300`
   
> All the queries will be limited and the data can change durting time, that means the same query can have different result in long enough time span.   

## Base URL
The base URL for all endpoints is: `http://localhost:8080`

## Endpoints

### Get Total Number of Flights

**Request**

- **Method**: GET
- **URL**: `/all-flights`

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of inbound and outbound flights from TLV airport.

**Example**

```
GET /all-flights
200 OK
12
```

---

### Get Number of Outbound Flights

**Request**

- **Method**: GET
- **URL**: `/outbound-flights`

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of outbound flights from TLV airport.

**Example**

```
GET /outbound-flights
200 OK
7
```

---

### Get Number of Inbound Flights

**Request**

- **Method**: GET
- **URL**: `/inbound-flights`

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of inbound flights to TLV airport.

**Example**

```
GET /inbound-flights
200 OK
5
```

---

### Get Number of Flights from a Specific Country

**Request**

- **Method**: GET
- **URL**: `/all-flights-from-country`
- **Parameters**:
  - `country` (required): `String` - Name of the country.

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of flights (inbound and outbound) from the specified country.

**Example**

```
GET /all-flights-from-country?country=France
200 OK
3
```

---

### Get Number of Outbound Flights to a Specific Country

**Request**

- **Method**: GET
- **URL**: `/outbound-flights-from-country`
- **Parameters**:
  - `country` (required): `String` - Name of the country.

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of outbound flights to the specified country.

**Example**

```
GET /outbound-flights-from-country?country=France
200 OK
2
```

---

### Get Number of Inbound Flights from a Specific Country

**Request**

- **Method**: GET
- **URL**: `/inbound-flights-from-country`
- **Parameters**:
  - `country` (required): `String` - Name of the country.

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of inbound flights from the specified country.

**Example**

```
GET /inbound-flights-from-country?country=France
200 OK
1
```

---

### Get Number of Delayed Flights

**Request**

- **Method**: GET
- **URL**: `/delayed`

**Response**

- **Status Code**: 200 OK
- **Body**: `Integer` - Number of delayed flights from TLV airport.

**Example**

```
GET /delayed
200 OK
4
```

---

### Get Most Popular Destination

**Request**

- **Method**: GET
- **URL**: `/most-popular-destination`

**Response**

- **Status Code**: 200 OK
- **Body**: `String` - Name of the city with the highest number of outbound flights from TLV airport.

**Example**

```
GET /most-popular-destination
200 OK
"Paris"
```

---

### Get Quick Getaway Flights

**Request**

- **Method**: GET
- **URL**: `/quick-getaway`

**Response**

- **Status Code**: 200 OK
- **Body**: `JSON` - JSON object containing departure and arrival flight numbers if a quick getaway exists, otherwise an empty JSON.

**Example**

```
GET /quick-getaway
200 OK
{
  "departureFlightNumber": "TLV123",
  "arrivalFlightNumber": "TLV456"
}
```

---

## Error Handling
All endpoints return a 400 Bad Request error for invalid or missing parameters and a 500 Internal Server Error for any server-side issues.

## Logging
Each endpoint logs the received request with relevant details for debugging and monitoring purposes.

<br />   

---

# Building and Running the Flight Scanner Server

This documentation provides instructions on how to build and run the Flight Scanner Server both using Docker and without Docker.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Java Development Kit (JDK) 8 or higher
- Maven
- Docker (for Docker instructions)

## Building and Running Without Docker

### Step 1: Clone the Repository

First, clone the repository to your local machine.

```bash
git clone https://github.com/Idan-sh/TLV-Flight-Scanner.git
cd TLV-Flight-Scanner
```

### Step 2: Build the Project

Use Maven to build the project. This will compile the code and package it into a JAR file.

```bash
mvn clean install
```

### Step 3: Run the Server

After successfully building the project, run the server using the following command:

```bash
java -jar target/flight-scanner-0.0.1-SNAPSHOT.jar
```

The server should now be running and accessible at `http://localhost:8080`.
   
## Building and Running with Docker

### Step 1: Clone the Repository

First, clone the repository to your local machine.

```bash
git clone https://github.com/Idan-sh/TLV-Flight-Scanner.git
cd TLV-Flight-Scanner
```

### Step 2: Build the Docker Image

Build the Docker image using the Dockerfile included in the repository.

```bash
docker build -t flight-scanner .
```

### Step 3: Run the Docker Container

Run the Docker container using the image you just built.

```bash
docker run -d -p 8080:8080 --name flight-scanner-server flight-scanner
```

The server should now be running inside the Docker container and accessible at `http://localhost:8080`.

## Verifying the Setup

To verify that the server is running correctly, you can use any HTTP client (such as `curl`, Postman, or a web browser) to make requests to the server. For example, to check the number of all flights:

```bash
curl http://localhost:8080/all-flights
```

## Stopping the Server

### Without Docker

To stop the server, you can use `Ctrl + C` in the terminal where the server is running.

### With Docker

To stop the Docker container, use the following command:

```bash
docker stop flight-scanner-server
```

To remove the Docker container, use the following command:

```bash
docker rm flight-scanner-server
```
