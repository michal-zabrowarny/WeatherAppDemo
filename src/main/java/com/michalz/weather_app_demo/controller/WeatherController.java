package com.michalz.weather_app_demo.controller;

import com.michalz.weather_app_demo.dto.DailyForecastDTO;
import com.michalz.weather_app_demo.service.WeatherService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public ResponseEntity<List<DailyForecastDTO>> getFormattedForecast(
            @RequestParam @Min(-90) @Max(90) Double latitude,
            @RequestParam @Min(-180) @Max(180) Double longitude
    ) {
        List<DailyForecastDTO> forecast = weatherService.getFormatted7DayForecast(latitude, longitude);
        return ResponseEntity.ok(forecast);
    }

    @GetMapping("/summary")
    public ResponseEntity<String> getWeatherSummary(
            @RequestParam @Min(-90) @Max(90) Double latitude,
            @RequestParam @Min(-180) @Max(180) Double longitude
    ) {
        String summary = weatherService.getWeatherSummary(latitude, longitude);
        return ResponseEntity.ok(summary);
    }
}
