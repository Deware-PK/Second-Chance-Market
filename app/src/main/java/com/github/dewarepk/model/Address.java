package com.github.dewarepk.model;

public class Address {

    private final String address;
    private final String apartment;
    private final String subDistrict;
    private final String district;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;

    public Address(String address, String apartment, String subDistrict, String district, String province, String postalCode, String phoneNumber) {
        this.address = address;
        this.apartment = apartment;
        this.subDistrict = subDistrict;
        this.district = district;
        this.province = province;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getApartment() {
        return apartment;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Method to return full address
    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();

        if (apartment != null && !apartment.isEmpty()) {
            fullAddress.append(apartment).append(", ");
        }

        if (address != null && !address.isEmpty()) {
            fullAddress.append(address).append(", ");
        }

        if (subDistrict != null && !subDistrict.isEmpty()) {
            fullAddress.append(subDistrict).append(", ");
        }

        if (district != null && !district.isEmpty()) {
            fullAddress.append(district).append(", ");
        }

        if (province != null && !province.isEmpty()) {
            fullAddress.append(province).append(" ");
        }

        if (postalCode != null && !postalCode.isEmpty()) {
            fullAddress.append(postalCode).append(", ");
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            fullAddress.append("Phone: ").append(phoneNumber);
        }

        // Remove trailing comma and space
        if (fullAddress.toString().endsWith(", ")) {
            fullAddress.setLength(fullAddress.length() - 2);
        }

        return fullAddress.toString();
    }

    public static Address createEmptyInstance() {
        return new Address("", "", "", "", "", "", "");
    }
}
