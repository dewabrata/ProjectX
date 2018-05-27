package com.projectx.main.modelservice.vendor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;
import com.projectx.main.Application.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 3/12/2018.
 */
@Table(database = AppController.class)
public class Vendor extends BaseModel implements Parcelable {


    @Column
    @PrimaryKey
    private String id;
    @Column
    private String imageUrl;
    @Column
    private Double lat;
    @PropertyName("long")
    @Column
    private Double lon;
    @Column
    private String name;
    @Column
    private String parentId;
    @Column
    private String thumbnailUrl;
    @Column
    private Boolean hasChild;
    @Column
    private String categoryId;



    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    @PropertyName("long")
    public Double getLon() {
        return lon;
    }
    @PropertyName("long")
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.imageUrl);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
        dest.writeString(this.name);
        dest.writeString(this.parentId);
        dest.writeString(this.thumbnailUrl);
        dest.writeByte((byte) (this.hasChild ? 1 : 0));
        dest.writeString(this.categoryId);

    }

    public Vendor() {
    }

    protected Vendor(Parcel in) {
        this.id = in.readString();
        this.imageUrl = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();;
        this.name = in.readString();
        this.parentId = in.readString();
        this.thumbnailUrl = in.readString();
        this.hasChild = in.readByte() != 0;
        this.categoryId =  in.readString();
    }

    public static final Parcelable.Creator<Vendor> CREATOR = new Parcelable.Creator<Vendor>() {
        @Override
        public Vendor createFromParcel(Parcel source) {
            return new Vendor(source);
        }

        @Override
        public Vendor[] newArray(int size) {
            return new Vendor[size];
        }
    };
}
