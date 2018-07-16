package com.meyoung.tools;

import com.meyoung.actions.ScreenShot;
import com.meyoung.log4j.LoggerControler;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.collections.Lists;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

import static org.testng.internal.EclipseInterface.*;
import static org.testng.internal.EclipseInterface.ASSERT_MIDDLE;
import static org.testng.internal.EclipseInterface.ASSERT_RIGHT;

public class MyAssert extends Assert {
    final  static Logger logger = LoggerControler.getLogger1(MyAssert.class);
        /**
         * Asserts that a condition is true. If it isn't,
         * an AssertionError, with the given message, is thrown.
         * @param condition the condition to evaluate
         * @param message the assertion error message
         */
        static public void assertTrue(boolean condition, String message) {
            if(!condition) {
                failNotEquals(condition, Boolean.TRUE, message);
            }
        }

        /**
         * Asserts that a condition is true. If it isn't,
         * an AssertionError is thrown.
         * @param condition the condition to evaluate
         */
        static public void assertTrue(boolean condition) {
            assertTrue(condition, null);
        }

        /**
         * Asserts that a condition is false. If it isn't,
         * an AssertionError, with the given message, is thrown.
         * @param condition the condition to evaluate
         * @param message the assertion error message
         */
        static public void assertFalse(boolean condition, String message) {
            if(condition) {
                failNotEquals(condition, Boolean.FALSE, message); // TESTNG-81
            }
        }

        /**
         * Asserts that a condition is false. If it isn't,
         * an AssertionError is thrown.
         * @param condition the condition to evaluate
         */
        static public void assertFalse(boolean condition) {
            assertFalse(condition, null);
        }

        /**
         * Fails a test with the given message and wrapping the original exception.
         *
         * @param message the assertion error message
         * @param realCause the original exception
         */
        static public void fail(String message, Throwable realCause) {
            AssertionError ae = new AssertionError(message);
            ae.initCause(realCause);

            throw ae;
        }

        /**
         * Fails a test with the given message.
         * @param message the assertion error message
         */
        static public void fail(String message) {

            //这里加截图，所有失败的就都截图了
            //但是如果是assert之外的，例如打开浏览器，定位元素，出现问题，这里管不到。
            //可以使用github上的https://github.com/NetEase/arrow这个来全局监听。添加截图代码等实现全局异常截图、重跑功能
            ScreenShot.screenShot();

            throw new AssertionError(message);
        }

        /**
         * Fails a test with no message.
         */
        static public void fail() {
            fail(null);
        }

        /**
         * Asserts that two objects are equal. If they are not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(Object actual, Object expected, String message) {
            if((expected == null) && (actual == null)) {
                return;
            }
            if(expected == null ^ actual == null) {
                failNotEquals(actual, expected, message);
            }
            if (expected.getClass().isArray()) {
                assertArrayEquals(actual, expected, message);
                return;
            }
            if (expected.equals(actual) && actual.equals(expected)) {
                return;
            }
            failNotEquals(actual, expected, message);
        }

        /**
         * Asserts that two objects are equal. It they are not, an AssertionError,
         * with given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value (should be an non-null array value)
         * @param message the assertion error message
         */
        private static void assertArrayEquals(Object actual, Object expected, String message) {
            //is called only when expected is an array
            if (actual.getClass().isArray()) {
                int expectedLength = Array.getLength(expected);
                if (expectedLength == Array.getLength(actual)) {
                    for (int i = 0 ; i < expectedLength ; i++) {
                        Object _actual = Array.get(actual, i);
                        Object _expected = Array.get(expected, i);
                        try {
                            assertEquals(_actual, _expected);
                        } catch (AssertionError ae) {
                            failNotEquals(actual, expected, message == null ? "" : message
                                    + " (values at index " + i + " are not the same)");
                        }
                    }
                    //array values matched
                    return;
                } else {
                    failNotEquals(Array.getLength(actual), expectedLength, message == null ? "" : message
                            + " (Array lengths are not the same)");
                }
            }
            failNotEquals(actual, expected, message);
        }

