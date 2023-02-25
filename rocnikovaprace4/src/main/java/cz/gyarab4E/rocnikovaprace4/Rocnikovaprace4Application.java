package cz.gyarab4E.rocnikovaprace4;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Rocnikovaprace4Application {

    public static void main(String[] args) {
        SpringApplication.run(Rocnikovaprace4Application.class, args);
    }

    @Bean
    public RestTemplate getResttemplate() {
        return new RestTemplate();
    }


}
