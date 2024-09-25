package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.marsad.stylishdialogs.StylishAlertDialog;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        AppCompatButton confirm = this.findViewById(R.id.add_product_confirm);

        confirm.setOnClickListener(aVoid -> {
            new StylishAlertDialog(this, StylishAlertDialog.ERROR)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong!")
                    .setConfirmClickListener(l -> {
                        this.startActivity(new Intent(this, ProfileActivity.class));
                        this.finish();
                    })
                    .show();

        });
    }
}
