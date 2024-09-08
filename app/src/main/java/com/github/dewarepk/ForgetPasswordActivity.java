package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        EditText emailInput = this.findViewById(R.id.email_input);
        Button confirmButton = this.findViewById(R.id.confirm_button);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ImageView imageViewReturn = this.findViewById(R.id.return_back);

        imageViewReturn.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(ForgetPasswordActivity.this , LoginActivity.class));
            this.finish();
        });

        confirmButton.setOnClickListener(aVoid -> {
            String email = emailInput.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.sendPasswordResetEmail(email);

            Toast.makeText(this, "Password reset link sent to your email.", Toast.LENGTH_SHORT).show();

            this.startActivity(new Intent(ForgetPasswordActivity.this , LoginActivity.class));
            this.finish();
        });
    }


}