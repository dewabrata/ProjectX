package com.projectx.main.modelservice.vendor;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Merchandise implements Serializable, Parcelable
{

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("promoPrice")
    @Expose
    private String promoPrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("endDate")
    @Expose
    private int endDate;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("favourite")
    @Expose
    private int favourite;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("id")
    @Expose
    private String id;
    public final static Parcelable.Creator<Merchandise> CREATOR = new Creator<Merchandise>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Merchandise createFromParcel(Parcel in) {
            return new Merchandise(in);
        }

        public Merchandise[] newArray(int size) {
            return (new Merchandise[size]);
        }

    }
            ;
    private final static long serialVersionUID = 2176815450935387092L;

    protected Merchandise(Parcel in) {
        this.startDate = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.promoPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.endDate = ((int) in.readValue((int.class.getClassLoader())));
        this.active = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.favourite = ((int) in.readValue((int.class.getClassLoader())));
        this.vendorId = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Merchandise() {
    }

    /**
     *
     * @param id
     * @param startDate
     * @param price
     * @param promoPrice
     * @param imageUrl
     * @param thumbnailUrl
     * @param name
     * @param categoryId
     * @param active
     * @param endDate
     * @param vendorId
     * @param itemId
     * @param favourite
     */
    public Merchandise(String startDate, String categoryId, String imageUrl, String promoPrice, String price, int endDate, boolean active, String itemId, String name, String thumbnailUrl, int favourite, String vendorId, String id) {
        super();
        this.startDate = startDate;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.promoPrice = promoPrice;
        this.price = price;
        this.endDate = endDate;
        this.active = active;
        this.itemId = itemId;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.favourite = favourite;
        this.vendorId = vendorId;
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Merchandise withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Merchandise withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Merchandise withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Merchandise withPromoPrice(String promoPrice) {
        this.promoPrice = promoPrice;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Merchandise withPrice(String price) {
        this.price = price;
        return this;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public Merchandise withEndDate(int endDate) {
        this.endDate = endDate;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Merchandise withActive(boolean active) {
        this.active = active;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Merchandise withItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Merchandise withName(String name) {
        this.name = name;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Merchandise withThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public Merchandise withFavourite(int favourite) {
        this.favourite = favourite;
        return this;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Merchandise withVendorId(String vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Merchandise withId(String id) {
        this.id = id;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(startDate);
        dest.writeValue(categoryId);
        dest.writeValue(imageUrl);
        dest.writeValue(promoPrice);
        dest.writeValue(price);
        dest.writeValue(endDate);
        dest.writeValue(active);
        dest.writeValue(itemId);
        dest.writeValue(name);
        dest.writeValue(thumbnailUrl);
        dest.writeValue(favourite);
        dest.writeValue(vendorId);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
