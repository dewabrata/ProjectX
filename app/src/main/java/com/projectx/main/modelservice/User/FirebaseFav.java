package com.projectx.main.modelservice.User;

public class FirebaseFav {
    private  String category;
    private String id;
    private String parentVendor;
    private String type;
    private String vendor;



    public FirebaseFav(String category, String id, String parentVendor, String type, String vendor) {
        this.category = category;
        this.id = id;
        this.parentVendor = parentVendor;
        this.type = type;
        this.vendor = vendor;
    }




    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentVendor() {
        return parentVendor;
    }

    public void setParentVendor(String parentVendor) {
        this.parentVendor = parentVendor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }



}
