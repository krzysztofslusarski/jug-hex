package pl.ks.hex.police;

import gov.policja.SerwisPolicyjny;
import java.util.Map;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

class PoliceServiceImpl implements PoliceService {
    public static final String CONVICTED_RESULT = "jest";

    @Override
    public ConvictedStatus checkForCrimes(FirstName firstName, LastName lastName) {
        String result = SerwisPolicyjny.czyJestKarany(Map.of("imię", firstName.getValue(), "nazwisko", lastName.getValue()));
        if (result.equalsIgnoreCase(CONVICTED_RESULT)) {
            return ConvictedStatus.CONVICTED;
        }
        return ConvictedStatus.CLEAN;
    }
}
