package kitkat.auth.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;

@Component
public class DateUtils {

    private final Clock clock;

    public DateUtils(Clock clock) {
        this.clock = clock;
    }

    public Date getCurrentDate() {
        return new Date(clock.instant().getEpochSecond());
    }

    public Date getCurrentDateWithOffset(long offset) {
        return new Date(clock.instant().getEpochSecond() + offset);
    }

    public long getCurrentDateInMillis() {
        return clock.instant().toEpochMilli();
    }

}
