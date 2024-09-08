package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.SecureAccess;
import com.google.firebase.auth.FirebaseAuth;

/** The class represent the store activity. */
public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Button logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(view -> {
            try {
                SecureAccess secureAccess = new SecureAccess(this, "UserPreferences");
                secureAccess.removeValue("isLoggedIn");
                secureAccess.removeValue("userId");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            auth.signOut();
            this.startActivity(new Intent(StoreActivity.this , LoginActivity.class));
            this.finish();
        });

    }
}