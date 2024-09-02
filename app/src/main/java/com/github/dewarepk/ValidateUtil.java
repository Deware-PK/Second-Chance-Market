package com.github.dewarepk;

import android.content.Context;
import android.content.SharedPreferences;

public final class ValidateUtil {

    public static boolean isLogin(Context context) {
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return userPreferences.getBoolean("isLoggedIn", false);
    }


}
