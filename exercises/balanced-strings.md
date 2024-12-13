# Balanced strings

A string containing grouping symbols `{}[]()` is said to be balanced if every open symbol `{[(` has a matching closed symbol `)]}` and the substrings before, after and between each pair of symbols is also balanced. The empty string is considered as balanced.

For example: `{[][]}({})` is balanced, while `][`, `([)]`, `{`, `{(}{}` are not.

Implement the following method:

```java
public static boolean isBalanced(String str) {
    ...
}
```

`isBalanced` returns `true` if `str` is balanced according to the rules explained above. Otherwise, it returns `false`.

Use the coverage criteria studied in classes as follows:

1. Use input space partitioning to design an initial set of inputs. Explain below the characteristics and partition blocks you identified.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators, check if the test cases written so far satisfy *Base Choice Coverage*. If needed, add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Write below the actions you took on each step and the results you obtained.
Use the project in [tp3-balanced-strings](../code/tp3-balanced-strings) to complete this exercise.

## Answer

# Question 1

- **Test 1:** String containing only grouping symbols. (`"()[]{}"`, `"{[()]}"`)
- **Test 2:** String containing grouping symbols and other characters. (`"a(b)c"`, `"{a[b(c)d]e}"`)
- **Test 3:** String containing no grouping symbols (includes the empty string). (`""`, `"abcde"`)
- **Test 4:** String containing only parentheses `()`. (`"((()))"`)
- **Test 5:** String containing only brackets `[]`. (`"[[[]]]"`)
- **Test 6:** String containing only braces `{}`. (`"{{{}}}"`)
- **Test 7:** String containing a combination of different types of symbols `()[]{}`. (`"{[]()}"`)
- **Test 8:** Correctly paired and nested symbols. (`"{[()]}"`)
- **Test 9:** Correctly paired but not nested symbols. (`"()[]{}"`)
- **Test 10:** Incorrectly paired or nested symbols. (`"([)]"`, `"{(}{}"`)
- **Test 11:** Opening symbols without matching closing symbols. (`"((("`, `"{{"`)
- **Test 12:** Closing symbols without matching opening symbols. (`")))"`, `"}}"`)
- **Test 13:** Empty string. (`""`)
- **Test 14:** Even-length string. (`"()[]{}"`)
- **Test 15:** Odd-length string. (`"({})("`)
- **Test 16:** `null` string. (`null`)
- **Test 17:** String containing only opening symbols. (`"((("`, `"[["`, `"{{{"`)
- **Test 18:** String containing only closing symbols. (`")))"`, `"]]"`, `"}}}"`)

# Question 2

### **Instructions Covered by Existing Tests**

By analyzing the test cases, we find that:

- **All conditional statements** (such as checks for opening and closing symbols) are covered by at least one test case.
- **Return branches** (`return false` and `return true`) are also covered.
- **Calls to `isMatchingPair`** are tested with both matching and non-matching pairs.
- **Scenarios with `null` strings, empty strings, strings with only opening or closing symbols, and correctly or incorrectly nested symbols** are all tested.

# Question 3

### **Predicates in the `isBalanced` Method**

1. **Checking for Opening Symbols:**
   ```java
   if (c == '(' || c == '{' || c == '[')
   ```
   - **Basic Conditions:**
      - `c == '('`
      - `c == '{'`
      - `c == '['`

2. **Checking for Closing Symbols:**
   ```java
   else if (c == ')' || c == '}' || c == ']')
   ```
   - **Basic Conditions:**
      - `c == ')'`
      - `c == '}'`
      - `c == ']'`

### **Predicate in the `isMatchingPair` Method**

```java
return (open == '(' && close == ')') ||
       (open == '{' && close == '}') ||
       (open == '[' && close == ']');
```

- **Basic Conditions:**
   - `(open == '(' && close == ')'`
   - `(open == '{' && close == '}'`
   - `(open == '[' && close == ']'`

## **Adding Test Cases to Ensure Complete Base Choice Coverage**

Even though base choice coverage seems to be covered by the existing tests, to strengthen the tests, let's add some additional cases that explicitly target certain conditions.

## **Updating Test Cases**

```java
// New Test Case 19: Pairs with Mixed but Non-Matching Types
@Test
void testMixedNonMatchingPairs() {
    // open == '(' but close != ')'
    assertFalse(isBalanced("(]"), "Pair '(' with ']' should not be balanced.");
    
    // open == '{' but close != '}'
    assertFalse(isBalanced("{)"), "Pair '{' with ')' should not be balanced.");
    
    // open == '[' but close != ']'
    assertFalse(isBalanced("[}"), "Pair '[' with '}' should not be balanced.");
}

// New Test Case 20: Incorrect Closing Symbols in the Middle
@Test
void testIncorrectClosingSymbolsInMiddle() {
    // String with an incorrect closing in the middle
    assertFalse(isBalanced("{[}(])}"), "String with incorrect closing in the middle should not be balanced.");
}

// New Test Case 21: Unmatched Opening and Closing Symbols
@Test
void testUnmatchedOpeningAndClosingSymbols() {
    // Multiple opening symbols without matching closings
    assertFalse(isBalanced("({["), "String with multiple opening symbols without closings should not be balanced.");
    
    // Closing symbols without matching opening symbols
    assertFalse(isBalanced(")]}"), "String with multiple closing symbols without openings should not be balanced.");
}

// New Test Case 22: Mixed Symbols with Non-Symbol Characters
@Test
void testSymbolsWithNonMatchingCharacters() {
    // Mixed symbols with non-symbol characters and incorrect pairs
    assertFalse(isBalanced("{a[b(c]d)e}"), "String with incorrectly nested symbols and characters should not be balanced.");
}
```

# Question 4

### **Mutation Score**

![pit.png](../code/tp3-balanced-strings/pit.png)

- **Mutants Generated:** 21
- **Mutants Killed:** 21
- **Live Mutants:** 0
- **Mutation Score:** **100%**

### **b. Line Coverage**

- **Lines Covered:** 14/15 (93%)
- **Mutations Not Covered:** 0
- **Strength of Tests:** 100%

### **c. Details of Killed Mutants**

The mutators used and their impact:

1. **BooleanTrueReturnValsMutator**
   - **Mutations Generated:** 5
   - **Mutants Killed:** 5 (100%)

2. **BooleanFalseReturnValsMutator**
   - **Mutations Generated:** 1
   - **Mutants Killed:** 1 (100%)

3. **NegateConditionalsMutator**
   - **Mutations Generated:** 15
   - **Mutants Killed:** 15 (100%)

#### **1. Mutation Score of 100%**

A **mutation score of 100%** indicates that the test suite is extremely robust. Every mutation introduced by PIT was detected by at least one test case, demonstrating complete coverage of critical scenarios and conditional branches in the code.

#### **2. Absence of Live Mutants**

The absence of **live mutants** confirms that the current test suite does not leave any part of the code unchecked. This significantly reduces the risk of undetected bugs and ensures high reliability of the `isBalanced` method.