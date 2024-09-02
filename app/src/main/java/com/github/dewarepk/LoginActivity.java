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


public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    
    // Login UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(this, StoreActivity.class);
        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        boolean isLoggedIn = userPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            username = this.findViewById(R.id.usernameBtn);
            password = this.findViewById(R.id.passwordBtn);
            Button loginButton = this.findViewById(R.id.signInBtn);
            TextView textView = this.findViewById(R.id.signUp_Btn);

            textView.setOnClickListener(view -> {
                Toast.makeText(this, "Madame", Toast.LENGTH_SHORT).show();
            });

            loginButton.setOnClickListener(view -> {
                if(username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();

                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    this.startActivity(intent);
                    this.finish();
                }else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            this.startActivity(intent);
            this.finish();
        }

    }
}