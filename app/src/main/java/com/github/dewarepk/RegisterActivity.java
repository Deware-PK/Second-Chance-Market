package com.github.dewarepk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.FirestoreCallback;
import com.github.dewarepk.model.FirestoreHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

/**
 * The class represent the register activity.
 */
public class RegisterActivity extends AppCompatActivity {

    /** Firebase authentication instance **/
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    /** UI elements **/
    private EditText emailReg;
    private EditText passwordReg;
    private EditText fullNameReg;
    private EditText usernameReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        FirestoreHandler handler = new FirestoreHandler();

        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();

        emailReg = this.findViewById(R.id.emailReg);
        passwordReg = this.findViewById(R.id.passwordReg);
        fullNameReg = this.findViewById(R.id.fullNameReg);
        usernameReg = this.findViewById(R.id.usernameReg);

        String email = emailReg.getText().toString();
        String password = passwordReg.getText().toString();
        String fullname = fullNameReg.getText().toString();
        String username = usernameReg.getText().toString();


        handler.checkIfSpecificExist("email", email).thenAccept(emailResult -> {
            if (emailResult) {
                Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
            } else {
                handler.checkIfSpecificExist("username", username).thenAccept(usernameResult -> {
                    if (usernameResult) {
                        Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    } else {

                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        if (user != null) {
                                            String uid = user.getUid();
                                            handler.establishUser(uid, fullname, username, email, new FirestoreCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    editor.putString("userId", user.getUid());
                                                    editor.apply();
                                                }

                                                @Override
                                                public void onFailure(Exception ex) {
                                                    Toast.makeText(RegisterActivity.this, "Register failed! Please try again later.", Toast.LENGTH_SHORT).show();
                                                    RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    RegisterActivity.this.finish();
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
        });

    }
}