package com.missingdongles.rowmapper;

import com.missingdongles.model.Dongle;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DongleRowMapper implements RowMapper<Dongle> {
    public Dongle mapRow(ResultSet rs, int rowNum) throws SQLException {

        Dongle dongle = new Dongle();

        dongle.setMerchant(rs.getString("Merchant"));
        dongle.setDescription(rs.getString("Description"));
        dongle.setDeviceSerial(rs.getString("Device_Serial"));
        dongle.setEventTs(rs.getTimestamp("Last_Event"));
        dongle.setBattery(rs.getInt("Battery"));
        dongle.setBrandName(rs.getString("BRAND_NAME"));
        dongle.setVendorName(rs.getString("VENDOR_NAME"));

        return dongle;
    }
}
