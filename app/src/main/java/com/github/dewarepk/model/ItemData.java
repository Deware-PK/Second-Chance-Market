package com.github.dewarepk.model;

import java.util.UUID;

public class ItemData {

    private final UUID uuid;
    private final String header;
    private final String pictureUrl;
    private final String[] details;
    private final double price;
    private final ItemType type;

    public ItemData(String header, String pictureUrl,double price, ItemType type, String... details) {
        this.uuid = java.util.UUID.randomUUID();
        this.header = header;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.type = type;
        this.details = details;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getHeader() {
        return header;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public double getPrice() {
        return price;
    }

    public ItemType getType() {
        return type;
    }

    public String getDetails() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < details.length; i++) {
            builder.append(details[i]);

            if (i < details.length - 1)
                builder.append("\n");

        }

        return builder.toString();
    }
}
