package com.example.eTransact.Model;

public class City {

    private String name;
    private String state;
    private String postalCode;
    private String country;

    public City(String name, String state, String postalCode, String country){
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setState(String state) {
        this.state = state;
    }
}
