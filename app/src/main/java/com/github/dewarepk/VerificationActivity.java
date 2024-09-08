package com.github.dewarepk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationActivity extends AppCompatActivity {

    private boolean isCooldown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification);
        FirestoreHandler database = new FirestoreHandler();
        String userId;

        try {
            SecureAccess secureAccess = new SecureAccess(this.getApplicationContext(), "UserPreferences");
            userId = secureAccess.getValue("userId", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Button okayButton = this.findViewById(R.id.okayBtn);
        TextView resendLink = this.findViewById(R.id.resend_Link);
        ImageView imageViewReturn = this.findViewById(R.id.return_back);

        database.getSpecificData(userId, "email")
                .thenAccept(fieldValue -> {
                    TextView displayEmail = this.findViewById(R.id.displayEmail);
                    displayEmail.setText(fieldValue.toString());
                }).exceptionally(ex -> {
                    Log.e("VerificationActivity", "Error getting specific data", ex);
                    return null;
                });


        okayButton.setOnClickListener(view -> {
            this.startActivity(new Intent(VerificationActivity.this, StoreActivity.class));
            this.finish();
        });

        resendLink.setOnClickListener(view -> {
            this.resendEmailVerification(resendLink);
        });

        imageViewReturn.setOnClickListener(aVoid -> {
           this.startActivity(new Intent(VerificationActivity.this , LoginActivity.class));
           this.finish();
        });

    }


    private void resendEmailVerification(TextView resendLink) {

        if (isCooldown) {
            Toast.makeText(this, "Please wait before resending the email.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null && !user.isEmailVerified()) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                            this.startCooldown(resendLink, 60000);
                        } else {
                            Toast.makeText(this, "Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Email is already verified or user is not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startCooldown(TextView resendTextView, long cooldownTime) {
        isCooldown = true;
        resendTextView.setEnabled(false);
        resendTextView.setTextColor(resendTextView.getResources().getColor(R.color.light_red));

        new CountDownTimer(cooldownTime , 1000) {
            @Override
            public void onTick(long l) {
                long seconds = l / 1000;
                resendTextView.setText(seconds + "");
            }

            @Override
            public void onFinish() {
                isCooldown = false;
                resendTextView.setEnabled(true);
                resendTextView.setTextColor(resendTextView.getResources().getColor(R.color.deepblue));
                resendTextView.setText("Resend");
            }
        }.start();
    }

}