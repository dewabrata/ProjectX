package com.projectx.main.modelservice.favourite;

import com.projectx.main.Application.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppController.class)

public class Star extends BaseModel {

    public Star(String merchantId) {
        this.merchantId = merchantId;
    }
    public Star(){

    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    @Column
    @PrimaryKey
    private String merchantId;


}
