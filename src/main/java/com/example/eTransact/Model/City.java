package com.example.eTransact.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class City {

    @NotBlank
    @Size(min = 2, message = "write a valid city name")
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 3, message = "please enter a valid state name")
    private String state;

    @Pattern(regexp = "^[0-9]{6}$", message = "please provide a valid Nigerian postal code")
    @Column(nullable = false)
    private String postalCode;

    @NotBlank
    @Column(nullable = false)
    @Size(min=3, message = "please enter a valid country name")
    private String country;

    public City(String name, String state, String postalCode, String country){
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.name = name;
    }

    public City(){}

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

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return  true;
        if(obj == null || getClass() != obj.getClass())
            return  false;
        City city = (City)obj;
        return this.name.equals(city.name) && this.state.equals(city.state) && this.country.equals(city.country) && this.postalCode.equals(city.postalCode);
    }
}
