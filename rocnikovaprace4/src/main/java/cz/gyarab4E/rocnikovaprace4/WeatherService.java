package cz.gyarab4E.rocnikovaprace4;

import cz.gyarab4E.rocnikovaprace4.model.WeatherResponse;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WeatherService {
    @Value("#{environment.API_KEY}")
    private String apiKey;
    @Value("#{environment.CITY}")
    private String city;
    private AtomicInteger openweather_temperature;
    private AtomicInteger openweather_cloudiness;
    private AtomicInteger visibility;
    private AtomicInteger openweather_feelslike;
    private AtomicInteger openweather_humidity;
    private AtomicInteger openweather_pressure;
    private AtomicInteger uv_index;
    private AtomicInteger openweather_winddegree;
    private AtomicInteger openweather_windspeed;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MeterRegistry meterRegistry;


    public WeatherService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        openweather_temperature = meterRegistry.gauge("openweather_temperature", new AtomicInteger(0));
        openweather_cloudiness = meterRegistry.gauge("openweather_cloudiness", new AtomicInteger(0));
        visibility = meterRegistry.gauge("visibility", new AtomicInteger(0));
        openweather_feelslike = meterRegistry.gauge("openweather_feelslike", new AtomicInteger(0));
        openweather_humidity = meterRegistry.gauge("openweather_humidity", new AtomicInteger(0));
        openweather_pressure = meterRegistry.gauge("openweather_pressure", new AtomicInteger(0));
        uv_index = meterRegistry.gauge("uv_index", new AtomicInteger(0));
        openweather_winddegree = meterRegistry.gauge("openweather_winddegree", new AtomicInteger(0));
        openweather_windspeed = meterRegistry.gauge("openweather_windspeed", new AtomicInteger(0));
    }

    @Scheduled(fixedRate = 30000000)
    void getWeather() {
        System.out.println("correct");
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject("http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + city, WeatherResponse.class);
        openweather_temperature.set(response.getCurrent().getTemperature());
        openweather_cloudiness.set(response.getCurrent().getCloudcover());
        visibility.set(response.getCurrent().getVisibility());
        openweather_feelslike.set(response.getCurrent().getFeelslike());
        openweather_humidity.set(response.getCurrent().getHumidity());
        openweather_pressure.set(response.getCurrent().getPressure());
        uv_index.set(response.getCurrent().getUv_index());
        openweather_windspeed.set(response.getCurrent().getWind_speed());

    }
}
