# Weather Forecast REST API

This project is a simple RESTful web service built with Spring Boot that integrates with the Open-Meteo API to provide 7-day weather forecasts.  
It demonstrates clean architecture, data validation, error handling, and energy estimation logic.

## Features

- Fetches 7-day weather forecast for any geographic coordinates
- Calculates estimated solar energy production (kWh) based on sunshine duration
- Validates input parameters (latitude and longitude ranges)
- Handles and customizes error responses for:
  - Invalid input
  - API failures
  - Server exceptions
- Unit and integration tests included (JUnit, Mockito)
- Uses MapStruct for mapping between DTOs and internal data models

## API Endpoint

### `GET /weather/forecast`

#### Query Parameters
| Name      | Type    | Description                          |
|-----------|---------|--------------------------------------|
| latitude  | double  | Must be between -90 and 90           |
| longitude | double  | Must be between -180 and 180         |

#### Example request
```
http://localhost:8080/weather/forecast?latitude=52.52&longitude=13.41
```

#### Sample JSON response
```json
[
  {
    "date": "2025-06-20",
    "minTemperature": 13.4,
    "maxTemperature": 24.6,
    "solarEnergyKWh": 1.2,
    "weatherCode": 2
  },
  ...
]
```

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Web
- MapStruct
- Open-Meteo API
- Maven
- JUnit 5 / Mockito
