package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.PaymentMethodDaoJdbc;

import java.sql.Date;


/**
 * Created by flowerpower on 2017. 05. 02..
 */
public class Order {
    private int id;
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String billingAddress;
    private String shippingAddress;
    private String description;
    private int paymentMethod;
    private Date date;
    private int status;

    public Order() {
        this(0, "");
    }

    public Order(int userId, String description) {
        this(0, userId, null, null, null, null, null, description,0, new Date(System.currentTimeMillis()),0);
    }

    public Order(int id, int userId, String name, String email, String phoneNumber, String billingAddress, String shippingAddress, String description, int paymentMethod, Date date, int status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.status = status;
    }

    public void setByUser(String name, String email, String phoneNumber, String billingAddress, String shippingAddress, String description) {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setBillingAddress(billingAddress);
        setShippingAddress(shippingAddress);
        setDescription(description);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethodDaoJdbc.getInstance().find(paymentMethod);
    }

    public Date getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }
}
