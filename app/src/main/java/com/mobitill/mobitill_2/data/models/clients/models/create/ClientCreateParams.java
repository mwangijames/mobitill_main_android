package com.mobitill.mobitill_2.data.models.clients.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClientCreateParams {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("appid")
    @Expose
    private String appid;

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *
     * @param appid
     * The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public boolean isEmpty(){
        return (appid == null || "".equals(appid)) ||
                (email == null || "".equals(email)) ||
                (name == null || "".equals(name)) ||
                (phone == null || "".equals(phone));
    }

}