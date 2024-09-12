package com.github.dewarepk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.activity.EdgeToEdge;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.model.WalletHandler;
import com.github.dewarepk.model.WalletMode;

public class TopUpActivity extends AppCompatActivity {
    private ImageView returnBackTopup;
    private AppCompatButton buttonOneHundred;
    private AppCompatButton buttonTwoHundred;
    private AppCompatButton buttonThreeHundred;
    private AppCompatButton buttonFiveHundred;
    private AppCompatButton buttonOneThousand;
    private AppCompatButton buttonTwoThousand;
    private EditText totalTopup;
    private AppCompatButton confirmButtonTopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up);

        String userId;

        try {
            SecureAccess localStorage = new SecureAccess(this.getApplicationContext(), "UserPreferences");
            userId = localStorage.getValue("userId", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Initializing views
        returnBackTopup = findViewById(R.id.return_back_topup);
        buttonOneHundred = findViewById(R.id.button_one_hundred);
        buttonTwoHundred = findViewById(R.id.button_two_hundred);
        buttonThreeHundred = findViewById(R.id.button_three_hundred);
        buttonFiveHundred = findViewById(R.id.button_five_hundred);
        buttonOneThousand = findViewById(R.id.button_one_thousand);
        buttonTwoThousand = findViewById(R.id.button_2000);
        totalTopup = findViewById(R.id.Totaltopup);
        confirmButtonTopup = findViewById(R.id.confirm_button_topup);

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
}