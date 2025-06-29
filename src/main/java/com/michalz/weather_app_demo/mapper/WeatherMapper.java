package com.michalz.weather_app_demo.mapper;


import com.michalz.weather_app_demo.dto.DailyForecastDTO;
import com.michalz.weather_app_demo.dto.WeatherDTO;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    default List<DailyForecastDTO> mapToDailyForecasts(WeatherDTO.Daily daily, List<Double> solarEnergyKWh) {
        return IntStream.range(0, daily.getTime().size())
                .mapToObj(i -> new DailyForecastDTO(
                        daily.getTime().get(i),
                        daily.getTemperature_2m_min().get(i),
                        daily.getTemperature_2m_max().get(i),
                        solarEnergyKWh.get(i),
                        daily.getWeathercode().get(i)
                ))
                .toList();
    }
}
