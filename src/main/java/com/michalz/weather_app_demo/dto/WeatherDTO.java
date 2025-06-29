package com.michalz.weather_app_demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDTO {
    private Daily daily;

    @Data
    public static class Daily {
        private List<String> time;
        private List<Double> temperature_2m_min;
        private List<Double> temperature_2m_max;
        private List<Double> sunshine_duration;
        private List<Integer> weathercode;
    }
}

