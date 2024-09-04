package com.github.dewarepk;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.FirestoreCallback;
import com.github.dewarepk.model.FirestoreHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        FirestoreHandler handler = new FirestoreHandler();

        // Do not run me. It just template.

        handler.checkIfSpecificExist("email", "value").thenAccept(result -> {
            if (result) {
                Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword("FILL ME" , "FILL ME")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    String uid = user.getUid();
                                    handler.establishUser(uid, "fullname", "username", "email", new FirestoreCallback() {
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
                                }
                            }
                        });
            }
        });

    }
}