        /**
         * Asserts that two objects are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(Object actual, Object expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two Strings are equal. If they are not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(String actual, String expected, String message) {
            assertEquals((Object) actual, (Object) expected, message);
        }

        /**
         * Asserts that two Strings are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(String actual, String expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two doubles are equal concerning a delta.  If they are not,
         * an AssertionError, with the given message, is thrown.  If the expected
         * value is infinity then the delta value is ignored.
         * @param actual the actual value
         * @param expected the expected value
         * @param delta the absolute tolerable difference between the actual and expected values
         * @param message the assertion error message
         */
        static public void assertEquals(double actual, double expected, double delta, String message) {
            // handle infinity specially since subtracting to infinite values gives NaN and the
            // the following test fails
            if(Double.isInfinite(expected)) {
                if(!(expected == actual)) {
                    failNotEquals(actual, expected, message);
                }
            }
            else if(!(Math.abs(expected - actual) <= delta)) { // Because comparison with NaN always returns false
                failNotEquals(actual, expected, message);
            }
        }

        /**
         * Asserts that two doubles are equal concerning a delta. If they are not,
         * an AssertionError is thrown. If the expected value is infinity then the
         * delta value is ignored.
         * @param actual the actual value
         * @param expected the expected value
         * @param delta the absolute tolerable difference between the actual and expected values
         */
        static public void assertEquals(double actual, double expected, double delta) {
            assertEquals(actual, expected, delta, null);
        }

        /**
         * Asserts that two floats are equal concerning a delta. If they are not,
         * an AssertionError, with the given message, is thrown.  If the expected
         * value is infinity then the delta value is ignored.
         * @param actual the actual value
         * @param expected the expected value
         * @param delta the absolute tolerable difference between the actual and expected values
         * @param message the assertion error message
         */
        static public void assertEquals(float actual, float expected, float delta, String message) {
            // handle infinity specially since subtracting to infinite values gives NaN and the
            // the following test fails
            if(Float.isInfinite(expected)) {
                if(!(expected == actual)) {
                    failNotEquals(actual, expected, message);
                }
            }
            else if(!(Math.abs(expected - actual) <= delta)) {
                failNotEquals(actual, expected, message);
            }
        }

        /**
         * Asserts that two floats are equal concerning a delta. If they are not,
         * an AssertionError is thrown. If the expected
         * value is infinity then the delta value is ignored.
         * @param actual the actual value
         * @param expected the expected value
         * @param delta the absolute tolerable difference between the actual and expected values
         */
        static public void assertEquals(float actual, float expected, float delta) {
            assertEquals(actual, expected, delta, null);
        }

        /**
         * Asserts that two longs are equal. If they are not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(long actual, long expected, String message) {
            assertEquals(Long.valueOf(actual), Long.valueOf(expected), message);
        }

        /**
         * Asserts that two longs are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(long actual, long expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two booleans are equal. If they are not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(boolean actual, boolean expected, String message) {
            assertEquals( Boolean.valueOf(actual), Boolean.valueOf(expected), message);
        }

        /**
         * Asserts that two booleans are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(boolean actual, boolean expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two bytes are equal. If they are not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(byte actual, byte expected, String message) {
            assertEquals(Byte.valueOf(actual), Byte.valueOf(expected), message);
        }

        /**
         * Asserts that two bytes are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(byte actual, byte expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two chars are equal. If they are not,
         * an AssertionFailedError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(char actual, char expected, String message) {
            assertEquals(Character.valueOf(actual), Character.valueOf(expected), message);
        }

        /**
         * Asserts that two chars are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(char actual, char expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two shorts are equal. If they are not,
         * an AssertionFailedError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(short actual, short expected, String message) {
            assertEquals(Short.valueOf(actual), Short.valueOf(expected), message);
        }

        /**
         * Asserts that two shorts are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(short actual, short expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two ints are equal. If they are not,
         * an AssertionFailedError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(int actual,  int expected, String message) {
            assertEquals(Integer.valueOf(actual), Integer.valueOf(expected), message);
        }

        /**
         * Asserts that two ints are equal. If they are not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(int actual, int expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that an object isn't null. If it is,
         * an AssertionError is thrown.
         * @param object the assertion object
         */
        static public void assertNotNull(Object object) {
            assertNotNull(object, null);
        }

        /**
         * Asserts that an object isn't null. If it is,
         * an AssertionFailedError, with the given message, is thrown.
         * @param object the assertion object
         * @param message the assertion error message
         */
        static public void assertNotNull(Object object, String message) {
            if (object == null) {
                String formatted = "";
                if(message != null) {
                    formatted = message + " ";
                }
                fail(formatted + "expected object to not be null");
            }
            assertTrue(object != null, message);
        }

