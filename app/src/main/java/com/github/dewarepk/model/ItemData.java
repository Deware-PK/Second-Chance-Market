package com.github.dewarepk.model;

public class ItemData {

    private final String header;
    private final String pictureUrl;
    private final int review;
    private final float score;
    private final double price;
    private final ItemType type;

    public ItemData(String header, String pictureUrl, int review, float score, double price, ItemType type) {
        this.header = header;
        this.pictureUrl = pictureUrl;
        this.review = review;
        this.score = score;
        this.price = price;
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getReview() {
        return review;
    }

    public float getScore() {
        return score;
    }

    public double getPrice() {
        return price;
    }

    public ItemType getType() {
        return type;
    }
}
