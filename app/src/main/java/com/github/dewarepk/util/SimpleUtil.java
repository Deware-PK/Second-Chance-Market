package com.github.dewarepk.util;

import android.content.Context;
import android.util.Log;

import com.github.dewarepk.model.Address;
import com.github.dewarepk.model.AddressHandler;
import com.github.dewarepk.model.FirestoreCallback;
import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.model.SimpleCallback;
import com.github.dewarepk.model.WalletHandler;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * The class that simplify some common tasks.
 * It may increase your quality of life.
 */
public final class SimpleUtil {

    /** Singleton instance of this class. */
    private static final FirestoreHandler database = new FirestoreHandler();

    /**
     * Get user's id from local storage.
     *
     * @return null if user id is not found
     */
    public static String getCurrentUserId(Context context) {

        try {
            SecureAccess localStorage = new SecureAccess(context, "UserPreferences");
            return localStorage.getValue("userId", String.class);
        } catch (Exception ex) {
            Log.d("SimpleUtil", "Error getting user id");
        }

        return null;
    }

    /**
     * Get user's full name from database split by whitespace.
     */
    public static void getUserFullName(Context context, SimpleCallback<String> callback) {
        final String[] firstName = new String[1];
        final String[] lastName = new String[1];

        database.getUser(getCurrentUserId(context), new FirestoreCallback() {
            @Override
            public void onSuccess() {
                // Do nothing
            }

            @Override
            public void onFailure(Exception ex) {
                Log.d("SimpleUtil" , "Error getting user full name", ex);
            }

            @Override
            public void onDataReceived(Map<String, Object> data) {
                firstName[0] = (String) data.get("firstName");
                lastName[0] = (String) data.get("lastName");

                callback.onDataReceived(firstName[0] + " " + lastName[0]);
            }
        });

    }

    /**
     * Check if user's email is verified.
     *
     * @return true if email is verified, false otherwise
     */
    public static boolean isEmailVerified() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null)
            return false;

        return auth.getCurrentUser().isEmailVerified();
    }

    /**
     * Get current user's wallet address.
     *
     * @param context
     * @param callback
     * @return wallet address
     */
    public static void getCurrentUserWallet(Context context, SimpleCallback<String> callback) {

        database.getSpecificData(getCurrentUserId(context) , "wallet_address")
                .thenAccept(value -> callback.onDataReceived((String) value));

    }

    /**
     * Get current user's balance.
     *
     * @param context
     * @param callback
     */
    public static void getCurrentUserBalance(Context context, SimpleCallback<Double> callback) {
        WalletHandler walletHandler = new WalletHandler();
        getCurrentUserWallet(context, result ->
                walletHandler.getBalance(result).thenAccept(callback::onDataReceived));

    }

    /**
     * Get current user's address key.
     *
     * @param context
     * @param callback
     */
    public static void getCurrentUserAddressKey(Context context, SimpleCallback<String> callback) {

        database.getSpecificData(getCurrentUserId(context) , "address").thenAccept(value ->
                callback.onDataReceived((String) value));

    }

    /**
     * Get current user's address.
     *
     * @param context
     * @param callback
     * @return current user's address
     */
    public static void getCurrentUserAddress(Context context, SimpleCallback<Address> callback) {
        AddressHandler addressHandler = new AddressHandler();

        getCurrentUserAddressKey(context, key ->
                addressHandler.getAddress(key).thenAccept(callback::onDataReceived));
    }
}
