package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_store);

        this.findViewById(R.id.return_back_my_store).setOnClickListener(aVoid -> {
            this.startActivity(new Intent(MyStoreActivity.this , ProfileActivity.class));
            this.finish();
        });

        this.findViewById(R.id.add_product).setOnClickListener(aVoid -> {
            this.startActivity(new Intent(MyStoreActivity.this , AddProductActivity.class));
            this.finish();
        });

    }
}