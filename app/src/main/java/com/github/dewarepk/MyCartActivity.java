package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MyCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_cart);

        ImageView returnBack = this.findViewById(R.id.return_back_my_cart);

        returnBack.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(this, HomePageActivity.class));
            this.finish();
        });
    }
}