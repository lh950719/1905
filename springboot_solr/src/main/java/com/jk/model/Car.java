package com.jk.model;

import java.io.Serializable;
import java.util.Date;

public class Car implements Serializable {
    private Integer carid;

    private String carname;

    private Integer carsales;

    private String cartime;

    private String cartype;

    public Integer getCarid() {
        return carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public Integer getCarsales() {
        return carsales;
    }

    public void setCarsales(Integer carsales) {
        this.carsales = carsales;
    }

    public String getCartime() {
        return cartime;
    }

    public void setCartime(String cartime) {
        this.cartime = cartime;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }
}