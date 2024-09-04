package com.github.dewarepk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {

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

            textView.setOnClickListener(view -> {
                Toast.makeText(this, "Madame", Toast.LENGTH_SHORT).show();
            });

            loginButton.setOnClickListener(view -> {
                this.signIn(eMail.getText().toString(), password.getText().toString());
            });

        } else {
            this.startActivity(new Intent(this, StoreActivity.class));
            this.finish();

        }

    }

    private void signIn(String email, String password) {

        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        boolean isLoggedIn = userPreferences.getBoolean("isLoggedIn", false);
        Intent nextIntent = new Intent(this, StoreActivity.class);

        FirestoreHandler database = new FirestoreHandler();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Data shouldn't be empty!" , Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();

                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();
                        this.startActivity(nextIntent);
                        this.finish();

                    } else {

                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}