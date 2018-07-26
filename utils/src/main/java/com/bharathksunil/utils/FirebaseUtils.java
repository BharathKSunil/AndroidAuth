package com.bharathksunil.utils;

/**
 * Use this utility to perform some generic operations related to firebase
 *
 * @author Bharath
 * @since 10-01-2018.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class FirebaseUtils {

    private static final String DOT_REPLACEMENT = "_dot_";

    private FirebaseUtils() {
    }

    /**
     * Call this function to convert the email as a key in the Real-time database. It stores the
     * email by replacing the '.'(dot) with the {@link #DOT_REPLACEMENT} token instead.
     *
     * @param email the email address
     * @return the key safe of the email address
     */
    public static String getEmailAsFirebaseKey(String email) {
        email = email.toLowerCase();
        return email.replaceAll("\\.", DOT_REPLACEMENT);
    }

    /**
     * Call this function to convert the firebase key in the form of an email back to a regular email
     * It removes the {@link #DOT_REPLACEMENT} from the key and replaces it with '.'(dot). This
     * method reverses the action in the {@link #getEmailAsFirebaseKey(String)}.
     *
     * @param key the email which was stored as a key
     * @return the regular email ID
     */
    public static String getEmailFromFirebaseKey(String key) {
        return key.replaceAll(DOT_REPLACEMENT, "\\.").toLowerCase();
    }

}