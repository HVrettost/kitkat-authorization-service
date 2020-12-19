package kitkat.auth.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;

@Component
public class DateUtils {

    Clock utcClock;

    public DateUtils(@Qualifier("utcClock") Clock clock) {
        this.utcClock = clock;
    }

    public Date getCurrentUTCDate() {
        return new Date(utcClock.millis());
    }

    public Date getCurrentUTCDateWithOffset(long offset) {
        return new Date(utcClock.millis() + offset);
    }
}
