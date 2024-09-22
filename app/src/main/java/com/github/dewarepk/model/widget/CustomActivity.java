package com.github.dewarepk.model.widget;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.HomePageActivity;
import com.github.dewarepk.MyCartActivity;
import com.github.dewarepk.ProfileActivity;
import com.github.dewarepk.R;

public abstract class CustomActivity extends AppCompatActivity {

    private int selectedTab = 1;

    public void setDefaultTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public void makeNavigationBar() {

        final LinearLayout homeBtn = this.getProperties().getHomeButton();
        final LinearLayout cartBtn = this.getProperties().getCartButton();
        final LinearLayout profileBtn = this.getProperties().getProfileButton();

        final ImageView homeImage = this.getProperties().getHomeImage();
        final ImageView cartImage = this.getProperties().getCartImage();
        final ImageView profileImage = this.getProperties().getProfileImage();

        final TextView homeTxt = this.getProperties().getHomeText();
        final TextView cartTxt = this.getProperties().getCartText();
        final TextView profileTxt = this.getProperties().getProfileText();

        homeBtn.setOnClickListener(view -> {
            if (selectedTab != 1) {
                resetTabs(cartTxt, profileTxt, homeImage, cartImage, profileImage, cartBtn, profileBtn, homeBtn);
                homeTxt.setVisibility(View.VISIBLE);
                homeImage.setImageResource(R.drawable.home_homepage);
                homeBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(homeBtn);
                selectedTab = 1;
                this.startActivity(new Intent(this, HomePageActivity.class));
                this.finish();
            }
        });

        cartBtn.setOnClickListener(view -> {
            if (selectedTab != 2) {
                resetTabs(homeTxt, profileTxt, homeImage, cartImage, profileImage, homeBtn, profileBtn, cartBtn);
                cartTxt.setVisibility(View.VISIBLE);
                cartImage.setImageResource(R.drawable.cart_homepage);
                cartBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(cartBtn);
                selectedTab = 2;
                this.startActivity(new Intent(this, MyCartActivity.class));
                this.finish();
            }
        });

        profileBtn.setOnClickListener(view -> {
            if (selectedTab != 3) {
                resetTabs(homeTxt, cartTxt, homeImage, cartImage, profileImage, homeBtn, cartBtn, profileBtn);
                profileTxt.setVisibility(View.VISIBLE);
                profileImage.setImageResource(R.drawable.profile_homepage);
                profileBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(profileBtn);
                selectedTab = 3;
                this.startActivity(new Intent(this, ProfileActivity.class));
                this.finish();
            }
        });

    }

    private void resetTabs(TextView txt1, TextView txt2, ImageView img1, ImageView img2, ImageView img3, LinearLayout btn1, LinearLayout btn2, LinearLayout btn3) {
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);

        // Reset icons based on the selectedTab
        if (selectedTab == 1) {
            img1.setImageResource(R.drawable.home_homepage); // Update Home icon when Home is selected
        } else {
            img1.setImageResource(R.drawable.home_homepage); // Default icon for Home when not selected
        }

        if (selectedTab == 2) {
            img2.setImageResource(R.drawable.cart_homepage); // Update Cart icon when Cart is selected
        } else {
            img2.setImageResource(R.drawable.cart_homepage); // Default icon for Cart when not selected
        }

        if (selectedTab == 3) {
            img3.setImageResource(R.drawable.profile_homepage); // Update Profile icon when Profile is selected
        } else {
            img3.setImageResource(R.drawable.profile_homepage); // Default icon for Profile when not selected
        }

        // Reset background color for all buttons
        btn1.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));
        btn2.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));
        btn3.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));
    }


    private void startScaleAnimation(LinearLayout btn) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        btn.startAnimation(scaleAnimation);
    }



    public abstract NavigationProperty getProperties();
}
