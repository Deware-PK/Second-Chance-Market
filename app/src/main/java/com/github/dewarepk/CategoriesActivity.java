package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.ItemPool;
import com.github.dewarepk.model.widget.CustomActivity;
import com.github.dewarepk.model.widget.ItemAdapter;
import com.github.dewarepk.model.widget.NavigationProperty;
import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.ValidateUtil;

public class CategoriesActivity extends CustomActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        this.makeSearchBar(R.id.search_bar);
        this.setDefaultTab(1);
        this.makeNavigationBar();

        TextView fullNameDisplay = this.findViewById(R.id.fullname_display);
        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        /** Properties **/
        ImageView clothesButton = this.findViewById(R.id.image_tshirt);
        ImageView digitalButton = this.findViewById(R.id.image_computer);
        ImageView toolButton = this.findViewById(R.id.image_tools);
        ImageView furnitureButton = this.findViewById(R.id.image_furniture);
        ImageView toyButton = this.findViewById(R.id.image_toys);
        ImageView instrumentButton = this.findViewById(R.id.image_music);

        this.catalogClickEvent(clothesButton, "CLOTHE");
        this.catalogClickEvent(digitalButton, "DIGITAL");
        this.catalogClickEvent(toolButton, "TOOL");
        this.catalogClickEvent(furnitureButton, "FURNITURE");
        this.catalogClickEvent(toyButton, "TOY");
        this.catalogClickEvent(instrumentButton, "INSTRUMENT");

    }

    private void catalogClickEvent(ImageView buttonImage, String type) {
        buttonImage.setOnClickListener(aVoid -> {
            Intent nextPageOfSearch = new Intent(CategoriesActivity.this, SearchPageActivity.class);
            nextPageOfSearch.putExtra("type", type);
            this.startActivity(nextPageOfSearch);
        });
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