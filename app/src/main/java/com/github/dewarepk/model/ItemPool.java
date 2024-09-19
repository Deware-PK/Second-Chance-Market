package com.github.dewarepk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class ItemPool {

    private static final ItemPool instance = new ItemPool();

    private final ArrayList<ItemData> items = new ArrayList<>();

    private ItemPool() {
        registerDefault();
    }

    public void addItem(ItemData item) {
        items.add(item);
    }

    public void addItem(String title, String pictureUrl, double price, ItemType type, String... description) {
        ItemData item = new ItemData(title, pictureUrl, price, type, description);
        items.add(item);
    }

    public void deleteItem(UUID uuid) {
        for (ItemData item : items) {
            if (item.getUuid().equals(uuid)) {
                items.remove(item);
                return;
            }
        }
    }

    public void deleteItem(ItemData item) {
        items.remove(item);
    }


    public List<ItemData> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<ItemData> getItemByType(ItemType type) {
        List<ItemData> byTypes = new ArrayList<>();

        for (ItemData item : this.items) {
            if (item.getType().equals(type)) {
                byTypes.add(item);
            }
        }

        return byTypes;
    }


    public static ItemPool getInstance() {
        return instance;
    }

    private void registerDefault() {
        items.add(new ItemData("0",
                "https://clipart-library.com/image_gallery2/Knife-PNG-Picture.png",
                5,
                ItemType.CLOTHE,
                "Line1",
                "Line2",
                "Line3"));
        items.add(new ItemData("1",
                "https://play-lh.googleusercontent.com/QPo012nDmGHkDLe7MtXkP4J7y_W_MZ8VgfyY6Iqx79ePl_n-0o6cDFLKI0ATdMEqea2Gnq_WtQEY32U5Zg",
                5,
                ItemType.CLOTHE,
                "Hello",
                "Line 2 asdasdasdasd",
                "Line 3"));
    }
}
