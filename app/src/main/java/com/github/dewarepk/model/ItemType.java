package com.github.dewarepk.model;

public enum ItemType {

    CLOTHE,
    DIGITAL,
    TOOL,
    FURNITURE,
    TOY,
    INSTRUMENT,
    CUSTOMIZE;

    public static ItemType fromString(String type) {
        try {
            return ItemType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
