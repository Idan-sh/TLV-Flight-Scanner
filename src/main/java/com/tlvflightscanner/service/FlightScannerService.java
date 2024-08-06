package com.tlvflightscanner.service;

import com.tlvflightscanner.dto.FlightData;
import com.tlvflightscanner.dto.QuickGetawayResponse;
import com.tlvflightscanner.validator.DataValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightScannerService {
    private final ApiService apiService;

    /**
     * Parse the JSON received from the API into a FlightData objects for ease of use.
     * @return List of FlightData objects.
     */
    public List<FlightData> getFlightsData() {
        String jsonResponse = apiService.fetchDataFromApi();

        log.info("Parsing JSON response from API...");
        List<FlightData> flightDataList = new ArrayList<>();
        JSONObject responseObj = new JSONObject(jsonResponse);
        JSONArray recordsArray = responseObj.getJSONObject("result").getJSONArray("records");

        for (int i = 0; i < recordsArray.length(); i++) {
            JSONObject record = recordsArray.getJSONObject(i);
            FlightData flightData = new FlightData(
                    record.getString("CHOPER") + record.getString("CHFLTN"),
                    record.optString("CHSTOL"),
                    record.optString("CHPTOL"),
                    record.optString("CHLOC1"),
                    record.optString("CHLOC1T"),
                    record.optString("CHLOCCT"),
                    record.isNull("CHCINT") // null if inbound, otherwise outbound
            );

            DataValidator.validateFlightData(flightData);
            flightDataList.add(flightData);
        }

        log.debug("Parsed flight Data List: {}", flightDataList);
        return flightDataList;
    }

    /**
     * Returns (if exists) two flights: one from Israel and one to Israel,
     * that someone can take for a quick getaway - considering date and time.
     */
    public QuickGetawayResponse getQuickGetaway() {
        log.info("Searching for a quick getaway...");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); // DateTimeFormatter to parse the string times
        List<FlightData> flights = getFlightsData();

        // Split flights list into inbound flights and outbound flights, sorted by time
        List<FlightData> inboundFlights = flights.stream()
                .filter(FlightData::isInbound)
                .sorted(Comparator.comparing(f -> LocalDateTime.parse(f.realDepartureTime(), dateTimeFormatter)))
                .toList();
        List<FlightData> outboundFlights = flights.stream()
                .filter(flight -> !flight.isInbound())
                .sorted(Comparator.comparing(f -> LocalDateTime.parse(f.realDepartureTime(), dateTimeFormatter)))
                .toList();

        // Find outbound flight, then try to find a matching inbound flight
        for(FlightData outbound : outboundFlights) {
            Optional<FlightData> matchingInbound = inboundFlights.stream()
                    .filter(inbound ->
                            inbound.city().equals(outbound.city()) && // Ensure the cities match
                                    Duration.between(
                                            LocalDateTime.parse(outbound.realDepartureTime(), dateTimeFormatter),
                                                    LocalDateTime.parse(inbound.realDepartureTime(), dateTimeFormatter))
                                            .toHours() > 0) // Ensure outbound flight is before inbound flight
                    .findFirst();
            if (matchingInbound.isPresent()) {
                return new QuickGetawayResponse(outbound.flightNumber(), matchingInbound.get().flightNumber());
            }
        }

        return null; // Couldn't find a quick getaway.
    }
}
