package com.michalz.weather_app_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michalz.weather_app_demo.controller.WeatherController;
import com.michalz.weather_app_demo.dto.DailyForecastDTO;
import com.michalz.weather_app_demo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WeatherController.class)
@Import(WeatherControllerTest.TestConfig.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnForecastForValidCoordinates() throws Exception {
        List<DailyForecastDTO> mockForecast = List.of(
                new DailyForecastDTO("2025-06-21", 12.3, 25.7, 1.2, 0),
                new DailyForecastDTO("2025-06-22", 14.1, 27.8, 1.4, 1)
        );

        when(weatherService.getFormatted7DayForecast(50.0, 20.0)).thenReturn(mockForecast);

        mockMvc.perform(get("/weather/forecast")
                        .param("latitude", "50.0")
                        .param("longitude", "20.0"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockForecast)));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public WeatherService weatherService() {
            return Mockito.mock(WeatherService.class);
        }
    }
}
