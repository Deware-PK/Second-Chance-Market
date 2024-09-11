package com.github.dewarepk.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AddressHandler {

    /**
     * Firebase connection instance
     */
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();


    public String addAddress(Address address, FirestoreCallback callback) {
        final Map<String, Object> user = new HashMap<>();
        user.put("address", address.getAddress());
        user.put("apartment", address.getApartment());
        user.put("sub-district", address.getSubDistrict());
        user.put("district", address.getDistrict());
        user.put("province", address.getProvince());
        user.put("postal", address.getPostalCode());
        user.put("phone", address.getPhoneNumber());
        user.put("lastUpdated", FieldValue.serverTimestamp());

        String uid = RandomKeyGenerator.generateKey(16);

        database.collection("addresses").document(uid)
                .set(user)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);

        return uid;
    }


    public CompletableFuture<Address> getAddress(String key) {
        CompletableFuture<Address> future = new CompletableFuture<>();
        DocumentReference addressRef = database.collection("addresses").document(key);

        addressRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userAddress = task.getResult().getString("address");
                String apartment = task.getResult().getString("apartment");
                String subDistrict = task.getResult().getString("sub-district");
                String district = task.getResult().getString("district");
                String province = task.getResult().getString("province");
                String postal = task.getResult().getString("postal");
                String phone = task.getResult().getString("phone");

                Address addressData = new Address(userAddress, apartment, subDistrict, district, province, postal, phone);

                future.complete(addressData);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
