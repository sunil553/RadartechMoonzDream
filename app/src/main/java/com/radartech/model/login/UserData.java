package com.radartech.model.login;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customertype")
    @Expose
    private Integer customertype;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("contact")
    @Expose
    private String contact;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The customertype
     */
    public Integer getCustomertype() {
        return customertype;
    }

    /**
     *
     * @param customertype
     * The customertype
     */
    public void setCustomertype(Integer customertype) {
        this.customertype = customertype;
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
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * The account
     */
    public String getAccount() {
        return account;
    }

    /**
     *
     * @param account
     * The account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     *
     * @return
     * The pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     *
     * @param pid
     * The pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     *
     * @return
     * The type
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone
     * The telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
        Log.i("shiva", "telephone : " + telephone);
    }

    /**
     *
     * @return
     * The contact
     */
    public String getContact() {
        return contact;
    }

    /**
     *
     * @param contact
     * The contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}
