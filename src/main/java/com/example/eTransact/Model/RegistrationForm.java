package com.example.eTransact.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegistrationForm {
    @NotBlank
    @Size(min = 3, max = 25, message = "please enter a valid firstname, must be between 3 and 25 characters long ")
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 25, message = "please enter a valid lastname, must be between 3 and 25 characters long ")
    private String lastname;
    @Email
    @NotNull
    private String email;
    private String confirmEmail;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=.!]).{8,}$", message = "password must be at least 8 digits long and must contain lowercase, uppercase, digit and special character")
    private String password;
    private String confirmPassword;
    @NotNull
    @Valid
    private Address address;

    private LocalDate dob;
    @NotNull
    @Pattern(regexp = "^0[7-9][0-1][0-9]{8}$", message = "please enter a valid Nigeria phone number")
    private String phoneNumber;

    public RegistrationForm(String firstname, String lastname, String email,
                            String confirmEmail, String password, String confirmPassword,
                            Address address, LocalDate dob, String phoneNumber){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.address = address;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Address getAddress() {
        return address;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public User toUser(){
        User user = new User();
        user.setAddress(address);
        user.setDob(dob);
        user.setEmail(email);
        user.setLastName(lastname);
        user.setFirstname(firstname);
        user.setPassword(password);
        user.setPhoneNo(phoneNumber);
        return  user;
    }
}
