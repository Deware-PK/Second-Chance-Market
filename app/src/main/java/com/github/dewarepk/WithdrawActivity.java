package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.model.WalletHandler;
import com.github.dewarepk.model.WalletMode;
import com.github.dewarepk.util.TimeUtil;
import com.github.dewarepk.util.ValidateUtil;


public class WithdrawActivity extends AppCompatActivity {

    private boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_withdraw);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        String userId;

        try {
            SecureAccess localStorage = new SecureAccess(this.getApplicationContext(), "UserPreferences");
            userId = localStorage.getValue("userId", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        WalletHandler walletHandler = new WalletHandler();
        Spinner spinner = findViewById(R.id.bank_account_spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bank_accounts)));
        AppCompatButton confirmButton = this.findViewById(R.id.order_now_button);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();

                // Deduct the balance from the user's balance
                EditText withdrawEditText = findViewById(R.id.withdrawText);

                confirmButton.setOnClickListener(l -> {
                    double withdrawAmount = Double.parseDouble(withdrawEditText.getText().toString());


                    walletHandler.updateBalance(userId, withdrawAmount, WalletMode.WITHDRAW, isSuccessful -> {
                        if (!isSuccessful) {
                            Toast.makeText(WithdrawActivity.this, "Insufficient balance", Toast.LENGTH_SHORT).show();

                        } else {

                            TimeUtil.loadDataDialog(WithdrawActivity.this, isFinish, 1500);
                            TimeUtil.delayExecution(1500, () -> {
                                isFinish = true;
                                Toast.makeText(WithdrawActivity.this, withdrawAmount + "฿ has been transfer to " + selectedItem, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(WithdrawActivity.this, ProfileActivity.class));
                                finish();
                            });
                        }
                    });


                });
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        ImageView returnButton = this.findViewById(R.id.return_back_withdraw);
        returnButton.setOnClickListener(aVoid -> {
            startActivity(new Intent(WithdrawActivity.this, ProfileActivity.class));
            finish();
        });
    }

}
