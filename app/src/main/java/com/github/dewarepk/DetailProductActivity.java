package com.github.dewarepk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.model.Address;
import com.github.dewarepk.model.ItemData;
import com.github.dewarepk.model.ItemPool;
import com.github.dewarepk.model.SimpleCallback;
import com.github.dewarepk.model.WalletHandler;
import com.github.dewarepk.model.WalletMode;
import com.github.dewarepk.util.SimpleUtil;
import com.google.android.material.button.MaterialButton;
import com.marsad.stylishdialogs.StylishAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class DetailProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);

        Intent intent = this.getIntent();

        String productHeader = intent.getStringExtra("productHeader");
        String productPictureUrl = intent.getStringExtra("productPictureUrl");
        double productPrice = intent.getDoubleExtra("productPrice", 0);
        String productType = intent.getStringExtra("productType");
        String productDetails = intent.getStringExtra("productDetails");
        UUID productUuid = UUID.fromString(intent.getStringExtra("productUuid"));
        ItemData rawItem = ItemPool.getInstance().getItemByUuid(productUuid);

        ImageView returnButton = this.findViewById(R.id.detail_return_button);
        ImageView addCart = this.findViewById(R.id.add_order);
        TextView header = this.findViewById(R.id.product_header);
        ImageView showcase = this.findViewById(R.id.product_detail_display);
        TextView priceTag = this.findViewById(R.id.price_detail);
        TextView details = this.findViewById(R.id.details);

        header.setText(productHeader);
        Picasso.get()
                .load(productPictureUrl)
                .into(showcase);
        priceTag.setText(productPrice + " ฿");
        details.setText(productDetails);
        returnButton.setOnClickListener(aVoid -> {
            this.startActivity(new Intent(this, HomePageActivity.class));
            this.finish();
        });

        addCart.setOnClickListener(l -> {

            if (TemporaryCache.getInstance().isContains(rawItem)) {
                Toast.makeText(DetailProductActivity.this, "You already have this item in your cart.", Toast.LENGTH_SHORT).show();
                return;
            }

            TemporaryCache.getInstance().addToCart(rawItem);
            Toast.makeText(DetailProductActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });

        // Dialog
        AppCompatButton buyNowButton = this.findViewById(R.id.buy_btn);


        buyNowButton.setOnClickListener(aVoid -> {

            SimpleUtil.getCurrentUserBalance(DetailProductActivity.this.getApplicationContext(), result -> {
                if (result < productPrice) {
                    new StylishAlertDialog(this, StylishAlertDialog.ERROR)
                            .setTitleText("Oops...")
                            .setContentText("You don't have enough fund.!")
                            .show();
                } else {
                    showConfirmationDialog(productUuid , productPrice);
                }

            });
        });
    }


    private void showConfirmationDialog(UUID productUuid, double price) {


        StylishAlertDialog dialog = new StylishAlertDialog(this, StylishAlertDialog.WARNING)
                .setTitleText("Are you sure?")
                .setContentText("Your balance will be deducted automatically.")
                .setConfirmText("Yes, Buy it!")
                .setConfirmClickListener(new StylishAlertDialog.OnStylishClickListener() {
                    @Override
                    public void onClick(StylishAlertDialog sDialog) {

                        SimpleUtil.getCurrentUserAddress(DetailProductActivity.this, new SimpleCallback<Address>() {
                            @Override
                            public void onDataReceived(Address result) {
                                if (result.getAddress().isEmpty()
                                        || result.getDistrict().isEmpty()
                                        || result.getPhoneNumber().isEmpty()
                                        || result.getPostalCode().isEmpty()
                                        || result.getProvince().isEmpty()
                                        || result.getSubDistrict().isEmpty()) {
                                    Toast.makeText(DetailProductActivity.this, "Please set your address first.", Toast.LENGTH_SHORT).show();
                                    sDialog.dismissWithAnimation();
                                } else {

                                    WalletHandler wallet = new WalletHandler();

                                    wallet.updateBalance(SimpleUtil.getCurrentUserId(DetailProductActivity.this.getApplicationContext()), price, WalletMode.WITHDRAW);

                                    sDialog.dismissWithAnimation();
                                    startActivity(new Intent(DetailProductActivity.this, HomePageActivity.class));
                                    finish();
                                    ItemPool.getInstance().deleteItem(productUuid);
                                }
                            }
                        });

                    }
                })
                .setCancelButton("Cancel", new StylishAlertDialog.OnStylishClickListener() {
                    @Override
                    public void onClick(StylishAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        // No action on cancel, simply dismiss
                    }
                });

        // แสดง dialog ทุกครั้งที่ฟังก์ชันนี้ถูกเรียก
        dialog.setCancelledOnTouchOutside(false);
        dialog.show();
    }
}