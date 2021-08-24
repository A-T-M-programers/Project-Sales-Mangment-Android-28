package com.example.awmsales;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sales  implements Serializable {
    private int sal_id,count;
    private Person person;
    private users user;
    private Product product;
    private Date registration_date;
    private Double amount;
    private Double discount;
    private Double total;
    private Double afterdiscount;

    public Sales(int sal_id, Person person, Product product,users user, Date registration_date, Double amount, Double discount,Double total,Double afterdiscount,int count) {
        this.sal_id = sal_id;
        this.person = person;
        this.product = product;
        this.user = user;
        this.registration_date = registration_date;
        this.amount = amount;
        this.discount = discount;
        this.afterdiscount = afterdiscount;
        this.total = total;
        this.count = count;
    }

    public Sales(){}

    public int getSal_id() {
        return sal_id;
    }

    public void setSal_id(int sal_id) {
        this.sal_id = sal_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Product getRegion() {
        return product;
    }

    public void setRegion(Product product) {
        this.product = product;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getAfterdiscount() {
        return afterdiscount;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getTotal() {
        return total;
    }

    public Product getProduct() {
        return product;
    }

    public users getUser() {
        return user;
    }

    public void setAfterdiscount(Double afterdiscount) {
        this.afterdiscount = afterdiscount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setUser(users user) {
        this.user = user;
    }
}
