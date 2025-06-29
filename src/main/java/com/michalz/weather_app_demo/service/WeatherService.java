package com.michalz.weather_app_demo.service;

import com.michalz.weather_app_demo.exception.ExternalApiException;
import com.michalz.weather_app_demo.mapper.WeatherMapper;
import com.michalz.weather_app_demo.dto.DailyForecastDTO;
import com.michalz.weather_app_demo.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



import java.util.List;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherMapper weatherMapper;


    public List<DailyForecastDTO> getFormatted7DayForecast(double latitude, double longitude) {
        WeatherDTO dto = get7DayForecast(latitude, longitude);
        List<Double> energy = convertSecondsToKWh(dto.getDaily().getSunshine_duration());
        return weatherMapper.mapToDailyForecasts(dto.getDaily(), energy);
    }

    public WeatherDTO get7DayForecast(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "temperature_2m_max,temperature_2m_min,sunshine_duration,weathercode")
                .queryParam("timezone", "auto")
                .toUriString();
        try {
            return restTemplate.getForObject(url, WeatherDTO.class);
        } catch (RestClientException e) {
            throw new ExternalApiException("Failed to fetch weather data", e);
        }
    }

    List<Double> convertSecondsToKWh(List<Double> secondsList) {
        return secondsList.stream()
                .map(seconds -> Math.round((seconds / 3600.0) * 10.0) / 10.0 * 0.2 * 2.5)
                .toList();
    }

    public String getWeatherSummary(Double latitude, Double longitude) {
        return String.format("Weather summary for %.2f, %.2f", latitude, longitude);
    }
}
