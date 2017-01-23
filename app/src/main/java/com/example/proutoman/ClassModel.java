package com.example.proutoman;

/**
 * Created by apprenti on 23/01/17.
 */

public class ClassModel {
    private Float lat, lon;
    private int resultProut;
    private String resultDate;

    public ClassModel(Float lat, Float lon, int resultProut, String resultDate) {
        this.lat = lat;
        this.lon = lon;
        this.resultProut = resultProut;
        this.resultDate = resultDate;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public int getResultProut() {
        return resultProut;
    }

    public void setResultProut(int resultProut) {
        this.resultProut = resultProut;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }
}