        /**
         * Asserts that an object is null. If it is not,
         * an AssertionError, with the given message, is thrown.
         * @param object the assertion object
         */
        static public void assertNull(Object object) {
            assertNull(object, null);
        }

        /**
         * Asserts that an object is null. If it is not,
         * an AssertionFailedError, with the given message, is thrown.
         * @param object the assertion object
         * @param message the assertion error message
         */
        static public void assertNull(Object object, String message) {
            if (object != null) {
                failNotSame(object, null, message);
            }
        }

        /**
         * Asserts that two objects refer to the same object. If they do not,
         * an AssertionFailedError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertSame(Object actual, Object expected, String message) {
            if(expected == actual) {
                return;
            }
            failNotSame(actual, expected, message);
        }

        /**
         * Asserts that two objects refer to the same object. If they do not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertSame(Object actual, Object expected) {
            assertSame(actual, expected, null);
        }

        /**
         * Asserts that two objects do not refer to the same objects. If they do,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertNotSame(Object actual, Object expected, String message) {
            if(expected == actual) {
                failSame(actual, expected, message);
            }
        }

        /**
         * Asserts that two objects do not refer to the same object. If they do,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertNotSame(Object actual, Object expected) {
            assertNotSame(actual, expected, null);
        }

        static private void failSame(Object actual, Object expected, String message) {
            String formatted = "";
            if(message != null) {
                formatted = message + " ";
            }
            fail(formatted + ASSERT_LEFT2 + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
        }

        static private void failNotSame(Object actual, Object expected, String message) {
            String formatted = "";
            if(message != null) {
                formatted = message + " ";
            }
            fail(formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
        }

        static private void failNotEquals(Object actual , Object expected, String message ) {

            fail(format(actual, expected, message));
        }

        static String format(Object actual, Object expected, String message) {
            String formatted = "";
            if (null != message) {
                formatted = message + " ";
            }

            return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
        }

        /**
         * Asserts that two collections contain the same elements in the same order. If they do not,
         * an AssertionError is thrown.
         *
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(Collection<?> actual, Collection<?> expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two collections contain the same elements in the same order. If they do not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(Collection<?> actual, Collection<?> expected, String message) {
            if(actual == expected) {
                return;
            }

            if (actual == null || expected == null) {
                if (message != null) {
                    fail(message);
                } else {
                    fail("Collections not equal: expected: " + expected + " and actual: " + actual);
                }
            }

            assertEquals(actual.size(), expected.size(), (message == null ? "" : message + ": ") + "lists don't have the same size");

            Iterator<?> actIt = actual.iterator();
            Iterator<?> expIt = expected.iterator();
            int i = -1;
            while(actIt.hasNext() && expIt.hasNext()) {
                i++;
                Object e = expIt.next();
                Object a = actIt.next();
                String explanation = "Lists differ at element [" + i + "]: " + e + " != " + a;
                String errorMessage = message == null ? explanation : message + ": " + explanation;

                assertEquals(a, e, errorMessage);
            }
        }

        /** Asserts that two iterators return the same elements in the same order. If they do not,
         * an AssertionError is thrown.
         * Please note that this assert iterates over the elements and modifies the state of the iterators.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(Iterator<?> actual, Iterator<?> expected) {
            assertEquals(actual, expected, null);
        }

        /** Asserts that two iterators return the same elements in the same order. If they do not,
         * an AssertionError, with the given message, is thrown.
         * Please note that this assert iterates over the elements and modifies the state of the iterators.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(Iterator<?> actual, Iterator<?> expected, String message) {
            if(actual == expected) {
                return;
            }

            if(actual == null || expected == null) {
                if(message != null) {
                    fail(message);
                } else {
                    fail("Iterators not equal: expected: " + expected + " and actual: " + actual);
                }
            }

            int i = -1;
            while(actual.hasNext() && expected.hasNext()) {

                i++;
                Object e = expected.next();
                Object a = actual.next();
                String explanation = "Iterators differ at element [" + i + "]: " + e + " != " + a;
                String errorMessage = message == null ? explanation : message + ": " + explanation;

                assertEquals(a, e, errorMessage);

            }

            if(actual.hasNext()) {

                String explanation = "Actual iterator returned more elements than the expected iterator.";
                String errorMessage = message == null ? explanation : message + ": " + explanation;
                fail(errorMessage);

            } else if(expected.hasNext()) {

                String explanation = "Expected iterator returned more elements than the actual iterator.";
                String errorMessage = message == null ? explanation : message + ": " + explanation;
                fail(errorMessage);

            }

        }

        /** Asserts that two iterables return iterators with the same elements in the same order. If they do not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(Iterable<?> actual, Iterable<?> expected) {
            assertEquals(actual, expected, null);
        }

        /** Asserts that two iterables return iterators with the same elements in the same order. If they do not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(Iterable<?> actual, Iterable<?> expected, String message) {
            if(actual == expected) {
                return;
            }

            if(actual == null || expected == null) {
                if(message != null) {
                    fail(message);
                } else {
                    fail("Iterables not equal: expected: " + expected + " and actual: " + actual);
                }
            }

            Iterator<?> actIt = actual.iterator();
            Iterator<?> expIt = expected.iterator();

            assertEquals(actIt, expIt, message);
        }




        /**
         * Asserts that two arrays contain the same elements in the same order. If they do not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(Object[] actual, Object[] expected, String message) {
            if(actual == expected) {
                return;
            }

            if ((actual == null && expected != null) || (actual != null && expected == null)) {
                if (message != null) {
                    fail(message);
                } else {
                    fail("Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual));
                }
            }
            assertEquals(Arrays.asList(actual), Arrays.asList(expected), message);
        }

        /**
         * Asserts that two arrays contain the same elements in no particular order. If they do not,
         * an AssertionError, with the given message, is thrown.
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEqualsNoOrder(Object[] actual, Object[] expected, String message) {
            if(actual == expected) {
                return;
            }

            if ((actual == null && expected != null) || (actual != null && expected == null)) {
                failAssertNoEqual(
                        "Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual),
                        message);
            }

            if (actual.length != expected.length) {
                failAssertNoEqual(
                        "Arrays do not have the same size:" + actual.length + " != " + expected.length,
                        message);
            }

            List<Object> actualCollection = Lists.newArrayList();
            for (Object a : actual) {
                actualCollection.add(a);
            }
            for (Object o : expected) {
                actualCollection.remove(o);
            }
            if (actualCollection.size() != 0) {
                failAssertNoEqual(
                        "Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual),
                        message);
            }
        }

        private static void failAssertNoEqual(String defaultMessage, String message) {
            if (message != null) {
                fail(message);
            } else {
                fail(defaultMessage);
            }
        }

        /**
         * Asserts that two arrays contain the same elements in the same order. If they do not,
         * an AssertionError is thrown.
         *
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(Object[] actual, Object[] expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Asserts that two arrays contain the same elements in no particular order. If they do not,
         * an AssertionError is thrown.
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEqualsNoOrder(Object[] actual, Object[] expected) {
            assertEqualsNoOrder(actual, expected, null);
        }

        /**
         * Asserts that two arrays contain the same elements in the same order. If they do not,
         * an AssertionError is thrown.
         *
         * @param actual the actual value
         * @param expected the expected value
         */
        static public void assertEquals(final byte[] actual, final byte[] expected) {
            assertEquals(actual, expected, "");
        }

