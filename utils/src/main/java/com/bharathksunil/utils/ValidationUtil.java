package com.bharathksunil.utils;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class ValidationUtil {

    private ValidationUtil() {

    }

    //region Constants
    /**
     * a digit must occur at least once
     */
    public static final String MUST_HAVE_DIGIT = "(?=.*[0-9])";
    /**
     * a lower case letter must occur at least once
     */
    public static final String MUST_HAVE_LOWER_CASE_ALPHA = "(?=.*[0-9])";
    /**
     * an upper case letter must occur at least once
     */
    public static final String MUST_HAVE_UPPER_CASE_ALPHA = "(?=.*[a-z])";
    /**
     * a special character must occur at least once
     */
    public static final String MUST_HAVE_SPECIAL_CHAR = "(?=.*[@#$%^&+=])";
    /**
     * no whitespace allowed in the entire string
     */
    public static final String MUST_NOT_HAVE_WHITE_SPACES = "(?=\\S+$)";
    /**
     * anything, at least eight places though
     */
    public static final String MUST_HAVE_MINIMAL_8_CHAR = ".{8,}";
    //endregion

    //region Regular Expressions & their Setters
    /**
     * Regular expression for Password Strength validation , default modules:<br/>
     * # a digit must occur at least once<br/>
     * # a lower case letter must occur at least once<br/>
     * # no whitespace allowed in the entire string<br/>
     * # anything, at least eight places though<br/>
     * Set this for your app by calling the {@link #setPasswordStrength(String)} function in your
     * Application Class.
     */
    private static Pattern validPasswordRegex = Pattern.compile(
            "^"
                    + MUST_HAVE_DIGIT
                    + MUST_HAVE_LOWER_CASE_ALPHA
                    + MUST_NOT_HAVE_WHITE_SPACES
                    + MUST_HAVE_MINIMAL_8_CHAR
                    + "$"
    );

    /**
     * Phone number Validation Regular Expression to check if
     * ^                # start-of-string
     * [6789]           # contains 6 or 7 or 8 or 9
     * \\d{9}           # contains other 9 digits
     * $                # end-of-string
     */
    private static Pattern validPhoneNumberRegex = Pattern.compile("^[6789]\\d{9}$");

    /**
     * Email Id Validation Regular Expression to validate an email ID
     */
    private static Pattern validEmailAddressRegex = Pattern.compile(
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Call this method to set the password strength that is required
     *
     * @param regex the regular Expression
     */
    public static void setPasswordStrength(@NonNull final String regex) {
        validPasswordRegex = Pattern.compile(regex);
    }

    /**
     * Call this method to set the password strength that is required
     *
     * @param pattern the pattern for the strength of password
     */
    public static void setPasswordStrength(@NonNull final Pattern pattern) {
        validPasswordRegex = pattern;
    }

    /**
     * Call this method to set the phone number validation regular expression
     *
     * @param regex the regular expression
     */
    public static void setValidPhoneNumberRegex(@NonNull final String regex) {
        validPhoneNumberRegex = Pattern.compile(regex);
    }

    /**
     * Call this method to set the phone number validation pattern
     *
     * @param pattern the pattern for phone number validation
     */
    public static void setValidPhoneNumberRegex(@NonNull final Pattern pattern) {
        validPhoneNumberRegex = pattern;
    }

    /**
     * Call this method to set the EmailId validation regular Expression
     *
     * @param regex the regular expression
     */
    public static void setValidEmailAddressRegex(@NonNull final String regex) {
        validEmailAddressRegex = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Call this method to set the EmailId validation pattern
     *
     * @param pattern the pattern for validation of emailID
     */
    public static void setValidEmailAddressRegex(@NonNull final Pattern pattern) {
        validEmailAddressRegex = pattern;
    }
    //endregion

    /**
     * This method checks if the emailID syntax is valid
     *
     * @param email the emailId to be validated
     * @return true, if the email is syntactically valid
     */
    public static boolean isEmailValid(@NonNull final CharSequence email) {
        return validEmailAddressRegex.matcher(email).find();
    }

    /**
     * It checks if the phone number is a valid mobile number; by default in India.
     * For any other, use {@link #setValidPhoneNumberRegex(String)} and pass the regex
     *
     * @param phone the mobile number to be validated
     * @return true, if the phone number is valid
     */
    public static boolean isPhoneNumberValid(@NonNull final CharSequence phone) {
        return validPhoneNumberRegex.matcher(phone).find();
    }

    /**
     * It checks if the password matches these criteria to ensure a strong password
     * C1: Contains 8 characters
     * C2: Contains minimum one digit
     * C3: Contains one Special Character
     *
     * @param password the password to be validated
     * @return true, if the password is strong and matches all criteria
     */
    public static boolean isPasswordStrong(@NonNull final CharSequence password) {
        return validPasswordRegex.matcher(password).find();
    }

    /**
     * Call this method to check if the two strings are equal
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are equal
     */
    public static boolean areEqual(@NonNull final String firstString,
                                   @NonNull final String secondString) {
        return firstString.equals(secondString);
    }

}
