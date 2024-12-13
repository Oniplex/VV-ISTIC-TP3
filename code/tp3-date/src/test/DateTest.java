package test;

import main.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    void testConstructorWithValidDate() {
        assertDoesNotThrow(() -> new Date(15, 8, 2023));
    }

    @Test
    void testConstructorWithInvalidDay() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Date(32, 1, 2023));
        assertEquals("Invalid date provided.", exception.getMessage());
    }

    @Test
    void testConstructorWithInvalidMonth() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Date(15, 13, 2023));
        assertEquals("Invalid date provided.", exception.getMessage());
    }

    @Test
    void testConstructorWithInvalidYear() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Date(15, 8, 0));
        assertEquals("Invalid date provided.", exception.getMessage());
    }

    @Test
    void testConstructorWithLeapDayValid() {
        assertDoesNotThrow(() -> new Date(29, 2, 2024));
    }

    @Test
    void testConstructorWithLeapDayInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Date(29, 2, 2023));
        assertEquals("Invalid date provided.", exception.getMessage());
    }

    @Test
    void testIsValidDate_ValidDates() {
        assertTrue(Date.isValidDate(1, 1, 2023));
        assertTrue(Date.isValidDate(31, 1, 2023));
        assertTrue(Date.isValidDate(30, 4, 2023));
        assertTrue(Date.isValidDate(29, 2, 2024));
    }

    @Test
    void testIsValidDate_InvalidDay() {
        assertFalse(Date.isValidDate(0, 1, 2023));
        assertFalse(Date.isValidDate(32, 1, 2023));
        assertFalse(Date.isValidDate(31, 4, 2023));
    }

    @Test
    void testIsValidDate_InvalidMonth() {
        assertFalse(Date.isValidDate(15, 0, 2023));
        assertFalse(Date.isValidDate(15, 13, 2023));
    }

    @Test
    void testIsValidDate_InvalidYear() {
        assertFalse(Date.isValidDate(15, 8, 0));
        assertFalse(Date.isValidDate(15, 8, -1));
    }

    @Test
    void testIsValidDate_FebruaryNonLeapYear() {
        assertFalse(Date.isValidDate(29, 2, 2023));
        assertTrue(Date.isValidDate(28, 2, 2023));
    }

    @Test
    void testIsValidDate_FebruaryLeapYear() {
        assertTrue(Date.isValidDate(29, 2, 2024));
        assertFalse(Date.isValidDate(30, 2, 2024));
    }

    @Test
    void testIsLeapYear_CommonLeapYears() {
        assertTrue(Date.isLeapYear(2024));
        assertTrue(Date.isLeapYear(2000));
        assertTrue(Date.isLeapYear(2400));
    }

    @Test
    void testIsLeapYear_CommonNonLeapYears() {
        assertFalse(Date.isLeapYear(2023));
        assertFalse(Date.isLeapYear(1900));
        assertFalse(Date.isLeapYear(2100));
    }

    @Test
    void testIsLeapYear_EdgeCases() {
        assertFalse(Date.isLeapYear(100));
        assertTrue(Date.isLeapYear(400));
        assertFalse(Date.isLeapYear(1));
    }

    @Test
    void testNextDate_NormalDay() {
        Date date = new Date(15, 8, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(16, 8, 2023), next);
    }

    @Test
    void testNextDate_EndOfMonth() {
        Date date = new Date(31, 1, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 2, 2023), next);
    }

    @Test
    void testNextDate_EndOfMonth_LeapYear() {
        Date date = new Date(28, 2, 2024);
        Date next = date.nextDate();
        assertEquals(new Date(29, 2, 2024), next);
    }

    @Test
    void testNextDate_EndOfMonth_NonLeapYear() {
        Date date = new Date(28, 2, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2023), next);
    }

    @Test
    void testNextDate_EndOfYear() {
        Date date = new Date(31, 12, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 1, 2024), next);
    }

    @Test
    void testPreviousDate_NormalDay() {
        Date date = new Date(15, 8, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(14, 8, 2023), previous);
    }

    @Test
    void testPreviousDate_StartOfMonth() {
        Date date = new Date(1, 3, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(28, 2, 2023), previous);
    }

    @Test
    void testPreviousDate_StartOfMonth_LeapYear() {
        Date date = new Date(1, 3, 2024);
        Date previous = date.previousDate();
        assertEquals(new Date(29, 2, 2024), previous);
    }

    @Test
    void testPreviousDate_StartOfYear() {
        Date date = new Date(1, 1, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(31, 12, 2022), previous);
    }

    @Test
    void testPreviousDate_FirstDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Date firstDate = new Date(1, 1, 1);
            firstDate.previousDate();
        });
        assertEquals("Year out of valid range.", exception.getMessage());
    }

    @Test
    void testCompareTo_SameDate() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertEquals(0, date1.compareTo(date2));
    }

    @Test
    void testCompareTo_AfterDate() {
        Date date1 = new Date(16, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_BeforeDate() {
        Date date1 = new Date(14, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareTo_DifferentYears() {
        Date date1 = new Date(15, 8, 2024);
        Date date2 = new Date(15, 8, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_DifferentMonths() {
        Date date1 = new Date(15, 9, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_DifferentDays() {
        Date date1 = new Date(16, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_Null() {
        Date date = new Date(15, 8, 2023);
        Exception exception = assertThrows(NullPointerException.class, () -> date.compareTo(null));
        assertEquals("The compared Date is null.", exception.getMessage());
    }

    @Test
    void testEquals_SameObject() {
        Date date = new Date(15, 8, 2023);
        assertEquals(date, date);
    }

    @Test
    void testEquals_EqualObjects() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertEquals(date1, date2);
    }

    @Test
    void testEquals_DifferentDay() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(16, 8, 2023);
        assertNotEquals(date1, date2);
    }

    @Test
    void testEquals_DifferentMonth() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(15, 9, 2023);
        assertNotEquals(date1, date2);
    }

    @Test
    void testEquals_DifferentYear() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(15, 8, 2024);
        assertNotEquals(date1, date2);
    }

    @Test
    void testEquals_Null() {
        Date date = new Date(15, 8, 2023);
        assertNotEquals(null, date);
    }

    @Test
    void testEquals_DifferentObjectType() {
        Date date = new Date(15, 8, 2023);
        String notADate = "15/08/2023";
        assertNotEquals(notADate, date);
    }

    @Test
    void testHashCode_EqualObjects() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(15, 8, 2023);
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        Date date1 = new Date(15, 8, 2023);
        Date date2 = new Date(16, 8, 2023);
        assertNotEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    void testToString_Format() {
        Date date = new Date(5, 7, 2023);
        assertEquals("05/07/2023", date.toString());
    }

    @Test
    void testToString_SingleDigitMonthAndDay() {
        Date date = new Date(1, 1, 2023);
        assertEquals("01/01/2023", date.toString());
    }

    @Test
    void testToString_DoubleDigitMonthAndDay() {
        Date date = new Date(15, 12, 2023);
        assertEquals("15/12/2023", date.toString());
    }

    @Test
    void testNextDate_February28_NonLeapYear() {
        Date date = new Date(28, 2, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2023), next);
    }

    @Test
    void testNextDate_February28_LeapYear() {
        Date date = new Date(28, 2, 2024);
        Date next = date.nextDate();
        assertEquals(new Date(29, 2, 2024), next);
    }

    @Test
    void testNextDate_February29_LeapYear() {
        Date date = new Date(29, 2, 2024);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2024), next);
    }

    @Test
    void testPreviousDate_March1_LeapYear() {
        Date date = new Date(1, 3, 2024);
        Date previous = date.previousDate();
        assertEquals(new Date(29, 2, 2024), previous);
    }

    @Test
    void testPreviousDate_March1_NonLeapYear() {
        Date date = new Date(1, 3, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(28, 2, 2023), previous);
    }

    @Test
    void testPreviousDate_BeforeYear1() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Date date = new Date(1, 1, 1);
            date.previousDate();
        });
        assertEquals("Year out of valid range.", exception.getMessage());
    }

    @Test
    void testBoundary_Year1() {
        Date date = new Date(1, 1, 1);
        assertDoesNotThrow(date::nextDate);
    }

    @Test
    void testBoundary_MaxDay_January() {
        Date date = new Date(31, 1, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 2, 2023), next);
    }

    @Test
    void testBoundary_MaxDay_February_LeapYear() {
        Date date = new Date(29, 2, 2024);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2024), next);
    }

    @Test
    void testBoundary_MaxDay_February_NonLeapYear() {
        Date date = new Date(28, 2, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2023), next);
    }

    @Test
    void testBoundary_MaxDay_April() {
        Date date = new Date(30, 4, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 5, 2023), next);
    }

    @Test
    void testBoundary_MinDay() {
        Date date = new Date(1, 1, 1);
        assertEquals(new Date(1, 1, 1), date);
    }

    @Test
    void testIsValidDate_Feb29_NonLeapYearNotCovered() {
        assertFalse(Date.isValidDate(29, 2, 2021));
    }

    @Test
    void testIsValidDate_December31() {
        assertTrue(Date.isValidDate(31, 12, 2023));
    }

    @Test
    void testIsValidDate_June30() {
        assertTrue(Date.isValidDate(30, 6, 2023));
        assertFalse(Date.isValidDate(31, 6, 2023));
    }

    @Test
    void testIsValidDate_Feb29_CenturyNonLeapYear() {
        assertFalse(Date.isValidDate(29, 2, 1900));
    }

    @Test
    void testIsValidDate_Feb29_CenturyLeapYear() {
        assertTrue(Date.isValidDate(29, 2, 2000));
    }

    @Test
    void testNextDate_EndOfApril() {
        Date date = new Date(30, 4, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 5, 2023), next);
    }

    @Test
    void testNextDate_EndOfJune() {
        Date date = new Date(30, 6, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 7, 2023), next);
    }

    @Test
    void testPreviousDate_AfterJanuary1() {
        Date date = new Date(1, 2, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(31, 1, 2023), previous);
    }

    @Test
    void testPreviousDate_MidMonth() {
        Date date = new Date(15, 5, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(14, 5, 2023), previous);
    }

    @Test
    void testCompareTo_SameYearSameMonthDifferentDays() {
        Date d1 = new Date(10, 5, 2023);
        Date d2 = new Date(11, 5, 2023);
        assertTrue(d1.compareTo(d2) < 0);
    }

    @Test
    void testHashCode_Consistency() {
        Date date = new Date(15, 8, 2023);
        int initialHash = date.hashCode();
        assertEquals(initialHash, date.hashCode());
        assertEquals(initialHash, date.hashCode());
    }

    @Test
    void testEquals_Symmetric() {
        Date d1 = new Date(10, 10, 2023);
        Date d2 = new Date(10, 10, 2023);
        assertEquals(d1, d2);
        assertEquals(d2, d1);
    }

    @Test
    void testEquals_Transitive() {
        Date d1 = new Date(15, 8, 2023);
        Date d2 = new Date(15, 8, 2023);
        Date d3 = new Date(15, 8, 2023);
        assertEquals(d1, d2);
        assertEquals(d2, d3);
        assertEquals(d1, d3);
    }

    @Test
    void testToString_LeadingZerosOnDayAndMonth() {
        Date date = new Date(9, 3, 2023);
        assertEquals("09/03/2023", date.toString());
    }

    @Test
    void testEquals_WithNonDateObject() {
        Date date = new Date(15, 8, 2023);
        Object nonDateObject = new Object();
        assertFalse(date.equals(nonDateObject));
    }

    @Test
    void testEquals_WithStringObject() {
        Date date = new Date(15, 8, 2023);
        String notADate = "15/08/2023";
        assertFalse(date.equals(notADate));
    }

    @Test
    void testIsValidDate_November30() {
        assertTrue(Date.isValidDate(30, 11, 2023));
        assertFalse(Date.isValidDate(31, 11, 2023));
    }

    @Test
    void testNovemberValidDateCreation() {
        Date date = new Date(30, 11, 2023);
        assertEquals("30/11/2023", date.toString());
    }

    @Test
    void testNovemberInvalidDateCreation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Date(31, 11, 2023));
        assertEquals("Invalid date provided.", exception.getMessage());
    }

    @Test
    void testCompareTo_NullThrowsException() {
        Date date = new Date(15, 8, 2023);
        assertThrows(NullPointerException.class, () -> date.compareTo(null));
    }

    @Test
    void testPreviousDate_YearOutOfRange() {
        Date firstDate = new Date(1, 1, 1);
        Exception exception = assertThrows(IllegalArgumentException.class, firstDate::previousDate);
        assertEquals("Year out of valid range.", exception.getMessage());
    }

    @Test
    void testIsValidDate_DayLowerBoundary() {
        assertFalse(Date.isValidDate(0, 5, 2023));
    }

    @Test
    void testIsValidDate_DayUpperBoundary_Valid() {
        assertTrue(Date.isValidDate(31, 1, 2023));
    }

    @Test
    void testIsValidDate_DayUpperBoundary_Invalid() {
        assertFalse(Date.isValidDate(32, 1, 2023));
    }

    @Test
    void testIsValidDate_DayBoundary_February_NonLeapYear() {
        assertTrue(Date.isValidDate(28, 2, 2023));
    }

    @Test
    void testIsValidDate_DayBoundary_February_LeapYear() {
        assertTrue(Date.isValidDate(29, 2, 2024));
    }

    @Test
    void testIsValidDate_DayUpperBoundary_February_NonLeapYear() {
        assertFalse(Date.isValidDate(29, 2, 2023));
    }

    @Test
    void testIsValidDate_DayUpperBoundary_February_LeapYear_Invalid() {
        assertFalse(Date.isValidDate(30, 2, 2024));
    }

    @Test
    void testIsValidDate_MonthLowerBoundary() {
        assertFalse(Date.isValidDate(15, 0, 2023));
    }

    @Test
    void testIsValidDate_MonthUpperBoundary_Valid() {
        assertTrue(Date.isValidDate(15, 12, 2023));
    }

    @Test
    void testIsValidDate_MonthUpperBoundary_Invalid() {
        assertFalse(Date.isValidDate(15, 13, 2023));
    }

    @Test
    void testIsValidDate_YearLowerBoundary() {
        assertTrue(Date.isValidDate(15, 8, 1));
    }

    @Test
    void testIsValidDate_YearUpperBoundary() {
        assertTrue(Date.isValidDate(15, 8, 9999));
    }

    @Test
    void testIsValidDate_YearBoundary_Invalid() {
        assertFalse(Date.isValidDate(15, 8, 0));
    }

    @Test
    void testPreviousDate_StartOfMonth_March_NonLeapYear() {
        Date date = new Date(1, 3, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(28, 2, 2023), previous);
    }

    @Test
    void testPreviousDate_StartOfMonth_March_LeapYear() {
        Date date = new Date(1, 3, 2024);
        Date previous = date.previousDate();
        assertEquals(new Date(29, 2, 2024), previous);
    }

    @Test
    void testPreviousDate_FirstDate_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Date firstDate = new Date(1, 1, 1);
            firstDate.previousDate();
        });
        assertEquals("Year out of valid range.", exception.getMessage());
    }

    @Test
    void testCompareTo_SameYearSameMonth_DayBoundaries() {
        Date date1 = new Date(1, 8, 2023);
        Date date2 = new Date(31, 8, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareTo_DifferentYearsBoundaries() {
        Date date1 = new Date(31, 12, 2023);
        Date date2 = new Date(1, 1, 2024);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareTo_DifferentMonthsBoundaries() {
        Date date1 = new Date(31, 5, 2023);
        Date date2 = new Date(1, 6, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testNextDate_LastValidDate_February_NonLeapYear() {
        Date date = new Date(28, 2, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2023), next);
    }

    @Test
    void testNextDate_LastValidDate_February_LeapYear() {
        Date date = new Date(29, 2, 2024);
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2024), next);
    }

    @Test
    void testNextDate_LastValidDate_December() {
        Date date = new Date(31, 12, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 1, 2024), next);
    }

    @Test
    void testPreviousDate_FirstValidDate_1Jan1() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Date firstDate = new Date(1, 1, 1);
            firstDate.previousDate();
        });
        assertEquals("Year out of valid range.", exception.getMessage());
    }

    @Test
    void testPreviousDate_StartOfMonth_January() {
        Date date = new Date(1, 1, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(31, 12, 2022), previous);
    }

    @Test
    void testToString_LeadingZeros_DayAndMonth() {
        Date date = new Date(5, 7, 2023);
        assertEquals("05/07/2023", date.toString());
    }

    @Test
    void testToString_LeadingZeros_Day() {
        Date date = new Date(9, 12, 2023);
        assertEquals("09/12/2023", date.toString());
    }

    @Test
    void testToString_LeadingZeros_Month() {
        Date date = new Date(15, 3, 2023);
        assertEquals("15/03/2023", date.toString());
    }

    @Test
    void testEquals_DifferentDates_Limits() {
        Date date1 = new Date(1, 1, 2023);
        Date date2 = new Date(31, 12, 2023);
        assertNotEquals(date1, date2);
    }

    @Test
    void testHashCode_DifferentDates_Limits() {
        Date date1 = new Date(1, 1, 2023);
        Date date2 = new Date(31, 12, 2023);
        assertNotEquals(date1.hashCode(), date2.hashCode());
    }
}