        /**
         * Asserts that two arrays contain the same elements in the same order. If they do not,
         * an AssertionError, with the given message, is thrown.
         *
         * @param actual the actual value
         * @param expected the expected value
         * @param message the assertion error message
         */
        static public void assertEquals(final byte[] actual, final byte[] expected, final String message) {
            if(expected == actual) {
                return;
            }
            if(null == expected) {
                fail("expected a null array, but not null found. " + message);
            }
            if(null == actual) {
                fail("expected not null array, but null found. " + message);
            }

            assertEquals(actual.length, expected.length, "arrays don't have the same size. " + message);

            for(int i= 0; i < expected.length; i++) {
                if(expected[i] != actual[i]) {
                    fail("arrays differ firstly at element [" + i +"]; "
                            + "expected value is <" + expected[i] +"> but was <"
                            + actual[i] + ">. "
                            + message);
                }
            }
        }

        /**
         * Asserts that two sets are equal.
         */
        static public void assertEquals(Set<?> actual, Set<?> expected) {
            assertEquals(actual, expected, null);
        }

        /**
         * Assert set equals
         */
        static public void assertEquals(Set<?> actual, Set<?> expected, String message) {
            if (actual == expected) {
                return;
            }

            if (actual == null || expected == null) {
                // Keep the back compatible
                if (message == null) {
                    fail("Sets not equal: expected: " + expected + " and actual: " + actual);
                } else {
                    failNotEquals(actual, expected, message);
                }
            }

            if (!actual.equals(expected)) {
                if (message == null) {
                    fail("Sets differ: expected " + expected + " but got " + actual);
                } else {
                    failNotEquals(actual, expected, message);
                }
            }
        }

