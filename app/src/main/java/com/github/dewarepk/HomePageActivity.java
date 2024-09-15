package com.github.dewarepk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dewarepk.model.SimpleCallback;
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

    }
}