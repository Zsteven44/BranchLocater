package com.szafrani.branchlocater;


import android.support.annotation.Nullable;

public class Location {
    private final String state;
    private final String type;
    private final String address;
    private final String city;
    private final String zip;
    private final String name;
    private final String bank;
    private final String phone;

    public Location(@Nullable String state,
                    @Nullable String type,
                    @Nullable String address,
                    @Nullable String city,
                    @Nullable String zip,
                    @Nullable String name,
                    @Nullable String bank,
                    @Nullable String phone) {
        this.state = state;
        this.type = type;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.name = name;
        this.bank = bank;
        this.phone = phone;
    }

    public String getState() {
        if (state != null) {
            return state;
        } else {
            return "";
        }
    }

    public String getType() {
        if (type != null) {
            return type;
        } else {
            return "";
        }
    }

    public String getAddress() {
        if (address!= null) {
            return address;
        } else {
            return "";
        }
    }

    public String getCity() {
        if (city!= null) {
            return city;
        } else {
            return "";
        }
    }

    public String getZip() {
        if (zip != null) {
            return zip;
        } else {
            return "";
        }
    }

    public String getName() {
        if (name!= null) {
            return name;
        } else {
            return "";
        }
    }

    public String getBank() {
        if (bank!= null) {
            return bank;
        } else {
            return "";
        }
    }

    public String getPhone() {
        if (phone!= null) {
            return phone;
        } else {
            return "";
        }
    }
}
