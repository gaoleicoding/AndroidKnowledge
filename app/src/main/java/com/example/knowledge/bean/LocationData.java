package com.example.knowledge.bean;

public class LocationData {
    private String longitude;
    private String latitude;
    private String address;

    private String loacationStyle;

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getLoacationStyle() {
        return loacationStyle;
    }

    public void setLoacationStyle(String loacationStyle) {
        this.loacationStyle = loacationStyle;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}