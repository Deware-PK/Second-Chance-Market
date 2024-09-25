package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.widget.CartItemAdapter;
import com.github.dewarepk.util.SimpleUtil;

import org.w3c.dom.Text;

import java.text.DecimalFormat;


public class MyCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_cart);

        ImageView returnBack = this.findViewById(R.id.return_back_my_cart);
        ImageView goNextAddress = this.findViewById(R.id.location_go_next_img);
        TextView subTotal = this.findViewById(R.id.subtotal_cost);
        TextView totalSum = this.findViewById(R.id.total_sum);
        AppCompatButton orderButton = this.findViewById(R.id.order_now_button);

        returnBack.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(this, HomePageActivity.class));
            this.finish();
        });

        goNextAddress.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(MyCartActivity.this, LocationEditorActivity.class));
            this.finish();
        });

        orderButton.setOnClickListener(aVoid -> {

        });

        DecimalFormat df = new DecimalFormat("#.##");
        subTotal.setText(df.format(TemporaryCache.getInstance().getPriceTotal()) + " ฿");
        double total = TemporaryCache.getInstance().getPriceTotal() + 36;
        totalSum.setText(df.format(total) + " ฿");

        RecyclerView recyclerView = this.findViewById(R.id.recycle_view_cart);
        recyclerView.setAdapter(new CartItemAdapter(this, SimpleUtil.convertListToArrayList(TemporaryCache.getInstance().getCarts())));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}