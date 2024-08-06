package com.tlvflightscanner.dto;

public record FlightData(
        String flightNumber,
        String estimatedDepartureTime,
        String realDepartureTime,
        String destinationAirport,
        String city,
        String country,
        Boolean isInbound) {
}
