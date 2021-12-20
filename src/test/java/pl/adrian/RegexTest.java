package pl.adrian;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegexTest {

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    @Test
    public void testMailRegexExpression() {
        assertTrue(isValid("name.surname@ptmsoft.pl"));
        assertTrue(isValid("yorinobu@arasaka.us"));
        assertFalse(isValid("mailWithoutDot@us"));
        assertFalse(isValid("mailWithoutAtSign.us"));
    }

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Test
    public void testWithSmallerRegex() {
        assertTrue(isValidWithSmallerRegex("name.surname@ptmsoft.pl"));
        assertTrue(isValidWithSmallerRegex("name.surname@ptmsoft.PL"));
        assertFalse(isValidWithSmallerRegex("mailWithoutDot@us"));
        assertFalse(isValidWithSmallerRegex("mailWithoutAtSign.us"));
        assertTrue(isValidWithSmallerRegex("012345678901234567890123456789012345678901234567890123456789" +
                "tooLong@ptmsoft.pl"));
        assertTrue(isValidWithSmallerRegex("-startsWith-symbol@mail.pl"));
        assertTrue(isValidWithSmallerRegex(".startsWith-symbol@mail.pl"));
    }

    public boolean isValidWithSmallerRegex(String email) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
