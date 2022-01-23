package pl.ks.hex.police;

import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

public interface PoliceService {
    ConvictedStatus checkForCrimes(FirstName firstName, LastName lastName);
}
