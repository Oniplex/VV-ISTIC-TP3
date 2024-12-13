package main;

public class Date implements Comparable<Date> {
    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructor for the Date class.
     * Throws IllegalArgumentException if the provided date is invalid.
     *
     * @param day   Day of the month
     * @param month Month of the year
     * @param year  Year
     */
    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date provided.");
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Checks if the given date is valid.
     *
     * @param day   Day of the month
     * @param month Month of the year
     * @param year  Year
     * @return true if the date is valid, false otherwise
     */
    public static boolean isValidDate(int day, int month, int year) {
        if (year < 1) { // Assuming year must be positive
            return false;
        }

        if (month < 1 || month > 12) {
            return false;
        }

        int maxDay = getDaysInMonth(month, year);

        return day >= 1 && day <= maxDay;
    }

    /**
     * Determines if the given year is a leap year.
     *
     * @param year Year to check
     * @return true if leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        // Leap year rules:
        // - Every year divisible by 4 is a leap year
        // - Except for years divisible by 100, unless also divisible by 400
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

    /**
     * Returns the number of days in a given month for a specific year.
     *
     * @param month Month (1-12)
     * @param year  Year
     * @return Number of days in the month
     */
    private static int getDaysInMonth(int month, int year) {
        return switch (month) {
            case 2 -> isLeapYear(year) ? 29 : 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    /**
     * Returns a new Date instance representing the next day.
     *
     * @return Date of the next day
     */
    public Date nextDate() {
        int newDay = this.day + 1;
        int newMonth = this.month;
        int newYear = this.year;

        int maxDay = getDaysInMonth(this.month, this.year);

        if (newDay > maxDay) {
            newDay = 1;
            newMonth++;
            if (newMonth > 12) {
                newMonth = 1;
                newYear++;
            }
        }

        return new Date(newDay, newMonth, newYear);
    }

    /**
     * Returns a new Date instance representing the previous day.
     *
     * @return Date of the previous day
     */
    public Date previousDate() {
        int newDay = this.day - 1;
        int newMonth = this.month;
        int newYear = this.year;

        if (newDay < 1) {
            newMonth--;
            if (newMonth < 1) {
                newMonth = 12;
                newYear--;
                if (newYear < 1) {
                    throw new IllegalArgumentException("Year out of valid range.");
                }
            }
            newDay = getDaysInMonth(newMonth, newYear);
        }

        return new Date(newDay, newMonth, newYear);
    }

    /**
     * Compares this date with another date.
     *
     * @param other The other Date to compare to
     * @return Positive integer if this date is after the other date,
     * negative integer if before, or zero if equal
     * @throws NullPointerException if other is null
     */
    @Override
    public int compareTo(Date other) {
        if (other == null) {
            throw new NullPointerException("The compared Date is null.");
        }

        if (this.year != other.year) {
            return this.year - other.year;
        }

        if (this.month != other.month) {
            return this.month - other.month;
        }

        return this.day - other.day;
    }

    /**
     * Returns a string representation of the date in the format DD/MM/YYYY.
     *
     * @return String representation of the date
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", this.day, this.month, this.year);
    }

    // Optionally, you can override equals and hashCode methods for better comparison and hashing behavior.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Date other)) return false;
        return this.day == other.day &&
                this.month == other.month &&
                this.year == other.year;
    }

    @Override
    public int hashCode() {
        // Simple hash code implementation
        int result = 17;
        result = 31 * result + day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}