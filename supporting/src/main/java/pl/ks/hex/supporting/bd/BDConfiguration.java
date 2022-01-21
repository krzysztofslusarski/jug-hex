package pl.ks.hex.supporting.bd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import pl.ks.hex.supporting.ac.AService;
import pl.ks.hex.supporting.ac.CService;

//@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class BDConfiguration {
    private final AService aService;
    private final CService cService;

    @Bean
    BService bService() {
        return new BService(aService);
    }

    @Bean
    DService dService() {
        return new DService(cService);
    }
}
