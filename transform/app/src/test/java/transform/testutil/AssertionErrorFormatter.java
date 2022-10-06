/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

// Copied from org.junit.jupiter.api.AssertionUtils

package transform.testutil;

import org.junit.platform.commons.util.StringUtils;
import org.junit.platform.commons.util.UnrecoverableExceptions;

public class AssertionErrorFormatter {
    static String buildPrefix(String message) {
        return (StringUtils.isNotBlank(message) ? message + " ==> " : "");
    }

    static String getCanonicalName(Class<?> clazz) {
        try {
            String canonicalName = clazz.getCanonicalName();
            return (canonicalName != null ? canonicalName : clazz.getName());
        } catch (Throwable t) {
            UnrecoverableExceptions.rethrowIfUnrecoverable(t);
            return clazz.getName();
        }
    }

    private static String toString(Object obj) {
        if (obj instanceof Class) {
            return getCanonicalName((Class<?>) obj);
        }
        return StringUtils.nullSafeToString(obj);
    }

    private static String getClassName(Object obj) {
        return (obj == null ? "null"
                : obj instanceof Class ? getCanonicalName((Class<?>) obj) : obj.getClass().getName());
    }

    private static String toHash(Object obj) {
        return (obj == null ? "" : "@" + Integer.toHexString(System.identityHashCode(obj)));
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String classAndHash = getClassName(value) + toHash(value);
        // if it's a class, there's no need to repeat the class name contained in the
        // valueString.
        return (value instanceof Class ? "<" + classAndHash + ">" : classAndHash + "<" + valueString + ">");
    }

    static String formatValues(Object expected, Object actual) {
        String expectedString = toString(expected);
        String actualString = toString(actual);
        if (expectedString.equals(actualString)) {
            return String.format("expected: %s but was: %s", formatClassAndValue(expected, expectedString),
                    formatClassAndValue(actual, actualString));
        }
        return String.format("expected: <%s> but was: <%s>", expectedString, actualString);
    }

    public static String format(Object expected, Object actual, String message) {
        return buildPrefix(message) + formatValues(expected, actual);
    }
}
