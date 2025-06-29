package com.michalz.weather_app_demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyForecastDTO {
    private String date;
    private double minTemperature;
    private double maxTemperature;
    private double solarEnergyKWh;
    private int weatherCode;

}

