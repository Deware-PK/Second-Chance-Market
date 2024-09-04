package com.github.dewarepk.model;

import java.util.Map;

/**
 * The interface class used to handle the firebase database listener.
 */
public interface FirestoreCallback {

    void onSuccess();

    void onFailure(Exception ex);

    void onDataReceived(Map<String, Object> data);
}
