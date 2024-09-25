package com.github.dewarepk;

import com.github.dewarepk.model.ItemData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TemporaryCache {

    private static final TemporaryCache instace = new TemporaryCache();

    private final List<ItemData> cart = new ArrayList<>();

    public void addToCart(ItemData item) {
        cart.add(item);
    }

    public void deleteItem(ItemData item) {
        cart.remove(item);
    }

    public Double getPriceTotal() {
        double sum = 0.0D;
        for (ItemData data : this.cart) {
            sum += data.getPrice();
        }

        return sum;
    }

    public boolean isContains(ItemData item) {
        for (ItemData data : this.cart)
            if (data.equals(item))
                return true;

        return false;

    }

    public List<ItemData> getCarts() {
        return Collections.unmodifiableList(cart);
    }

    public static TemporaryCache getInstance() {
        return instace;
    }
}
