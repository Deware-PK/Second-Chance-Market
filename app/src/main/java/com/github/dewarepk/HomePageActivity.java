package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

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