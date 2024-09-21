package com.github.dewarepk.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.R;

public class NavigationUtil {

    private static int selectedTab = 1;

    public static void makeNavigationBar(AppCompatActivity activity) {

        final LinearLayout homeBtn = activity.findViewById(R.id.homeBtn);
        final LinearLayout cartBtn = activity.findViewById(R.id.cartBtn);
        final LinearLayout profileBtn = activity.findViewById(R.id.profileBtn);

        final ImageView homeImage = activity.findViewById(R.id.homeImage);
        final ImageView cartImage = activity.findViewById(R.id.cartImage);
        final ImageView profileImage = activity.findViewById(R.id.profileImage);

        final TextView homeTxt = activity.findViewById(R.id.homeTxt);
        final TextView cartTxt = activity.findViewById(R.id.CartTxt);
        final TextView profileTxt = activity.findViewById(R.id.profileTxt);

        homeBtn.setOnClickListener(view -> {
            if (selectedTab != 1) {
                resetTabs(activity, cartTxt, profileTxt, cartImage, profileImage, cartBtn, profileBtn);
                homeTxt.setVisibility(View.VISIBLE);
                homeImage.setImageResource(R.drawable.home_homepage);
                homeBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(homeBtn);
                selectedTab = 1;
            }
        });

        cartBtn.setOnClickListener(view -> {
            if (selectedTab != 2) {
                resetTabs(activity, homeTxt, profileTxt, homeImage, profileImage, homeBtn, profileBtn);
                cartTxt.setVisibility(View.VISIBLE);
                cartImage.setImageResource(R.drawable.cart_homepage);
                cartBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(cartBtn);
                selectedTab = 2;
            }
        });

        profileBtn.setOnClickListener(view -> {
            if (selectedTab != 3) {
                resetTabs(activity, homeTxt, cartTxt, homeImage, cartImage, homeBtn, cartBtn);
                profileTxt.setVisibility(View.VISIBLE);
                profileImage.setImageResource(R.drawable.profile_homepage);
                profileBtn.setBackgroundResource(R.drawable.round_back_homepage);
                startScaleAnimation(profileBtn);
                selectedTab = 3;
            }
        });
    }

    private static void resetTabs(AppCompatActivity activity, TextView txt1, TextView txt2, ImageView img1, ImageView img2, LinearLayout btn1, LinearLayout btn2) {
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        img1.setImageResource(R.drawable.home_homepage);
        img2.setImageResource(R.drawable.cart_homepage);
        btn1.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        btn2.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
    }

    private static void startScaleAnimation(LinearLayout btn) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        btn.startAnimation(scaleAnimation);
    }
}
