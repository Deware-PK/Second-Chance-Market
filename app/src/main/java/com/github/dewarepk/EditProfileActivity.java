package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.InvalidAddressCause;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText emailText = this.findViewById(R.id.email);
        EditText nameText = this.findViewById(R.id.name);
        AppCompatButton changePassword = this.findViewById(R.id.change_password_button);
        AppCompatButton saveProfile = this.findViewById(R.id.save_profile_button);
        FirestoreHandler handler = new FirestoreHandler();

        TextView verifiedText = this.findViewById(R.id.verified);

        if (auth.getCurrentUser() == null) {
            try {
                SecureAccess secureAccess = new SecureAccess(this, "UserPreferences");
                secureAccess.removeValue("userId");
                secureAccess.removeValue("isLoggedIn");
                FirebaseAuth.getInstance().signOut();

                this.startActivity(new Intent(EditProfileActivity.this , LoginActivity.class));
                this.finish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return;
        }


        emailText.setText(auth.getCurrentUser().getEmail());
        boolean isVerified = FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();

        if (!isVerified) {
            verifiedText.setText("Not Verified");
            verifiedText.setTextColor(this.getResources().getColor(R.color.light_red));
        }

        SimpleUtil.getUsername(this.getApplicationContext(), nameText::setText);




        saveProfile.setOnClickListener(l -> {
            String eMail = emailText.getText().toString();
            String username = nameText.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(EditProfileActivity.this.getApplicationContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            final Map<String, Object> map = new HashMap<>();
            map.put("username", username);

            if (!eMail.equals(auth.getCurrentUser().getEmail())) {
                if (!auth.getCurrentUser().isEmailVerified()) {
                    Toast.makeText(EditProfileActivity.this.getApplicationContext(), "Please verify your email first.", Toast.LENGTH_SHORT).show();
                    return;
                }
                map.put("email", eMail);
                auth.getCurrentUser().verifyBeforeUpdateEmail(eMail);
            }

            handler.updateData("users", SimpleUtil.getCurrentUserId(this.getApplicationContext()), map);
            Toast.makeText(EditProfileActivity.this.getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();

            this.startActivity(new Intent(EditProfileActivity.this , ProfileActivity.class));
            this.finish();
        });

        changePassword.setOnClickListener(l -> {
            auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail());
            Toast.makeText(this.getApplicationContext(), "Reset link has been sent!", Toast.LENGTH_SHORT).show();
            this.startActivity(new Intent(EditProfileActivity.this , ProfileActivity.class));
            this.finish();
        });

    }
}
