package com.example.eTransact.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Person")
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Size(min = 3, max = 30, message = "must be between 3 to 30 characters long")
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 30, message = "ust be between 3 to 30 characters long")
    private String lastName;
    @NotNull
    private LocalDate dob;
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9._%+-]{0,63}@(?:[A-Za-z0-9-]{1,63}\\.){1,125}[A-Za-z]{2,63}$", message = "please enter a valid email")
    private String email;
    @Valid
    private  Address address;
    @NotNull
    @JsonIgnore
    private String password;
    @Pattern(regexp = "^0[7-9][0-1][0-9]{8}$", message = "please enter a valid Nigeria phone number")
    private String phoneNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(@NotNull Account account){
        if(account.getUser() != null)
            throw new IllegalStateException("Account already has an owner");
        accounts.add(account);
        account.setUser(this);
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String fullname(){
        return lastName + " " + firstname;
    }
}
