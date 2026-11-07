package com.example.demo.service;

import com.example.demo.dto.GeocodeResult;
import com.example.demo.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${OPENWEATHER_API_KEY}")
    private String apiKey;

    private static final String GEO_URL =
            "https://api.openweathermap.org/geo/1.0/direct?q={city}&limit=1&appid={key}";

    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=imperial";


    public WeatherData getCurrentWeather(String city) {

        // Step 1: Get latitude and longitude

        GeocodeResult[] geoResults = restTemplate.getForObject(
                GEO_URL,
                GeocodeResult[].class,
                city,
                apiKey
        );

        if (geoResults == null || geoResults.length == 0) {
            throw new RuntimeException("City not found: " + city);
        }

        double lat = geoResults[0].lat();
        double lon = geoResults[0].lon();

        // Step 2: Get current weather using coordinates

        WeatherData weather = restTemplate.getForObject(
                WEATHER_URL,
                WeatherData.class,
                lat,
                lon,
                apiKey
        );

        if (weather == null) {
            throw new RuntimeException("Weather data not available.");
        }

        return weather;
    }
}