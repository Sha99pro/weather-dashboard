package com.example.demo.controllers;

import com.example.demo.model.WeatherData;
import com.example.demo.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public WeatherData getWeather(@RequestParam String city) {
        return weatherService.getCurrentWeather(city);
    }
}