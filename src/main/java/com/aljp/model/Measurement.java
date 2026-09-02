package com.aljp.model;

public class Measurement {

    private Integer id;
    private String timestamp;
    private double value;
    private String unit;
    private Integer assetId;

    public Measurement() {
    }

    public Measurement(Integer id, String timestamp, double value, String unit, Integer assetId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.unit = unit;
        this.assetId = assetId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }
}
