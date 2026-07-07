package com.example.eTransact;

import com.example.eTransact.Model.Address;
import com.example.eTransact.Model.City;
import com.example.eTransact.Model.RegistrationForm;
import com.example.eTransact.repository.AccountRepository;
import com.example.eTransact.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseClass {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    RestClient client;


    @BeforeAll
    void populatedata(){
        client = RestClient.create("http://localhost:8080");
    }

}
