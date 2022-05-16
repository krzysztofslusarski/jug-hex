package pl.ks.hex.police;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class PoliceConfiguration {
    @Bean
    PoliceService policeService() {
        return new PoliceServiceImpl();
    }
}
