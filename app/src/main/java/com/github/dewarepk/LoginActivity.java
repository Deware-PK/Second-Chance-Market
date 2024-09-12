package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * The class represent the login activity.
 */
public class LoginActivity extends AppCompatActivity {

    /** UI elements **/
    private EditText eMail;
    private EditText password;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    
    // Login UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        if (!ValidateUtil.isLogin(this.getApplicationContext())) {
            eMail = this.findViewById(R.id.emailBtn);
            password = this.findViewById(R.id.passwordBtn);
            Button loginButton = this.findViewById(R.id.signInBtn);
            TextView textView = this.findViewById(R.id.signUpBtn);

            TextView forgotPassword = this.findViewById(R.id.forgotPasswordText);

            forgotPassword.setOnClickListener(aVoid -> {
                this.startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                this.finish();
            });

            textView.setOnClickListener(view -> {
                this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                this.finish();
            });

            loginButton.setOnClickListener(view -> {
                try {
                    this.signIn(eMail.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } else {

            this.startActivity(new Intent(this, TopUpActivity.class));
            this.finish();

        }

    }

    private void signIn(String email, String password) throws Exception {
        SecureAccess secureAccess = new SecureAccess(this, "UserPreferences");

        Intent nextIntent = new Intent(this, TopUpActivity.class);

        // Prevent empty input because it will crash the app.
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Data shouldn't be empty!" , Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();

                        // Check if user is null
                        if (user == null) {
                            Toast.makeText(this, "Cannot connect to server!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();

                        secureAccess.putValue("isLoggedIn", true);
                        secureAccess.putValue("userId", user.getUid());

                        this.startActivity(nextIntent);
                        this.finish();

                    } else {

                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}