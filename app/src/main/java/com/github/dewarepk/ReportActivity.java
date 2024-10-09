package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dewarepk.model.InvalidAddressCause;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppCompatButton report = this.findViewById(R.id.report_button);
        report.setOnClickListener(aVoid -> {

            Toast.makeText(ReportActivity.this.getApplicationContext(), "Report Successfully!", Toast.LENGTH_SHORT).show();
            this.finish();
        });
    }
}