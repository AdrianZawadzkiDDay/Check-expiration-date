package pl.adrian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.adrian.token.ChangePhoneNumberWithVerificationService;
import pl.adrian.token.ClockFactory;
import pl.adrian.token.ConfirmPhoneCodeDTO;
import pl.adrian.token.exceptions.ConfirmPhoneCodeIsExpiredException;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserFacadeConfirmPhoneCodeTest {
    private ChangePhoneNumberWithVerificationService service;
    private ClockFactory clockFactory;

    @BeforeEach
    void setUp() {
        clockFactory = Mockito.mock(ClockFactory.class);
        when(clockFactory.get()).thenReturn(Clock.systemDefaultZone());
        service = new ChangePhoneNumberWithVerificationService(clockFactory);
    }

    @Test
    public void shouldCreateConfirmPhoneCode() {
        UUID userId = UUID.randomUUID();
        ConfirmPhoneCodeDTO confirmPhoneCodeDTO = service.createConfirmPhoneCode(userId);
        assertThat(confirmPhoneCodeDTO).isNotNull();
    }

    @Test
    public void shouldNotThrowConfirmPhoneCodeIsExpiredException() throws ConfirmPhoneCodeIsExpiredException {
        UUID userId = UUID.randomUUID();
        ConfirmPhoneCodeDTO confirmPhoneCodeDTO = service.createConfirmPhoneCode(userId);
        assertThat(confirmPhoneCodeDTO).isNotNull();

        ZonedDateTime data = confirmPhoneCodeDTO.getExpiryDate();

        setUpClock(data, 0);
        assertThat(service.checkExpirationTime(confirmPhoneCodeDTO)).isTrue();
    }

    @Test
    public void shouldThrowConfirmPhoneCodeIsExpiredException() {
        UUID userId = UUID.randomUUID();
        ConfirmPhoneCodeDTO confirmPhoneCodeDTO = service.createConfirmPhoneCode(userId);
        assertThat(confirmPhoneCodeDTO).isNotNull();

        ZonedDateTime data = confirmPhoneCodeDTO.getExpiryDate();

        setUpClock(data, 3);
        assertThrows(ConfirmPhoneCodeIsExpiredException.class,
                () -> service.checkExpirationTime(confirmPhoneCodeDTO));
    }


    private void setUpClock(ZonedDateTime data, int hour) {
        int year = data.getYear();
        int month = data.getMonthValue();
        int day = data.getDayOfMonth();
        int newHour = data.getHour() + hour;
        if(newHour > 24) {
            day = day + 1;
            newHour = newHour - 24;
        }
        int minute = data.getMinute();
        when(clockFactory.get()).thenReturn(Clock.fixed(Instant.from(ZonedDateTime.of(year,
                month, day, newHour, minute, 0, 0, ZoneId.systemDefault())), ZoneId.systemDefault()));
    }
}
