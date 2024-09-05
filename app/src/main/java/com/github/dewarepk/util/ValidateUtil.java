package com.github.dewarepk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.github.dewarepk.model.FirestoreHandler;

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
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return userPreferences.getBoolean("isLoggedIn", false);
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

        if (fullname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Log.d("Validate", "Debug 1");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d("Validate", "Debug 2");
            return false;
        }

        if (!password.equals(passwordConfirm)) {
            Log.d("Validate", "Debug 3");
            return false;
        }

        return true;
    }

}
