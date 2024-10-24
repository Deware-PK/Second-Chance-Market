package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        EditText textId = this.findViewById(R.id.edit_text_id);
        EditText fillBox = this.findViewById(R.id.fillBox);
        AppCompatButton report = this.findViewById(R.id.report_button);
        ImageView returnBack = this.findViewById(R.id.return_back_my_profile);

        returnBack.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(ReportActivity.this , ProfileActivity.class));
            this.finish();
        });

        report.setOnClickListener(aVoid -> {
            if (textId.getText().toString().isEmpty()) {
                Toast.makeText(ReportActivity.this.getApplicationContext(), "Please fill the product ID.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fillBox.getText().toString().isEmpty()) {
                Toast.makeText(ReportActivity.this.getApplicationContext(), "Please fill the report form.", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(ReportActivity.this.getApplicationContext(), "Report Successfully!", Toast.LENGTH_SHORT).show();
            this.startActivity(new Intent(ReportActivity.this , ProfileActivity.class));
            this.finish();
        });
    }
}