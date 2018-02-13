package com.missingdongles.model;

import java.sql.Timestamp;

public class Dongle {

    private String merchant;
    private String description;
    private String deviceSerial;
    private Timestamp eventTs;
    private int battery;
    private String brandName;
    private String vendorName;
    //private int age;

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public Timestamp getEventTs() {
        return eventTs;
    }

    public void setEventTs(Timestamp eventTs) {
        this.eventTs = eventTs;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "Dongle{"
                + "Merchant='" + merchant + '\''
                + ", Description='" + description + '\''
                + ", Device_Serial='" + deviceSerial + '\''
                + ", Event_Ts=" + eventTs
                + ", battery=" + battery
                + ", Brand_Name='" + brandName + '\''
                + ", Vendor_Name='" + vendorName + '\''
                + '}';
    }


}
