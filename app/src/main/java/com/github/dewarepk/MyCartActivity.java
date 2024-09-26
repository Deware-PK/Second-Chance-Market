package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.Address;
import com.github.dewarepk.model.ItemData;
import com.github.dewarepk.model.ItemPool;
import com.github.dewarepk.model.SimpleCallback;
import com.github.dewarepk.model.WalletHandler;
import com.github.dewarepk.model.WalletMode;
import com.github.dewarepk.model.widget.CartItemAdapter;
import com.github.dewarepk.util.SimpleUtil;
import com.marsad.stylishdialogs.StylishAlertDialog;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;


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

        DecimalFormat df = new DecimalFormat("#.##");
        subTotal.setText(df.format(TemporaryCache.getInstance().getPriceTotal()) + " ฿");
        double total = Double.parseDouble(df.format(TemporaryCache.getInstance().getPriceTotal() + 36));
        totalSum.setText(df.format(total) + " ฿");

        orderButton.setOnClickListener(aVoid -> {
            SimpleUtil.getCurrentUserBalance(MyCartActivity.this.getApplicationContext(), result -> {
                if (result < total) {
                    new StylishAlertDialog(this, StylishAlertDialog.ERROR)
                            .setTitleText("Oops...")
                            .setContentText("You don't have enough fund.!")
                            .show();
                } else {
                    showConfirmationDialog(TemporaryCache.getInstance().getCarts() , total);
                }

            });
        });

        RecyclerView recyclerView = this.findViewById(R.id.recycle_view_cart);
        recyclerView.setAdapter(new CartItemAdapter(this, SimpleUtil.convertListToArrayList(TemporaryCache.getInstance().getCarts())));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void showConfirmationDialog(List<ItemData> products, double price) {


        StylishAlertDialog dialog = new StylishAlertDialog(this, StylishAlertDialog.WARNING)
                .setTitleText("Are you sure?")
                .setContentText("Your order will be sent to seller.")
                .setConfirmText("Yes, Buy it!")
                .setConfirmClickListener(new StylishAlertDialog.OnStylishClickListener() {
                    @Override
                    public void onClick(StylishAlertDialog sDialog) {

                        SimpleUtil.getCurrentUserAddress(MyCartActivity.this, new SimpleCallback<Address>() {
                            @Override
                            public void onDataReceived(Address result) {
                                if (result.getAddress().isEmpty()
                                        || result.getDistrict().isEmpty()
                                        || result.getPhoneNumber().isEmpty()
                                        || result.getPostalCode().isEmpty()
                                        || result.getProvince().isEmpty()
                                        || result.getSubDistrict().isEmpty()) {
                                    Toast.makeText(MyCartActivity.this, "Please set your address first.", Toast.LENGTH_SHORT).show();
                                    sDialog.dismissWithAnimation();
                                } else {

                                    WalletHandler wallet = new WalletHandler();

                                    wallet.updateBalance(SimpleUtil.getCurrentUserId(MyCartActivity.this.getApplicationContext()), price, WalletMode.WITHDRAW);

                                    sDialog.dismissWithAnimation();

                                    // Fix here
                                    for (int i = 0; i < products.size(); i++) {
                                        TemporaryCache.getInstance().addBoughtList(products.get(i));
                                        ItemPool.getInstance().deleteItem(products.get(i).getUuid(), true);

                                    }


                                    startActivity(new Intent(MyCartActivity.this, HomePageActivity.class));
                                    finish();

                                    TemporaryCache.getInstance().clearCarts();
                                }
                            }
                        });

                    }
                })
                .setCancelButton("Cancel", new StylishAlertDialog.OnStylishClickListener() {
                    @Override
                    public void onClick(StylishAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });

        // แสดง dialog ทุกครั้งที่ฟังก์ชันนี้ถูกเรียก
        dialog.setCancelledOnTouchOutside(false);
        dialog.show();
    }
}