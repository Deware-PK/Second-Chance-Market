package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.CartAdapterMode;
import com.github.dewarepk.model.widget.CartItemAdapter;
import com.github.dewarepk.util.SimpleUtil;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_list);

        RecyclerView recyclerView = this.findViewById(R.id.recycle_view_order);
        ImageView returnBack = this.findViewById(R.id.return_back_my_profile);
        CartItemAdapter adapter = new CartItemAdapter(this,
                SimpleUtil.convertListToArrayList(TemporaryCache.getInstance().getBoughtListAsArray()),
                CartAdapterMode.SOLD);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        returnBack.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(this, ProfileActivity.class));
            this.finish();
        });
    }
}