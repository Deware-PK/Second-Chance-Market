package com.github.dewarepk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.github.dewarepk.model.FirestoreHandler;
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
     * @param fullname
     * @param email
     * @param username
     * @param password
     * @param passwordConfirm
     * @return
     */
    public static boolean validateInput(String fullname, String email, String username, String password, String passwordConfirm) {

        if (fullname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
            return false;


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;


        if (!password.equals(passwordConfirm))
            return false;


        return true;
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
