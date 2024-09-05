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

        if (fullname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
            return false;


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;


        if (!password.equals(passwordConfirm))
            return false;


        return true;
    }

    public static boolean checkPasswordPattern(String password) {
        String passwordPattern = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }


}
