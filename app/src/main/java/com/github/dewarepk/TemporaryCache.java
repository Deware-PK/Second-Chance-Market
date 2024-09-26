package com.github.dewarepk;

import com.github.dewarepk.model.ItemData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TemporaryCache {

    private static final TemporaryCache instace = new TemporaryCache();

    private final List<ItemData> cart = new ArrayList<>();
    private final HashSet<ItemData> boughtList = new HashSet<>();

    public void addToCart(ItemData item) {
        cart.add(item);
    }

    public void deleteItem(ItemData item) {
        cart.remove(item);
    }

    public void addBoughtList(ItemData item) {

        boughtList.add(item);
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

    public Set<ItemData> getBoughtList() {
        return Collections.unmodifiableSet(boughtList);
    }

    public List<ItemData> getBoughtListAsArray() {
        return new ArrayList<>(boughtList);
    }

    public void clearCarts() {
        cart.clear();
    }

    public static TemporaryCache getInstance() {
        return instace;
    }
}
