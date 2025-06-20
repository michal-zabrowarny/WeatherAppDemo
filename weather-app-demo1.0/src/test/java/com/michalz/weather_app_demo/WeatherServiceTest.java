package com.michalz.weather_app_demo;

import com.michalz.weather_app_demo.dto.DailyForecastDTO;
import com.michalz.weather_app_demo.dto.WeatherDTO;
import com.michalz.weather_app_demo.mapper.WeatherMapper;
import com.michalz.weather_app_demo.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherMapper weatherMapper;

    @Test
    @DisplayName("Should correctly convert sunshine duration to kWh and call mapper")
    void shouldCallWeatherMapperWithCorrectData() {
        // given
        WeatherDTO.Daily daily = new WeatherDTO.Daily();
        daily.setTime(List.of("2025-06-21"));
        daily.setTemperature_2m_min(List.of(10.0));
        daily.setTemperature_2m_max(List.of(20.0));
        daily.setSunshine_duration(List.of(3600.0));
        daily.setWeathercode(List.of(1));

        WeatherDTO dto = new WeatherDTO();
        dto.setDaily(daily);

        DailyForecastDTO expected = new DailyForecastDTO("2025-06-21", 10.0, 20.0, 0.5, 1);
        List<DailyForecastDTO> expectedList = List.of(expected);

        WeatherService weatherServiceSpy = new WeatherService(weatherMapper) {
            @Override
            public WeatherDTO get7DayForecast(double lat, double lon) {
                return dto;
            }
        };

        when(weatherMapper.mapToDailyForecasts(daily, List.of(0.5))).thenReturn(expectedList);

        List<DailyForecastDTO> result = weatherServiceSpy.getFormatted7DayForecast(50.0, 20.0);

        assertEquals(expectedList, result);
    }
}
