package com.tlvflightscanner.service;

import com.tlvflightscanner.dto.data.FlightData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                    record.getString("CHSTOL"),
                    record.getString("CHPTOL"),
                    record.getString("CHLOC1"),
                    record.getString("CHLOC1T"),
                    record.getString("CHLOCCT"),
                    record.get("CHCINT") == null // Empty if inbound, otherwise outbound
            );
            flightDataList.add(flightData);
        }

        log.debug("Parsed flight Data List: {}", flightDataList);
        return flightDataList;
    }
}
