package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.identity.cbor.Simple;
import com.github.dewarepk.model.ItemPool;
import com.github.dewarepk.model.ItemType;
import com.github.dewarepk.model.widget.CustomActivity;
import com.github.dewarepk.model.widget.ItemAdapter;
import com.github.dewarepk.model.widget.NavigationProperty;
import com.github.dewarepk.util.SimpleUtil;

public class SearchPageActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);

        Intent intent = this.getIntent();
        this.makeSearchBar(R.id.search_bar);
        this.makeNavigationBar();
        this.setDefaultTab(0);

        TextView fullNameDisplay = this.findViewById(R.id.fullname_display);
        ItemType type = ItemType.fromString(intent.getStringExtra("type"));
        RecyclerView recyclerView = this.findViewById(R.id.view_holder);
        TextView searchText = this.findViewById(R.id.search_keyword);

        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        switch (type) {

            case CLOTHE:

                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.CLOTHE))));
                searchText.setText("Searching of 'CLOTHE'");
                break;

            case TOY:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.TOY))));
                searchText.setText("Searching of 'TOY'");
                break;

            case TOOL:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.TOOL))));
                searchText.setText("Searching of 'TOOL'");
                break;

            case DIGITAL:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.DIGITAL))));
                searchText.setText("Searching of 'DIGITAL'");
                break;

            case FURNITURE:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.FURNITURE))));
                searchText.setText("Searching of 'FURNITURE'");
                break;

            case INSTRUMENT:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemByType(ItemType.INSTRUMENT))));
                searchText.setText("Searching of 'INSTRUMENT'");
                break;

            case CUSTOMIZE:
                String searchKey = intent.getStringExtra("keyword");
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new ItemAdapter(this, SimpleUtil.convertListToArrayList(ItemPool.getInstance().getItemsByKeyword(searchKey))));
                searchText.setText("Searching of '{0}'".replace("{0}" , searchKey.toUpperCase()));
                break;
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