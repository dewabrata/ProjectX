package com.projectx.main.modelservice.favourite;

import com.projectx.main.Application.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = AppController.class)

public class Favourite extends BaseModel {

    public Favourite(String vendorId) {
        this.vendorId = vendorId;
    }
    public Favourite(){

    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }


    @Column
    @PrimaryKey
    private String vendorId;


}
