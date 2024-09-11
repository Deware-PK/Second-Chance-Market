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

    public static Address createEmptyInstance() {
        return new Address("", "", "", "", "", "", "");
    }
}
