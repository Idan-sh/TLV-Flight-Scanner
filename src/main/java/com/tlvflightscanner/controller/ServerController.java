package com.tlvflightscanner.controller;

import com.tlvflightscanner.dto.QuickGetawayResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    /**
     * Get the number of flights (inbound & outbound) from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/all-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getAllFlights() {
        return 0;
    }

    /**
     * Get the number of outbound flights from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/outbound-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOutboundFlights() {
        return 0;
    }

    /**
     * Get the number of inbound flights to TLV airport.
     * @return Integer number.
     */
    @GetMapping("/inbound-flights")
    @ResponseStatus(HttpStatus.OK)
    public Integer getInboundFlights() {
        return 0;
    }

    /**
     * Get the number of flights (inbound & outbound) from TLV airport,
     * from a specific country.
     * @return Integer number.
     */
    @GetMapping("/all-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getFlightsFromCountry(@RequestParam String country) {
        return 0;
    }

    /**
     * Get the number of outbound flights from TLV airport,
     * to a specific country.
     * @return Integer number.
     */
    @GetMapping("/outbound-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOutboundFlightsFromCountry(@RequestParam String country) {
        return 0;
    }

    /**
     * Get the number of inbound flights to TLV airport,
     * from a specific country.
     * @return Integer number.
     */
    @GetMapping("/inbound-flights-from-country")
    @ResponseStatus(HttpStatus.OK)
    public Integer getInboundFlightsFromCountry(@RequestParam String country) {
        return 0;
    }

    /**
     * Get the number of delayed flights from TLV airport.
     * @return Integer number.
     */
    @GetMapping("/delayed")
    @ResponseStatus(HttpStatus.OK)
    public Integer getDelayed() {
        return 0;
    }

    /**
     * Get the city with the highest number of outbound flights.
     * @return City name as String.
     */
    @GetMapping("/most-popular-destination")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMostPopularDestination() {
        return 0;
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
