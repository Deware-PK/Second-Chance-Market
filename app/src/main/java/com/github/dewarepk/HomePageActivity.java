package com.github.dewarepk;

import android.os.Bundle;
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

public class HomePageActivity extends CustomActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        this.setDefaultTab(1);

        this.makeNavigationBar();

        TextView fullNameDisplay = this.findViewById(R.id.fullname_display);

        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        ItemAdapter adapter = new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItems()));
        RecyclerView recyclerView = this.findViewById(R.id.view_holder);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

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