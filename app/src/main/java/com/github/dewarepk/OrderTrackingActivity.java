package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrderTrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_order);

        ImageView returnButton = this.findViewById(R.id.imagereturn);

        returnButton.setOnClickListener(l -> {
            this.startActivity(new Intent(OrderTrackingActivity.this, OrderListActivity.class));
            this.finish();
        });
    }
}