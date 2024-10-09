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

import com.github.dewarepk.OrderTrackingActivity;
import com.github.dewarepk.R;
import com.github.dewarepk.TemporaryCache;
import com.github.dewarepk.model.CartAdapterMode;
import com.github.dewarepk.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ItemViewHolder> {

    private final ArrayList<ItemData> items;
    private final Context context;
    private final CartAdapterMode mode;
    private IEvent event;

    public CartItemAdapter(Context context, ArrayList<ItemData> items) {
        this.items = items;
        this.context = context;
        this.mode = CartAdapterMode.HOLDING;
    }

    public CartItemAdapter(Context context, ArrayList<ItemData> items, CartAdapterMode mode) {
        this.items = items;
        this.context = context;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ItemViewHolder holder, int position) {
        ItemData currentItem = items.get(position);

        holder.titleTxt.setText(currentItem.getHeader());
        holder.priceText.setText("฿" + currentItem.getPrice());

        Picasso.get()
                .load(currentItem.getPictureUrl())
                .placeholder(R.drawable.shirt_holder)
                .into(holder.imageView);

        holder.deleteIcon.setVisibility(View.INVISIBLE);
        holder.deleteIcon.setActivated(false);

        if (mode == CartAdapterMode.HOLDING) {
            holder.deleteIcon.setVisibility(View.VISIBLE);
            holder.deleteIcon.setActivated(true);
            holder.deleteIcon.setOnClickListener(aVoid -> {
                TemporaryCache.getInstance().deleteItem(currentItem);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                if (event != null) {
                    event.onDelete(currentItem, position);
                }
            });
        } else {
            bindClickEvent(holder.imageView);
            bindClickEvent(holder.titleTxt);
        }

    }

    public void callDeleteEvent(IEvent event) {
        this.event = event;
    }

    public interface IEvent {

        void onDelete(ItemData item, int position);
    }

    private <T extends View> void bindClickEvent(T item) {
        item.setOnClickListener(aVoid -> {
            Intent nextIntent = new Intent(context, OrderTrackingActivity.class);
            context.startActivity(nextIntent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, deleteIcon;
        TextView titleTxt, priceText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_showcase_sub);
            titleTxt = itemView.findViewById(R.id.item_title);
            priceText = itemView.findViewById(R.id.price_holder);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }
    }



}
