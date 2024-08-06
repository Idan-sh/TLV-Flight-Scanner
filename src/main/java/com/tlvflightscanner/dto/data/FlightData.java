package com.tlvflightscanner.dto.data;

public record FlightData(
        String flightNumber,
        String estimatedDepartureTime,
        String realDepartureTime,
        String destinationAirport,
        String city,
        String country,
        Boolean isInbound) {
}
