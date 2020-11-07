package kitkat.auth.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;

@Component
public class DateUtils {

    public Date getCurrentUTCDate() {
        return new Date(Clock.systemUTC().millis());
    }

    public Date getCurrentUTCDateWithOffset(long offset) {
        return new Date(Clock.systemUTC().millis() + offset);
    }
}
