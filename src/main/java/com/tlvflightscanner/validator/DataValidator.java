package com.tlvflightscanner.validator;

import com.tlvflightscanner.dto.FlightData;

public class DataValidator {
    public static void validateFlightData(FlightData flightData) throws IllegalArgumentException {
        if (flightData == null || flightData.flightNumber() == null || flightData.estimatedDepartureTime() == null || flightData.realDepartureTime() == null || flightData.destinationAirport() == null ||
                flightData.city() == null || flightData.country() == null || flightData.isInbound() == null)
            throw new IllegalArgumentException("Invalid flight data received: " + flightData);
    }
}