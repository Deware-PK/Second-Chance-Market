package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.FirestoreCallback;
import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.util.ValidateUtil;
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
    private EditText firstNameReg;
    private EditText lastNameReg;
    private EditText usernameReg;
    private EditText confirmedPasswordReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirestoreHandler handler = new FirestoreHandler();


        SecureAccess secureAccess = null;
        try {
            secureAccess = new SecureAccess(this, "UserPreferences");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        firstNameReg = this.findViewById(R.id.firstNameText);
        lastNameReg = this.findViewById(R.id.lastNameText);
        emailReg = this.findViewById(R.id.emailText);
        usernameReg = this.findViewById(R.id.usernameText);
        passwordReg = this.findViewById(R.id.passwordText);
        confirmedPasswordReg = this.findViewById(R.id.confirmedPasswordText);

        Button signUpButton = this.findViewById(R.id.signUpButton);
        TextView signInText = this.findViewById(R.id.signInText);

        signInText.setOnClickListener(view -> {
            this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            this.finish();
        });

        SecureAccess finalSecureAccess = secureAccess;
        signUpButton.setOnClickListener(event -> {
            String email = emailReg.getText().toString();
            String firstName = firstNameReg.getText().toString();
            String lastName = lastNameReg.getText().toString();
            String username = usernameReg.getText().toString();
            String password = passwordReg.getText().toString();
            String confirmedPassword = confirmedPasswordReg.getText().toString();

            boolean isInputValid = ValidateUtil.validateInput(firstName, lastName, email, username, password, confirmedPassword);

            if (!isInputValid) {
                Toast.makeText(RegisterActivity.this, "Please fill the form", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ValidateUtil.checkPasswordPattern(password)) {
                Toast.makeText(RegisterActivity.this, "Password doesn't match the pattern.", Toast.LENGTH_SHORT).show();
                return;
            }


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

                                                handler.establishUser(uid, firstName, lastName, username, email, new FirestoreCallback() {
                                                    @Override
                                                    public void onSuccess() {

                                                        finalSecureAccess.putValue("userId", user.getUid());

                                                        if (!ValidateUtil.isEmailVerified()) {
                                                            user.sendEmailVerification();
                                                            Toast.makeText(RegisterActivity.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                                                        }

                                                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, VerificationActivity.class));
                                                        RegisterActivity.this.finish();
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
        });



    }
}