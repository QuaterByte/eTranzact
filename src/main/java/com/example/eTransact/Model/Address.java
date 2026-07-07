package com.example.eTransact.Model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Embeddable
public class Address {

    @NotBlank
    @Size(min = 2, message = "write a valid street name")
    private String street;

    @AttributeOverride(name = "name", column = @Column(name = "city", nullable = false))
    @Valid
    @NotNull
    private City city;

    public Address(String street, City city){
        this.city = city;
        this.street = street;
    }
    public Address(){}

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
