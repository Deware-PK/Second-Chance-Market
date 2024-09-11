package com.github.dewarepk;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.activity.EdgeToEdge;
public class TopUpActivity extends AppCompatActivity {
    private ImageView returnBackTopup;
    private AppCompatButton buttonOneHundred;
    private AppCompatButton buttonTwoHundred;
    private AppCompatButton buttonThreeHundred;
    private AppCompatButton buttonFiveHundred;
    private AppCompatButton buttonOneThousand;
    private AppCompatButton buttonTwoThousand;
    private EditText totalTopup;
    private AppCompatButton confirmButtonTopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up);

        returnBackTopup = findViewById(R.id.return_back_topup);
        buttonOneHundred = findViewById(R.id.button_one_hundred);
        buttonTwoHundred = findViewById(R.id.button_two_hundred);
        buttonThreeHundred = findViewById(R.id.button_three_hundred);
        buttonFiveHundred = findViewById(R.id.button_five_hundred);
        buttonOneThousand = findViewById(R.id.button_one_thousand);
        buttonTwoThousand = findViewById(R.id.button_2000);
        totalTopup = findViewById(R.id.Totaltopup);
        confirmButtonTopup = findViewById(R.id.confirm_button_topup);
        returnBackTopup.setOnClickListener(v -> onImageClick());

        buttonOneHundred.setOnClickListener(v -> updateTotalTopup("100"));
        buttonTwoHundred.setOnClickListener(v -> updateTotalTopup("200"));
        buttonThreeHundred.setOnClickListener(v -> updateTotalTopup("300"));
        buttonFiveHundred.setOnClickListener(v -> updateTotalTopup("500"));
        buttonOneThousand.setOnClickListener(v -> updateTotalTopup("1000"));
        buttonTwoThousand.setOnClickListener(v -> updateTotalTopup("2000"));

        confirmButtonTopup.setOnClickListener(v -> onConfirmClick());
    }

    public void onImageClick() {

    }
    private void updateTotalTopup(String amount) {
        totalTopup.setText(amount + " $");
    }

    private void onConfirmClick() {

    }
}