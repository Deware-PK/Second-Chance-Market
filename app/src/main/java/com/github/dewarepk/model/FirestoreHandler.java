package com.github.dewarepk.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FirestoreHandler {

    private final FirebaseFirestore database = FirebaseFirestore.getInstance();


    public void establishUser(String id, String fullname, String username, String email, FirestoreCallback callback) {
        final Map<String, Object> user = new HashMap<>();
        user.put("fullname", fullname);
        user.put("username", username);
        user.put("email", email);

        database.collection("users").document(id)
                .set(user)
                .addOnSuccessListener(result -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(callback::onFailure);
    }

    public void deleteUser(String id, FirestoreCallback callback) {
        database.collection("users").document(id)
                .delete()
                .addOnSuccessListener(result -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(callback::onFailure);
    }


    public void getUser(String id, FirestoreCallback callback) {
        database.collection("users").document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        callback.onDataReceived(documentSnapshot.getData());
                    else
                        callback.onFailure(new Exception("User not found!"));

                })
                .addOnFailureListener(callback::onFailure);
    }


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
