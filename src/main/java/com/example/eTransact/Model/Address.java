package com.example.eTransact.Model;

public class Address {

    private String street;
    private City city;

    public Address(String street, City city){
        this.city = city;
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
