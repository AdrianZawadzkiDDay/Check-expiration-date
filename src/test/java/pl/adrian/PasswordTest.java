package pl.adrian;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordTest {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Test
    public void passwordTest() {
        assertTrue(isPasswordValid("Password@example123"));
        assertTrue(isPasswordValid("Password1!"));
        assertFalse(isPasswordValid("Password@exampleMoreThan20Characters"));
    }

    public static boolean isPasswordValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
