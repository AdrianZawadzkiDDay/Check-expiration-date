package pl.adrian.token;

import pl.adrian.token.exceptions.ConfirmPhoneCodeIsExpiredException;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ChangePhoneNumberWithVerificationService {
    private final ClockFactory clockFactory;

    public ChangePhoneNumberWithVerificationService(ClockFactory clockFactory) {
        this.clockFactory = clockFactory;
    }

    public boolean checkExpirationTime(ConfirmPhoneCodeDTO confirmPhoneCodeDTO) throws ConfirmPhoneCodeIsExpiredException {
        ZonedDateTime expiryDate = confirmPhoneCodeDTO.getExpiryDate();
        boolean isConfirmPhoneCodeNotExpired = expiryDate.isAfter(ZonedDateTime.now(clockFactory.get()));
        if (!isConfirmPhoneCodeNotExpired) {
            throw new ConfirmPhoneCodeIsExpiredException();
        }
        return true;
    }

    public ConfirmPhoneCodeDTO createConfirmPhoneCode(UUID userId) {
        UUID id = UUID.randomUUID();
        String code = generateVerificationCode();
        ZonedDateTime expiryDate = calculateExpiryDateForConfirmPhoneCode();

        return new ConfirmPhoneCodeDTO(id, userId, code, expiryDate);
    }

    private ZonedDateTime calculateExpiryDateForConfirmPhoneCode() {
        return ZonedDateTime.now().plusHours(1L);
    }

    private String generateVerificationCode() {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(0, 9999));

        if(code.length() < 4) {
            if(code.length() < 2) {
                code = "000" + code;
            } else if (code.length() < 3) {
                code = "00" + code;
            } else {
                code = "0" + code;
            }
        }

        return code;
    }

}
