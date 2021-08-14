package com.example.awmsales;

import java.io.Serializable;

public class Region implements Serializable {
    private int reg_id;
    private String name;

    public Region(int reg_id, String name) {
        this.reg_id = reg_id;
        this.name = name;
    }

    public Region(){}

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
