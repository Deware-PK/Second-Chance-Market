package com.github.dewarepk.model.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.DetailProductActivity;
import com.github.dewarepk.R;
import com.github.dewarepk.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final ArrayList<ItemData> items;
    private final Context context;

    public ItemAdapter(Context context, ArrayList<ItemData> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_item_list, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        ItemData currentItem = items.get(position);

        holder.titleTxt.setText(currentItem.getHeader());
        holder.priceText.setText("Price: ฿ู" + currentItem.getPrice());

        Picasso.get()
                .load(currentItem.getPictureUrl())
                .placeholder(R.drawable.shirt_holder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(aVoid -> {
            Intent nextIntent = new Intent(context, DetailProductActivity.class);
            nextIntent.putExtra("productHeader", currentItem.getHeader());
            nextIntent.putExtra("productPictureUrl", currentItem.getPictureUrl());
            nextIntent.putExtra("productPrice", currentItem.getPrice());
            nextIntent.putExtra("productType", currentItem.getType());
            nextIntent.putExtra("productDetails", currentItem.getDetails());
            nextIntent.putExtra("productUuid", currentItem.getUuid().toString());
            context.startActivity(nextIntent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTxt, priceText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceText = itemView.findViewById(R.id.price_holder);
        }
    }

}
