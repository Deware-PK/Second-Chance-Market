package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.ItemPool;
import com.github.dewarepk.model.widget.ItemAdapter;
import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.ValidateUtil;

public class HomePageActivity extends AppCompatActivity {


    private int selectedTab = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        final LinearLayout homeBtn = findViewById(R.id.homeBtn);
        final LinearLayout cartBtn = findViewById(R.id.cartBtn);
        final LinearLayout profileBtn = findViewById(R.id.profileBtn);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView cartImage = findViewById(R.id.cartImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView CartTxt = findViewById(R.id.CartTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if home is already selected or not
                if(selectedTab != 1){

                    // unselected other tabs expert home Tab
                    CartTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);


                    cartImage.setImageResource(R.drawable.cart_homepage);
                    profileImage.setImageResource(R.drawable.profile_homepage);


                    cartBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.home_homepage);
                    homeBtn.setBackgroundResource(R.drawable.round_back_homepage);

                    // animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f , 1.0f, 1f, 1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeBtn.startAnimation(scaleAnimation);


                    selectedTab = 1;
                }
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 2){

                    // unselected other tabs expert home Tab
                    homeTxt.setVisibility(View.GONE);
                    CartTxt.setVisibility(View.GONE);


                    homeImage.setImageResource(R.drawable.home_homepage);
                    cartImage.setImageResource(R.drawable.cart_homepage);


                    homeBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    CartTxt.setVisibility(View.VISIBLE);
                    cartImage.setImageResource(R.drawable.cart_homepage);
                    cartBtn.setBackgroundResource(R.drawable.round_back_homepage);

                    // animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f , 1.0f, 1f, 1f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    cartBtn.startAnimation(scaleAnimation);


                    selectedTab = 2;
                }


            }
        });
        profileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedTab != 3){

                    // unselected other tabs expert home Tab
                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);


                    homeImage.setImageResource(R.drawable.home_homepage);
                    profileImage.setImageResource(R.drawable.profile_homepage);


                    homeBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profile_homepage);
                    profileBtn.setBackgroundResource(R.drawable.round_back_homepage);

                    // animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f , 1.0f, 1f, 1f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    cartBtn.startAnimation(scaleAnimation);


                    selectedTab = 3;
                }
            }
        });





        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        TextView fullNameDisplay = this.findViewById(R.id.fullname_display);

        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        LinearLayout profileButton = this.findViewById(R.id.profileBtn);

        profileButton.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
            this.finish();
        });


        ItemAdapter adapter = new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItems()));
        RecyclerView recyclerView = this.findViewById(R.id.view_holder);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);


    }

}