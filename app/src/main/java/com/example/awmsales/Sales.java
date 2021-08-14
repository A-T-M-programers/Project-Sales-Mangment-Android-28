package com.example.awmsales;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sales  implements Serializable {
    private int sal_id;
    private Person person;
    private Region region;
    private Date registration_date;
    private Double amount;
    private Double commission;

    public Sales(int sal_id, Person person, Region region, Date registration_date, Double amount, Double commission) {
        this.sal_id = sal_id;
        this.person = person;
        this.region = region;
        this.registration_date = registration_date;
        this.amount = amount;
        this.commission = commission;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
}
