package com.github.dewarepk.model;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WalletHandler {

    /**
     * Firebase connection instance
     */
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();

    /**
     * Create a new wallet
     *
     * @param balance
     * @param callback
     * @return wallet address
     */
    public String createWallet(double balance, FirestoreCallback callback) {
        final Map<String, Object> user = new HashMap<>();
        user.put("balance", balance);
        user.put("lastUpdated", FieldValue.serverTimestamp());

        String address = RandomKeyGenerator.generateKey(16);

        database.collection("wallets").document(address)
                .set(user)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);

        return address;
    }

    /**
     * Get user's wallet balance
     *
     * @param address
     * @return
     */
    public CompletableFuture<Double> getBalance(String address) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        DocumentReference walletRef = database.collection("wallets").document(address);

        walletRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Double balance = task.getResult().getDouble("balance");
                future.complete(balance != null ? balance : 0.0);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }

    public void updateBalance(String userId, double balance, WalletMode mode) {
        FirestoreHandler firestoreHandler = new FirestoreHandler();
        WalletHandler walletHandler = new WalletHandler();
        double[] newBalance = new double[1];
        final Map<String, Object> map = new HashMap<>();

        firestoreHandler.getSpecificData(userId , "wallet_address").thenAccept(walletAddress -> {

            walletHandler.getBalance((String) walletAddress).thenAccept(amount -> {
                switch (mode) {
                    case DEPOSIT:
                        newBalance[0] = amount + balance;
                        map.put("balance", newBalance[0]);
                        firestoreHandler.updateData("wallets", (String) walletAddress, map);
                        map.clear();
                        break;
                    case WITHDRAW:

                        if (amount < balance)
                            return;

                        newBalance[0] = amount - balance;
                        map.put("balance", newBalance[0]);
                        firestoreHandler.updateData("wallets", (String) walletAddress, map);
                        map.clear();
                        break;
                }

            });
        });



    }
}
