package com.github.dewarepk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

/** The class represent the store activity. */
public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.store);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();

        Button logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(view -> {
            this.startActivity(new Intent(StoreActivity.this , LoginActivity.class));
            this.finish();
            editor.remove("isLoggedIn");
            editor.apply();
            auth.signOut();
        });

    }
}