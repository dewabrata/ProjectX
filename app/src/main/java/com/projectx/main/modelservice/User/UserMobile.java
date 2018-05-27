package com.projectx.main.modelservice.User;

import com.projectx.main.Application.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppController.class)
public class UserMobile extends BaseModel {

    @Column
    private Boolean active ;
    @Column
    private String address;
    @Column
    private String created;
    @Column
    private String dob;
    @Column
    private String email;
    @Column
    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String occupation;
    @Column
    private Boolean online;
    @Column
    private String sex;
    @Column
    private String type;
    @Column
    private String userUid;


public UserMobile(){}

    public UserMobile(Boolean active, String address, String created, String dob, String email, String id, String name, String occupation, Boolean online, String sex, String type, String userUid) {
        this.active = active;
        this.address = address;
        this.created = created;
        this.dob = dob;
        this.email = email;
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.online = online;
        this.sex = sex;
        this.type = type;
        this.userUid = userUid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean isOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }














}
