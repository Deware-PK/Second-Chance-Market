package com.github.dewarepk.model;

import java.util.Map;

public interface FirestoreCallback {

    void onSuccess();

    void onFailure(Exception ex);

    void onDataReceived(Map<String, Object> data);
}
