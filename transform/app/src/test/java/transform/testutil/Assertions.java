package transform.testutil;

import static org.junit.jupiter.api.Assertions.*;

import org.opentest4j.AssertionFailedError;

public class Assertions {
    private static void fail(Object expected, Object actual, String message1, Throwable cause) {
        final var message2 = AssertionErrorFormatter.format(expected, actual, message1);
        throw new AssertionFailedError(message2, expected, actual, cause);
    }

    public static void assertEqualsAny(Object[] expected, Object actual, String message) {
        var succeeded = false;
        var errors = new CompositeException();

        for (var i = 0; i < expected.length; ++i) {
            try {
                assertEquals(expected[i], actual);
                succeeded = true;
                break;
            } catch (AssertionError err) {
                errors.add(err);
            }
        }

        if (!succeeded) {
            fail(expected, actual, message, errors);
        }
    }

    public static void assertEqualsAny(Object[] expected, Object actual) {
        assertEqualsAny(expected, actual, null);
    }
}
