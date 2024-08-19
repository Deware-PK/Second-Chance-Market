package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            if(username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, StoreActivity.class);
                this.startActivity(intent);
                this.finish();
            }else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}