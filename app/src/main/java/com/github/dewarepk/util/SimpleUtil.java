package com.github.dewarepk.util;

import android.content.Context;
import android.util.Log;

import com.github.dewarepk.model.Address;
import com.github.dewarepk.model.AddressHandler;
import com.github.dewarepk.model.FirestoreCallback;
import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
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
    public static String getUserFullName(Context context) {
        final String[] firstName = new String[1];
        final String[] lastName = new String[1];

        database.getUser(getCurrentUserId(context), new FirestoreCallback() {
            @Override
            public void onSuccess() {
                // Do nothing
            }

            @Override
            public void onFailure(Exception ex) {
                // Do nothing
            }

            @Override
            public void onDataReceived(Map<String, Object> data) {
                firstName[0] = (String) data.get("firstName");
                lastName[0] = (String) data.get("lastName");
            }
        });

        return firstName[0] + " " + lastName[0];
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
     * @return wallet address
     */
    public static String getCurrentUserWallet(Context context) {
        String[] walletAddress = new String[1];

        database.getSpecificData(getCurrentUserId(context) , "wallet_address")
                .thenAccept(value -> walletAddress[0] = (String) value);

        return walletAddress[0];
    }

    /**
     * Get current user's balance.
     *
     * @param context
     * @return current user's balance
     */
    public static Double getCurrentUserBalance(Context context) {
        WalletHandler walletHandler = new WalletHandler();
        String walletAddress = getCurrentUserWallet(context);
        double[] balance = new double[1];

        walletHandler.getBalance(walletAddress).thenAccept(amount -> balance[0] = amount);

        return balance[0];
    }

    /**
     * Get current user's address key.
     *
     * @param context
     * @return current user's address
     */
    public static String getCurrentUserAddressKey(Context context) {
        String[] addressKey = new String[1];

        database.getSpecificData(getCurrentUserId(context), "address").thenAccept(value -> addressKey[0] = (String) value);

        return addressKey[0];
    }

    /**
     * Get current user's address.
     *
     * @param context
     * @return current user's address
     */
    public static Address getCurrentUserAddress(Context context) {
        AddressHandler addressHandler = new AddressHandler();
        Address[] address = new Address[1];
        addressHandler.getAddress(getCurrentUserAddressKey(context)).thenAccept(data -> {
            address[0] = data;
        });

        return address[0];
    }
}
