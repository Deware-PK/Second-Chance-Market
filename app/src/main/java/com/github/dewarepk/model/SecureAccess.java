package com.github.dewarepk.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


/**
 * A class which enchanted program security such as accessing local file or touching sensitive data.
 */
public class SecureAccess {

    private final SharedPreferences encryptedPrefs;

    public SecureAccess(Context context, String prefsName) throws Exception {
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        encryptedPrefs = EncryptedSharedPreferences.create(
                prefsName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public <T> T getValue(String key, Class<T> clazz) {
        if (clazz == String.class) {
            return clazz.cast(encryptedPrefs.getString(key, null));
        } else if (clazz == Integer.class) {
            return clazz.cast(encryptedPrefs.getInt(key, 0));
        } else if (clazz == Boolean.class) {
            return clazz.cast(encryptedPrefs.getBoolean(key, false));
        } else if (clazz == Float.class) {
            return clazz.cast(encryptedPrefs.getFloat(key, 0f));
        } else if (clazz == Long.class) {
            return clazz.cast(encryptedPrefs.getLong(key, 0L));
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public void putValue(String key, Object value) {
        SharedPreferences.Editor editor = encryptedPrefs.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }

        editor.apply();
    }

    public void removeValue(String key) {
        SharedPreferences.Editor editor = encryptedPrefs.edit();
        editor.remove(key);
        editor.apply();
    }
}
