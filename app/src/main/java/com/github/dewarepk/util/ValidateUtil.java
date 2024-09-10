package com.github.dewarepk.util;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.InvalidCause;
import com.github.dewarepk.model.SecureAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public final class ValidateUtil {

    /** Internal database handler **/
    private static final FirestoreHandler databaseHandler = new FirestoreHandler();

    /**
     * Check if user is logged in
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {

        try {
            SecureAccess secureAccess = new SecureAccess(context, "UserPreferences");
            return secureAccess.getValue("isLoggedIn", Boolean.class);

        } catch (Exception exception) {
            Log.e("ValidateUtil", "Error getting specific data", exception);
        }

        return false;
    }


    /**
     *
     * Validating user input
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param username
     * @param password
     * @param passwordConfirm
     * @return
     */
    public static InvalidCause validateInput(String firstName, String lastName, String email, String username, String password, String passwordConfirm) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
            return InvalidCause.EMPTY_DATA;


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return InvalidCause.EMAIL_MISMATCH;


        if (!password.equals(passwordConfirm))
            return InvalidCause.PASSWORD_UNMATCHED;


        return InvalidCause.NONE;
    }

    /**
     * Check whether email verified or not
     *
     * @return
     */
    public static boolean isEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null && user.isEmailVerified();
    }

    /**
     * Check if password match the pattern
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordPattern(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }
}
