package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.model.widget.CustomActivity;
import com.github.dewarepk.model.widget.NavigationProperty;
import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.TimeUtil;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marsad.stylishdialogs.StylishAlertDialog;

import java.text.DecimalFormat;

public class ProfileActivity extends CustomActivity {

    private TextView balance;
    private StylishAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ValidateUtil.checkIntegrity(this.getApplicationContext() , this);

        this.setDefaultTab(3);

        this.makeNavigationBar();

        AppCompatButton topupButton = this.findViewById(R.id.buttonTop_up);
        AppCompatButton withdrawButton = this.findViewById(R.id.buttonWithdraw);
        AppCompatButton addressEditorButton = this.findViewById(R.id.address_button);
        AppCompatButton signOutButton = this.findViewById(R.id.sign_out_button);
        AppCompatButton profileButton = this.findViewById(R.id.my_profile_button);
        AppCompatButton myStoreButton = this.findViewById(R.id.my_store_button);
        AppCompatButton myOrderButton = this.findViewById(R.id.my_order_button);

        DecimalFormat df = new DecimalFormat("#.##");

        TextView fullNameDisplay = this.findViewById(R.id.profile_name);

        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        this.balance = this.findViewById(R.id.textmoney);

        dialog = TimeUtil.loadDataDialog(this, balance.getText().toString().equals("฿ -1"), 1000);

        SimpleUtil.getCurrentUserBalance(this.getApplicationContext(), amount -> {
            this.balance.setText("฿ " + df.format(amount));
        });

        myOrderButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this , OrderListActivity.class));
            this.finish();
        });

        topupButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this , TopUpActivity.class));
            this.finish();
        });

        withdrawButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this , WithdrawActivity.class));
            this.finish();
        });

        profileButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this ,EditProfileActivity.class));
            this.finish();
        });

        myStoreButton.setOnClickListener(l -> {
            this.startActivity(new Intent(ProfileActivity.this, MyStoreActivity.class));
        });

        ImageView refresh = this.findViewById(R.id.refresh);

        refresh.setOnClickListener(aVoid -> {

            this.balance.setText("฿ -1");

            SimpleUtil.getCurrentUserBalance(this.getApplicationContext(), amount -> {


                this.balance.setText("฿ " + df.format(amount));

            });

            dialog = TimeUtil.loadDataDialog(this, this.balance.getText().toString().equals("฿ -1"), 1000);

        });

        addressEditorButton.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(ProfileActivity.this , LocationEditorActivity.class));
            this.finish();
        });

        signOutButton.setOnClickListener(aVoid -> {
            try {
                SecureAccess secureAccess = new SecureAccess(this, "UserPreferences");
                secureAccess.removeValue("userId");
                secureAccess.removeValue("isLoggedIn");
                FirebaseAuth.getInstance().signOut();

                this.startActivity(new Intent(ProfileActivity.this , LoginActivity.class));
                this.finish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public NavigationProperty getProperties() {
        return new NavigationProperty()
                .setHomeButton(this.findViewById(R.id.homeBtn))
                .setCartButton(this.findViewById(R.id.cartBtn))
                .setProfileButton(this.findViewById(R.id.profileBtn))
                .setHomeImage(this.findViewById(R.id.homeImage))
                .setCartImage(this.findViewById(R.id.cartImage))
                .setProfileImage(this.findViewById(R.id.profileImage))
                .setHomeText(this.findViewById(R.id.homeTxt))
                .setCartText(this.findViewById(R.id.CartTxt))
                .setProfileText(this.findViewById(R.id.profileTxt));
    }
}
