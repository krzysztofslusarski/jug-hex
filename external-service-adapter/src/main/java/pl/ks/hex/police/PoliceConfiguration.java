package pl.ks.hex.police;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PoliceConfiguration {
    @Bean
    PoliceService policeService() {
        return new PoliceServiceImpl();
    }
}
