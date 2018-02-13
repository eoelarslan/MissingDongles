package com.missingdongles.dao;

import com.missingdongles.model.Dongle;

import java.util.List;


public interface DongleDao {

    /**
     * This is the method to be used to list down
     * all the Kanyon records from the TB_MERCHANT_DONGLES table.
     */
    List<Dongle> listDongles();
}
