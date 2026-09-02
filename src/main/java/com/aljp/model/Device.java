package com.aljp.model;

public class Device {

    private Integer id;
    private String name;
    private String serialNumber;
    private String Ubicación;
    private String type;
    private String Estate;

    public Device() {
    }

    public Device(Integer id, String name, String serialNumber, String ubicación, String type, String estate) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.Ubicación = ubicación;
        this.type = type;
        this.Estate = estate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUbicación() {
        return Ubicación;
    }

    public void setUbicación(String ubicación) {
        this.Ubicación = ubicación;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEstate() {
        return Estate;
    }

    public void setEstate(String estate) {
        this.Estate = estate;
    }
}
