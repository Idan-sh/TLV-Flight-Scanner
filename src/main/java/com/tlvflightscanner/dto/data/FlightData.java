package com.tlvflightscanner.dto.data;

public record FlightData(
        String flightNumber,
        String departureTime,
        String destinationAirport,
        String city,
        String country,
        Boolean isInbound) {
}
