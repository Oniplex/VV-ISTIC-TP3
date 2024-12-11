# On Assertions

Answer the following questions:

---

### 1. The following assertion fails `assertTrue(3 * .4 == 1.2)`. Explain why and describe how this type of check should be done.

The assertion `assertTrue(3 * 0.4 == 1.2)` fails due to floating-point precision errors. Computers represent decimal numbers in binary, which can lead to small rounding differences.

**Proper Check:**

Use a tolerance (epsilon) to compare the values:

```java
assertEquals(1.2, 3 * 0.4, 1e-9);
```

This method checks if the two numbers are equal within a small margin of error, accounting for precision limitations.

---

### 2. What is the difference between `assertEquals` and `assertSame`? Show scenarios where they produce the same result and scenarios where they do not produce the same result.

**Difference Between `assertEquals` and `assertSame`:**

- **`assertEquals`**: Checks if two objects are *equal in value*. It uses the `.equals()` method for comparison.
- **`assertSame`**: Checks if two references point to the *exact same object* (i.e., reference equality).

---

#### Scenarios Where They Produce the Same Result:

When both assertions are comparing the same object reference.

```java
String str = "test";

assertEquals(str, str); // Passes
assertSame(str, str);   // Passes
```

---

#### Scenarios Where They Do Not Produce the Same Result:

When two separate objects have the same value but are different instances.

```java
String str1 = new String("test");
String str2 = new String("test");

assertEquals(str1, str2); // Passes (values are equal)
assertSame(str1, str2);   // Fails (different object references)
```

---

### 3. In classes we saw that `fail` is useful to mark code that should not be executed because an exception was expected before. Find other uses for `fail`. Explain the use case and add an example.

**Additional Uses for `fail`:**

Beyond marking code that shouldn’t be executed because an exception was expected, `fail` can be used to:

1. **Indicate Unreachable Code Paths:** Ensure certain branches of code are never executed. This is useful in scenarios like exhaustive `switch` statements where all possible cases are handled, and reaching the `default` case would indicate an unexpected scenario.

2. **Mark Incomplete or Not Yet Implemented Tests:** Serve as a placeholder for tests that are planned but not yet implemented, reminding developers to complete them.

3. **Validate Conditions That Should Never Occur:** Assert that certain conditions are never met during the execution of a test, ensuring the integrity of the test logic.

---

#### **Use Case 1: Indicating Unreachable Code Paths**

When testing methods with exhaustive logic (like `switch` statements), we could include a `fail` in the `default` case to ensure that all possible scenarios are handled. If the `default` case is ever reached, it indicates that an unexpected value was encountered.

**Example:**

```java
@Test
public void testDayOfWeekSwitch() {
    Day day = Day.MONDAY; // Assume Day is an enum with all days

    switch (day) {
        case MONDAY:
        case TUESDAY:
        case WEDNESDAY:
        case THURSDAY:
        case FRIDAY:
        case SATURDAY:
        case SUNDAY:
            // Handle each day
            break;
        default:
            fail("Unexpected day: " + day);
    }

    // Additional assertions...
}
```

---

#### **Use Case 2: Marking Incomplete Tests**

When writing tests, we can use `fail` to mark tests that are yet to be implemented. This ensures that incomplete tests are flagged during the test run, reminding developers to complete them.

**Example:**

```java
@Test
public void testFeatureX() {
    // TODO: Implement this test
    fail("Test for Feature X is not yet implemented.");
}
```

---

#### **Use Case 3: Validating Impossible Conditions**

In certain scenarios, some conditions should logically never occur. Using `fail` can enforce this expectation by causing the test to fail if such a condition is met, highlighting potential issues in the code.

**Example:**

```java
@Test
public void testCalculateDiscount() {
    double discount = calculateDiscount(-5); // Assume discounts can't be negative

    if (discount < 0) {
        fail("Discount should never be negative.");
    }

    // Additional assertions to verify discount logic...
}
```

Using `fail` in these contexts helps maintain robust and reliable test suites by enforcing expectations and highlighting unexpected behaviors.

---

### 4. In JUnit 4, an exception was expected using the `@Test` annotation, while in JUnit 5 there is a special assertion method `assertThrows`. In your opinion, what are the advantages of this new way of checking expected exceptions?

1. **Precise Exception Location:**
   - **JUnit 4 (`@Test(expected=...)`):** The expected exception applies to the entire test method. Any exception of the specified type, regardless of where it occurs, will make the test pass. This can obscure the actual source of the exception.
   - **JUnit 5 (`assertThrows`):** Allows specifying exactly which code block should throw the exception, enhancing precision and reducing false positives.

2. **Exception Inspection:**
   - **JUnit 4:** Limited ability to inspect the exception details since the exception is only checked by type.
   - **JUnit 5:** `assertThrows` returns the exception object, enabling further assertions on the exception’s message, cause, or other properties.

3. **Improved Readability and Maintainability:**
   - **JUnit 4:** Using the `expected` parameter can make tests less readable, especially with multiple scenarios in a single test.
   - **JUnit 5:** Clear and explicit assertion within the test body improves readability and makes the test intent clearer.
