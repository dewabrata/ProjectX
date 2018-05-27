
package com.projectx.main.modelservice.mapsvendor;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapsVendor implements Serializable, Parcelable
{

    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("long")
    @Expose
    private Integer _long;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("hasChild")
    @Expose
    private Boolean hasChild;
    @SerializedName("promoCount")
    @Expose
    private Integer promoCount;
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<MapsVendor> CREATOR = new Creator<MapsVendor>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MapsVendor createFromParcel(Parcel in) {
            return new MapsVendor(in);
        }

        public MapsVendor[] newArray(int size) {
            return (new MapsVendor[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6816808731558851575L;

    protected MapsVendor(Parcel in) {
        this.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.favourite = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this._long = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.hasChild = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.promoCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parentId = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.lat = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MapsVendor() {
    }

    /**
     * 
     * @param phone
     * @param imageUrl
     * @param categoryId
     * @param id
     * @param parentId
     * @param _long
     * @param email
     * @param address
     * @param name
     * @param thumbnailUrl
     * @param active
     * @param hasChild
     * @param favourite
     * @param promoCount
     * @param lat
     */
    public MapsVendor(String thumbnailUrl, Integer favourite, Integer _long, String id, Boolean hasChild, Integer promoCount, String parentId, String categoryId, String imageUrl, String phone, Integer lat, String address, Boolean active, String email, String name) {
        super();
        this.thumbnailUrl = thumbnailUrl;
        this.favourite = favourite;
        this._long = _long;
        this.id = id;
        this.hasChild = hasChild;
        this.promoCount = promoCount;
        this.parentId = parentId;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.lat = lat;
        this.address = address;
        this.active = active;
        this.email = email;
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public Integer getLong() {
        return _long;
    }

    public void setLong(Integer _long) {
        this._long = _long;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getPromoCount() {
        return promoCount;
    }

    public void setPromoCount(Integer promoCount) {
        this.promoCount = promoCount;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thumbnailUrl);
        dest.writeValue(favourite);
        dest.writeValue(_long);
        dest.writeValue(id);
        dest.writeValue(hasChild);
        dest.writeValue(promoCount);
        dest.writeValue(parentId);
        dest.writeValue(categoryId);
        dest.writeValue(imageUrl);
        dest.writeValue(phone);
        dest.writeValue(lat);
        dest.writeValue(address);
        dest.writeValue(active);
        dest.writeValue(email);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
