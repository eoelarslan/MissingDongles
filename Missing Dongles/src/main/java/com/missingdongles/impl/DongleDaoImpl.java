package com.missingdongles.impl;

import com.missingdongles.dao.DongleDao;
import com.missingdongles.model.Dongle;
import com.missingdongles.rowmapper.DongleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DongleDaoImpl implements DongleDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
deneme
    dsad
        dsad

    private String selectStatement = "SELECT DISTINCT t4.`NAME` Merchant, t2.DESCRIPTION Description, t2.DEVICE_SERIAL Device_Serial,"
            + " t2.EVENT_TS Last_Event, t2.BATTERY Battery, t5.`NAME` BRAND_NAME, t3.`NAME` VENDOR_NAME"
            + " FROM TB_VENDOR_DONGLE t1, TB_MERCHANT_DONGLES t2, TB_VENDOR t3, TB_MERCHANT t4, TB_BRAND t5"
            + " WHERE t4.BRAND_ID=t5.ID"
            + " AND t1.VENDOR_ID=t3.ID"
            + " AND t2.MERCHANT_ID=t4.ID"
            + " AND t1.MERCHANT_DONGLE_ID=t2.ID"
            + " AND t2.`MERCHANT_ID`!=5543"
            + " AND t2.`MERCHANT_ID`!=5542"
            + " AND t2.`MERCHANT_ID`!=667"
            + " AND t2.DEVICE_TYPE=0"
            + " AND t2.`STATUS`=1"
            + " GROUP BY t2.DEVICE_SERIAL"
            + " ORDER  BY t4.`NAME`, t2.DEVICE_SERIAL"
            + ";";

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Dongle> listDongles() {
        return namedParameterJdbcTemplate.query(selectStatement, new DongleRowMapper());
    }
}
