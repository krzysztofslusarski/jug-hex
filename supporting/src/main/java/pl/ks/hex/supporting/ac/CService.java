package pl.ks.hex.supporting.ac;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ks.hex.supporting.bd.BService;

@Component
@RequiredArgsConstructor
public class CService {
    private final BService bService;
}
