package com.github.dewarepk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.activity.EdgeToEdge;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.model.WalletHandler;
import com.github.dewarepk.model.WalletMode;
import com.github.dewarepk.util.TimeUtil;
import com.github.dewarepk.util.ValidateUtil;

import java.util.function.Predicate;

public class TopUpActivity extends AppCompatActivity {

    private EditText totalTopup;
    private boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        String userId;

        try {
            SecureAccess localStorage = new SecureAccess(this.getApplicationContext(), "UserPreferences");
            userId = localStorage.getValue("userId", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        ImageView returnBackTopup = findViewById(R.id.return_back_topup);

        AppCompatButton buttonOneHundred = findViewById(R.id.button_100);
        AppCompatButton buttonTwoHundred = findViewById(R.id.button_200);
        AppCompatButton buttonThreeHundred = findViewById(R.id.button_300);
        AppCompatButton buttonFiveHundred = findViewById(R.id.button_500);
        AppCompatButton buttonOneThousand = findViewById(R.id.button_1000);
        AppCompatButton buttonTwoThousand = findViewById(R.id.button_2000);
        totalTopup = findViewById(R.id.Totaltopup);
        AppCompatButton confirmButtonTopup = findViewById(R.id.confirm_button_topup);

        // Set button listeners using helper method
        setButtonListener(buttonOneHundred, "100");
        setButtonListener(buttonTwoHundred, "200");
        setButtonListener(buttonThreeHundred, "300");
        setButtonListener(buttonFiveHundred, "500");
        setButtonListener(buttonOneThousand, "1000");
        setButtonListener(buttonTwoThousand, "2000");

        // Handle confirmation button click
        confirmButtonTopup.setOnClickListener(aVoid -> {
            handleTopUpConfirmation(userId);
            TimeUtil.loadDataDialog(this, isFinish, 1000);
            TimeUtil.delayExecution(1500, () -> {
                isFinish = true;
                Toast.makeText(TopUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TopUpActivity.this, ProfileActivity.class));
                finish();
            });
        });



    }

    private void setButtonListener(AppCompatButton button, String amount) {
        button.setOnClickListener(v -> totalTopup.setText(amount));
    }



    private void handleTopUpConfirmation(String userId) {
        try {
            // Handle the confirmation action
            double total = Double.parseDouble(totalTopup.getText().toString());
            WalletHandler walletHandler = new WalletHandler();
            walletHandler.updateBalance(userId, total, WalletMode.DEPOSIT);
        } catch (NumberFormatException e) {
            // Handle invalid input (not a valid number)
            totalTopup.setError("Please enter a valid amount");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimeUtil.stopTask();
    }
}