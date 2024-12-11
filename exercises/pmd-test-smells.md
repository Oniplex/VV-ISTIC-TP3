# Detecting test smells with PMD

In folder [`pmd-documentation`](../pmd-documentation) you will find the documentation of a selection of PMD rules designed to catch test smells.
Identify which of the test smells discussed in classes are implemented by these rules.

Use one of the rules to detect a test smell in one of the following projects:

- [Apache Commons Collections](https://github.com/apache/commons-collections)
- [Apache Commons CLI](https://github.com/apache/commons-cli)
- [Apache Commons Math](https://github.com/apache/commons-math)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

Discuss the test smell you found with the help of PMD and propose here an improvement.
Include the improved test code in this file.

## Answer

## Identified Test Smell

The PMD rule `JUnitUseExpected` warns about a common test smell where exceptions are tested using a `try/catch` block together with `Assert.fail()` rather than using the `@Test(expected=...)` annotation provided by JUnit 4. This test smell makes the test code more verbose and less clear about its intention. It also clutters the code with boilerplate try/catch blocks.

## Proposed Improvement

Instead of writing a `try/catch` around the code that should throw the exception and calling `Assert.fail()` if it does not, we can simply annotate the test method with `@Test(expected = NotFiniteNumberException.class)`. This makes it explicit that the test is expected to throw a `NotFiniteNumberException`, and if it does not, the test will fail automatically.

Additionally, the original tests check multiple scenarios (e.g. different inputs) in a single test method. It's often clearer and easier to maintain if each scenario is tested in a separate method, especially when using `@Test(expected=...)`.

Below is an example of how the improved test code might look. Each scenario is now its own test method, making the tests more focused and easier to read.

## Before Improvements

```java
public final class NotFiniteNumberExceptionTest {
    @Test
    public void testCheckSingle() {
        try {
            NotFiniteNumberException.check(Double.POSITIVE_INFINITY);
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
        try {
            NotFiniteNumberException.check(Double.NEGATIVE_INFINITY);
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
        try {
            NotFiniteNumberException.check(Double.NaN);
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
    }

    @Test
    public void testCheckArray() {
        try {
            NotFiniteNumberException.check(new double[] {0, -1, Double.POSITIVE_INFINITY, -2, 3});
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
        try {
            NotFiniteNumberException.check(new double[] {1, Double.NEGATIVE_INFINITY, -2, 3});
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
        try {
            NotFiniteNumberException.check(new double[] {4, 3, -1, Double.NaN, -2, 1});
            Assert.fail("an exception should have been thrown");
        } catch (NotFiniteNumberException e) {
            // Expected
        }
    }
}
```

## After improvements
```java
package org.apache.commons.math4.legacy.exception;

import org.junit.Test;

/**
 *
 */
public final class NotFiniteNumberExceptionTest {

    // Single value checks
    @Test(expected = NotFiniteNumberException.class)
    public void testCheckSinglePositiveInfinity() {
        NotFiniteNumberException.check(Double.POSITIVE_INFINITY);
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testCheckSingleNegativeInfinity() {
        NotFiniteNumberException.check(Double.NEGATIVE_INFINITY);
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testCheckSingleNaN() {
        NotFiniteNumberException.check(Double.NaN);
    }

    // Array checks
    @Test(expected = NotFiniteNumberException.class)
    public void testCheckArrayWithPositiveInfinity() {
        NotFiniteNumberException.check(new double[] {0, -1, Double.POSITIVE_INFINITY, -2, 3});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testCheckArrayWithNegativeInfinity() {
        NotFiniteNumberException.check(new double[] {1, Double.NEGATIVE_INFINITY, -2, 3});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testCheckArrayWithNaN() {
        NotFiniteNumberException.check(new double[] {4, 3, -1, Double.NaN, -2, 1});
    }
}
```

**Advantages of this improvement:**
- The test code is clearer and more concise.
- There’s no need for `try/catch` and `Assert.fail()` constructs.
- Each test explicitly states which exception it expects.
- Scenarios are isolated into separate test methods, improving readability and maintainability.