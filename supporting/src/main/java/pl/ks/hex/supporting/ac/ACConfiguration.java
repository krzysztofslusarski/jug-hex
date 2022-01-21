package pl.ks.hex.supporting.ac;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import pl.ks.hex.supporting.bd.BService;
import pl.ks.hex.supporting.bd.DService;

//@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class ACConfiguration {
    private final BService bService;
    private final DService dService;

    @Bean
    AService aService() {
        return new AService();
    }

    @Bean
    CService cService() {
        return new CService(bService);
    }
}