        /**
         * Asserts that two maps are equal.
         */
        static public void assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
            if (actual == expected) {
                return;
            }

            if (actual == null || expected == null) {
                fail("Maps not equal: expected: " + expected + " and actual: " + actual);
            }

            if (actual.size() != expected.size()) {
                fail("Maps do not have the same size:" + actual.size() + " != " + expected.size());
            }

            Set<?> entrySet = actual.entrySet();
            for (Object anEntrySet : entrySet) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) anEntrySet;
                Object key = entry.getKey();
                Object value = entry.getValue();
                Object expectedValue = expected.get(key);
                assertEquals(value, expectedValue, "Maps do not match for key:" + key + " actual:" + value
                        + " expected:" + expectedValue);
            }

        }

        /////
        // assertNotEquals
        //

        public static void assertNotEquals(Object actual1, Object actual2, String message) {
            boolean fail = false;
            try {
                org.testng.Assert.assertEquals(actual1, actual2);
                fail = true;
            } catch (AssertionError e) {
            }

            if (fail) {
                org.testng.Assert.fail(message);
            }
        }

        public static void assertNotEquals(Object actual1, Object actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(String actual1, String actual2, String message) {
            assertNotEquals((Object) actual1, (Object) actual2, message);
        }

        static void assertNotEquals(String actual1, String actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(long actual1, long actual2, String message) {
            assertNotEquals(Long.valueOf(actual1), Long.valueOf(actual2), message);
        }

        static void assertNotEquals(long actual1, long actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(boolean actual1, boolean actual2, String message) {
            assertNotEquals(Boolean.valueOf(actual1), Boolean.valueOf(actual2), message);
        }

        static void assertNotEquals(boolean actual1, boolean actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(byte actual1, byte actual2, String message) {
            assertNotEquals(Byte.valueOf(actual1), Byte.valueOf(actual2), message);
        }

        static void assertNotEquals(byte actual1, byte actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(char actual1, char actual2, String message) {
            assertNotEquals(Character.valueOf(actual1), Character.valueOf(actual2), message);
        }

        static void assertNotEquals(char actual1, char actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(short actual1, short actual2, String message) {
            assertNotEquals(Short.valueOf(actual1), Short.valueOf(actual2), message);
        }

        static void assertNotEquals(short actual1, short actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static void assertNotEquals(int actual1, int actual2, String message) {
            assertNotEquals(Integer.valueOf(actual1), Integer.valueOf(actual2), message);
        }

        static void assertNotEquals(int actual1, int actual2) {
            assertNotEquals(actual1, actual2, null);
        }

        static public void assertNotEquals(float actual1, float actual2, float delta, String message) {
            boolean fail = false;
            try {
                org.testng.Assert.assertEquals(actual1, actual2, delta, message);
                fail = true;
            } catch (AssertionError e) {

            }

            if (fail) {
                org.testng.Assert.fail(message);
            }
        }

        static public void assertNotEquals(float actual1, float actual2, float delta) {
            assertNotEquals(actual1, actual2, delta, null);
        }

        static public void assertNotEquals(double actual1, double actual2, double delta, String message) {
            boolean fail = false;
            try {
                org.testng.Assert.assertEquals(actual1, actual2, delta, message);
                fail = true;
            } catch (AssertionError e) {

            }

            if (fail) {
                org.testng.Assert.fail(message);
            }
        }

        static public void assertNotEquals(double actual1, double actual2, double delta) {
            assertNotEquals(actual1, actual2, delta, null);
        }

        /**
         * This interface facilitates the use of {@link #expectThrows} from Java 8. It allows
         * method references to both void and non-void methods to be passed directly into
         * expectThrows without wrapping, even if they declare checked exceptions.
         * <p/>
         * This interface is not meant to be implemented directly.
         */
        public interface ThrowingRunnable {
            void run() throws Throwable;
        }

        /**
         * Asserts that {@code runnable} throws an exception when invoked. If it does not, an
         * {@link AssertionError} is thrown.
         *
         * @param runnable A function that is expected to throw an exception when invoked
         * @since 6.9.5
         */
        public static void assertThrows(org.testng.Assert.ThrowingRunnable runnable) {
            assertThrows(Throwable.class, runnable);
        }

        /**
         * Asserts that {@code runnable} throws an exception of type {@code throwableClass} when
         * executed. If it does not throw an exception, an {@link AssertionError} is thrown. If it
         * throws the wrong type of exception, an {@code AssertionError} is thrown describing the
         * mismatch; the exception that was actually thrown can be obtained by calling {@link
         * AssertionError#getCause}.
         *
         * @param throwableClass the expected type of the exception
         * @param runnable       A function that is expected to throw an exception when invoked
         * @since 6.9.5
         */
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        public static <T extends Throwable> void assertThrows(Class<T> throwableClass, org.testng.Assert.ThrowingRunnable runnable) {
            expectThrows(throwableClass, runnable);
        }

        /**
         * Asserts that {@code runnable} throws an exception of type {@code throwableClass} when
         * executed and returns the exception. If {@code runnable} does not throw an exception, an
         * {@link AssertionError} is thrown. If it throws the wrong type of exception, an {@code
         * AssertionError} is thrown describing the mismatch; the exception that was actually thrown can
         * be obtained by calling {@link AssertionError#getCause}.
         *
         * @param throwableClass the expected type of the exception
         * @param runnable       A function that is expected to throw an exception when invoked
         * @return The exception thrown by {@code runnable}
         * @since 6.9.5
         */
        public static <T extends Throwable> T expectThrows(Class<T> throwableClass, org.testng.Assert.ThrowingRunnable runnable) {
            try {
                runnable.run();
            } catch (Throwable t) {
                if (throwableClass.isInstance(t)) {
                    return throwableClass.cast(t);
                } else {
                    String mismatchMessage = String.format("Expected %s to be thrown, but %s was thrown",
                            throwableClass.getSimpleName(), t.getClass().getSimpleName());

                    throw new AssertionError(mismatchMessage, t);
                }
            }
            String message = String.format("Expected %s to be thrown, but nothing was thrown",
                    throwableClass.getSimpleName());
            throw new AssertionError(message);
        }

    /**
     * 断言字符串是否已xxx开头
     * @param content 待断言字符串
     * @param prefix 前缀表达式
     * @param message 提示校验开始信息
     */
        public static void  assertStartWith(String content,String prefix,String message){
            if(message!=null){
                logger.info(message);
            }
            if(content.startsWith(prefix)){
                logger.info("前缀匹配成功");
            }else{
                logger.info("前缀匹配失败！\n待校验字符串："+content +"\n预期前缀为："+prefix);
                MyAssert.fail();
            }
        }
    /**
     * 断言字符串是否已xxx开头
     * @param content 待断言字符串
     * @param prefix 前缀表达式
     */
    public static void  assertStartWith(String content,String prefix){
        assertStartWith(content,prefix,null);
    }

    /**
     * 断言字符串是否已xxx结尾
     * @param content 待断言字符串
     * @param prefix 后缀表达式
     * @param message 提示校验开始信息
     */
    public static void  assertEndWith(String content,String prefix,String message){
        if(message!=null){
            logger.info(message);
        }
        if(content.endsWith(prefix)){
            logger.info("后缀匹配成功");
        }else{
            logger.info("后缀匹配失败！\n待校验字符串："+content +"\n预期后缀为："+prefix);
            MyAssert.fail();
        }
    }

    /**
     * 断言字符串是否已xxx结尾
     * @param content 待断言字符串
     * @param prefix 后缀表达式
     */
    public static void  assertEndWith(String content,String prefix){
        assertEndWith(content,prefix,null);
    }

    /**
     * 根据正则表达式断言是否匹配
     * @param matcher 待匹配字符串
     * @param regex 正则表达式
     * @param message 提示校验开始信息
     */
    public static void  assertMatch(String matcher,String regex,String message){
        if(message!=null){
            logger.info(message);
        }
        if(Pattern.matches(regex,matcher)){
            logger.info("正则表达式匹配成功");
        }else{
            logger.info("正则表达式匹配失败！\n待校验字符串："+matcher +"\n预期正则表达式为："+regex);
            MyAssert.fail();
        }
    }

    /**
     * 根据正则表达式断言是否匹配
     * @param matcher 待匹配字符串
     * @param regex 正则表达式
     */
    public static void  assertMatch(String matcher,String regex){
        assertMatch(matcher,regex,null);
    }
}
