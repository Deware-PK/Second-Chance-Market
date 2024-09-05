package com.github.dewarepk.model;

import com.google.firebase.firestore.CollectionReference;
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
    public void establishUser(String userId, String fullname, String username, String email, FirestoreCallback callback) {
        final Map<String, Object> user = new HashMap<>();
        user.put("fullname", fullname);
        user.put("username", username);
        user.put("email", email);

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
