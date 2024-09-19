package com.github.dewarepk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.ValidateUtil;
import com.marsad.stylishdialogs.StylishAlertDialog;

public class ProfileActivity extends AppCompatActivity {

    private TextView balance;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ValidateUtil.checkIntegrity(this.getApplicationContext() , this);

        this.loadDialog();

        AppCompatButton topupButton = this.findViewById(R.id.buttonTop_up);
        AppCompatButton withdrawButton = this.findViewById(R.id.buttonWithdraw);

        this.balance = this.findViewById(R.id.textmoney);
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
    }

    private void loadDialog() {
        StylishAlertDialog progessionDialog = new StylishAlertDialog(this, StylishAlertDialog.PROGRESS);
        progessionDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progessionDialog.setTitleText("Loading")
                .setCancellable(false)
                .setCancelledOnTouchOutside(false)
                .show();

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {

                if (balance.getText().toString().equals("฿ -1")) {
                    Log.d("Dialog", "Loading!");
                    // เรียก Runnable อีกครั้งหลังจาก 1 วินาที
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(runnable);
                    progessionDialog.dismissWithAnimation();
                    Log.d("Dialog", "Finish Loaded!");
                }
            }
        };

        // เรียก Runnable ครั้งแรก
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
