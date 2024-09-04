package com.github.dewarepk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import com.github.dewarepk.model.FirestoreHandler;

public final class ValidateUtil {

    /** Internal database handler **/
    private static final FirestoreHandler databaseHandler = new FirestoreHandler();

    public static boolean isLogin(Context context) {
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return userPreferences.getBoolean("isLoggedIn", false);
    }

    public static boolean validateInput(String fullname, String email, String username, String password, String passwordConfirm) {

        if (fullname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
            return false;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;

        if (!password.equals(passwordConfirm))
            return false;

        return true;
    }

}
