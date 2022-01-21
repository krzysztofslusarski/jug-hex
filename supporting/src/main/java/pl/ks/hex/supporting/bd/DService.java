package pl.ks.hex.supporting.bd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ks.hex.supporting.ac.CService;

@Component
@RequiredArgsConstructor
public class DService {
    private final CService cService;
}
