package com.tlvflightscanner.controller;

import com.tlvflightscanner.dto.data.FlightData;
import com.tlvflightscanner.dto.response.QuickGetawayResponse;
import com.tlvflightscanner.service.FlightScannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ServerController {
    private final FlightScannerService flightScannerService;

    /**
     * Get the number of flights (inbound & outbound) from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/all-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getAllFlights() {
        log.info("Received request to retrieve the number of flights (inbound & outbound).");
        return flightScannerService.getFlightsData().size();
    }

    /**
     * Get the number of outbound flights from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/outbound-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOutboundFlights() {
        log.info("Received request to retrieve the number of outbound flights.");
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(flightData -> !flightData.isInbound()) // Filter only outbound flights
                .count();
    }

    /**
     * Get the number of inbound flights to TLV airport.
     * @return Integer number.
     */
    @GetMapping("/inbound-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getInboundFlights() {
        log.info("Received request to retrieve the number of inbound flights.");
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(FlightData::isInbound) // Filter only inbound flights
                .count();
    }

    /**
     * Get the number of flights (inbound & outbound) from TLV airport,
     * from a specific country.
     * @return Integer number.
     */
    @GetMapping("/all-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getFlightsFromCountry(@RequestParam String country) {
        log.info("Received request to retrieve the number of flights (inbound & outbound) from country '{}'.", country);
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(flightData -> flightData.country().equals(country))
                .count();
    }

    /**
     * Get the number of outbound flights from TLV airport,
     * to a specific country.
     * @return Integer number.
     */
    @GetMapping("/outbound-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOutboundFlightsFromCountry(@RequestParam String country) {
        log.info("Received request to retrieve the number of outbound flights to country '{}'.", country);
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(flightData -> flightData.country().equals(country) && !flightData.isInbound())
                .count();
    }

    /**
     * Get the number of inbound flights to TLV airport,
     * from a specific country.
     * @return Integer number.
     */
    @GetMapping("/inbound-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getInboundFlightsFromCountry(@RequestParam String country) {
        log.info("Received request to retrieve the number of inbound flights to country '{}'.", country);
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(flightData -> flightData.country().equals(country) && flightData.isInbound())
                .count();
    }

    /**
     * Get the number of delayed flights from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/delayed")
    @ResponseStatus(HttpStatus.OK)
    public Integer getDelayed() {
        log.info("Received request to retrieve the number of delayed flights.");
        return (int) flightScannerService
                .getFlightsData()
                .stream()
                .filter(flightData -> !flightData.realDepartureTime().equals(flightData.estimatedDepartureTime())) // Check if the real departure time is not equal to the estimated departure time
                .count();
    }

    /**
     * Get the city with the highest number of outbound flights from TLV airport.
     * @return City name as String.
     */
    @GetMapping("/most-popular-destination")
    @ResponseStatus(HttpStatus.OK)
    public String getMostPopularDestination() {
        log.info("Received request to retrieve the name of the most popular destination, " +
                "with the highest number of outbound flights from TLV airport.");
        return flightScannerService
                .getFlightsData()
                .stream()
                .collect(Collectors.groupingBy(
                        flightData -> flightData.city() + ", " + flightData.country(), // Add country to the key to make sure there are no two cities from different countries
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().split(",")[0]) // Get only the city of the destination
                .orElse("There is no most popular destination");
    }

    /**
     * Return (if exist) two flights one from Israel and one to Israel,
     * that someone can take for a quick getaway, considering date and time.
     * @return JSON containing departure and arrival flight numbers (Strings) if a getaway exists,
     * otherwise empty JSON.
     */
    @GetMapping("/quick-getaway")
    @ResponseStatus(HttpStatus.OK)
    public QuickGetawayResponse getQuickGetaway() {
        return null;
    }
}
