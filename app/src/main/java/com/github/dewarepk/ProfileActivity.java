package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.TimeUtil;
import com.github.dewarepk.util.ValidateUtil;
import com.marsad.stylishdialogs.StylishAlertDialog;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProfileActivity extends AppCompatActivity {

    private TextView balance;
    private StylishAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ValidateUtil.checkIntegrity(this.getApplicationContext() , this);

        AppCompatButton topupButton = this.findViewById(R.id.buttonTop_up);
        AppCompatButton withdrawButton = this.findViewById(R.id.buttonWithdraw);

        this.balance = this.findViewById(R.id.textmoney);

        dialog = TimeUtil.loadDataDialog(this, balance.getText().toString().equals("฿ -1"), 1000);

        SimpleUtil.getCurrentUserBalance(this.getApplicationContext(), amount -> {
            this.balance.setText("฿ " + amount);
        });

        topupButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this , TopUpActivity.class));
            this.finish();
        });

        withdrawButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this , WithdrawActivity.class));
            this.finish();
        });

        ImageView refresh = this.findViewById(R.id.refresh);

        refresh.setOnClickListener(aVoid -> {

            this.balance.setText("฿ -1");

            SimpleUtil.getCurrentUserBalance(this.getApplicationContext(), amount -> {
                this.balance.setText("฿ " + amount);

            });

            dialog = TimeUtil.loadDataDialog(this, this.balance.getText().toString().equals("฿ -1"), 1000);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
