package com.github.dewarepk.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FirestoreHandler {

    /** Firebase database instance **/
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();

    /** Establish a new user **/
    public void establishUser(String userId, String fullName, String username, String email, FirestoreCallback callback) {
        WalletHandler walletHandler = new WalletHandler();

        final Map<String, Object> user = new HashMap<>();
        user.put("fullName", fullName);
        user.put("email", email);
        user.put("username", username);
        user.put("createdAt", FieldValue.serverTimestamp());

        String address = walletHandler.createWallet(0.0, new FirestoreCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception ex) {

            }

            @Override
            public void onDataReceived(Map<String, Object> data) {

            }
        });

        user.put("wallet_address", address);

        database.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    /** Delete a user **/
    public void deleteUser(String userId, FirestoreCallback callback) {
        database.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(result -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(callback::onFailure);
    }


    /** Get a specific user by userId **/
    public void getUser(String userId, FirestoreCallback callback) {
        database.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        callback.onDataReceived(documentSnapshot.getData());
                    else
                        callback.onFailure(new Exception("User not found!"));

                })
                .addOnFailureListener(callback::onFailure);
    }

    /**
     * get all users
     *
     * @param callback
     */
    public void getUsers(FirestoreCallback callback) {
        CollectionReference userRef = database.collection("users");

        userRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        querySnapshot.forEach(document -> callback.onDataReceived(document.getData()));
                    } else
                        callback.onFailure(task.getException());

                });
    }

    /**
     * Get data value from specific field
     *
     * @param userId
     * @param field
     * @return
     */
    public CompletableFuture<Object> getSpecificData(String userId, String field) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        DocumentReference docRef = database.collection("users").document(userId);

        docRef.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               DocumentSnapshot documentSnapshot = task.getResult();
               if (documentSnapshot != null && documentSnapshot.exists()) {
                   Object fieldValue = documentSnapshot.get(field);
                   future.complete(fieldValue);
               } else {
                   future.completeExceptionally(new Exception("Document does not exist!"));
               }
           } else {
               future.completeExceptionally(task.getException());
           }
        });

        return future;
    }

    /**
     * A method to update data in the database
     *
     * @param collectionName
     * @param documentId
     * @param updates
     * @return
     */
    public CompletableFuture<Void> updateData(String collectionName, String documentId, Map<String, Object> updates) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DocumentReference docRef = database.collection(collectionName).document(documentId);

        docRef.update(updates)
                .addOnSuccessListener(nil -> future.complete(null))
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }

    /**
     * check if a specific field exist
     *
     * @param field
     * @param value
     * @return
     */
    public CompletableFuture<Boolean> checkIfSpecificExist(String field, String value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        database.collection("users")
                .whereEqualTo(field, value)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty())
                            future.complete(true);
                        else
                            future.complete(false);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
