package com.tlvflightscanner.dto.response;

/**
 * Response to the bonus endpoint request.
 * @param departure Flight number of the departure flight to the getaway..
 * @param arrival Flight number of the arrival flight from the getaway.
 */
public record QuickGetawayResponse(String departure, String arrival) {
}
