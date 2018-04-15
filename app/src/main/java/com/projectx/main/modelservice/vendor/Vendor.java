package com.projectx.main.modelservice.vendor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

/**
 * Created by user on 3/12/2018.
 */

public class Vendor implements Parcelable {


    private String id;
    private String imageUrl;
    private Float lat;
    @PropertyName("long")
    private Float lon;
    private String name;
    private String parentId;
    private String thumbnailUrl;
    private Boolean hasChild;
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

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
    @PropertyName("long")
    public Float getLon() {
        return lon;
    }
    @PropertyName("long")
    public void setLon(Float lon) {
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

    public Boolean getHasChild() {
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
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lon);
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
        this.lat = in.readFloat();
        this.lon = in.readFloat();;
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
