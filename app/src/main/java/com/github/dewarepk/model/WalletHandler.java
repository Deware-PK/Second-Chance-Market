package com.github.dewarepk.model;

import com.google.firebase.firestore.DocumentReference;
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
     * Get a user's wallet balance
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
}
