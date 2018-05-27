package com.projectx.main.modelservice.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.projectx.main.Application.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 3/11/2018.
 */
@Table(database = AppController.class)
public class Category extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private String id;
    @Column
    private String  imageUrl;
    @Column
    private String name;
    @Column
    private Boolean isActive;
    @Column
    private String thumbnailUrl;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.imageUrl);
        dest.writeString(this.name);
        dest.writeByte((byte) (this.isActive ? 1 : 0));
        dest.writeString(this.thumbnailUrl);

    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.id = in.readString();
        this.imageUrl = in.readString();
        this.name = in.readString();
        this.isActive =in.readByte() != 0;
        this.thumbnailUrl = in.readString();

    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
