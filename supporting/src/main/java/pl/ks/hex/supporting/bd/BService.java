package pl.ks.hex.supporting.bd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ks.hex.supporting.ac.AService;

@Component
@RequiredArgsConstructor
public class BService {
    private final AService aService;
}
