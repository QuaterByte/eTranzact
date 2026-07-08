package com.example.eTransact.controller;

import com.example.eTransact.Model.Account;
import com.example.eTransact.Model.RegistrationForm;
import com.example.eTransact.Model.User;
import com.example.eTransact.error.FormError;
import com.example.eTransact.repository.AccountRepository;
import com.example.eTransact.repository.UserRepository;
import com.example.eTransact.response.FormErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepository;
    private  final AccountRepository accountRepository;
    private final  PasswordEncoder encoder;


    public UserController(UserRepository userRepository, AccountRepository accountRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> processUser(@Valid @RequestBody RegistrationForm form, Errors errors){

        if(form == null)
            throw  new IllegalArgumentException("form object can't be null");

        boolean passwordMisMatched = false;
        boolean isUnderAge = isUnderAge(form.getDob());
        boolean emailNotMatched = false;


        if(!errors.hasFieldErrors("email")){
            Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
            if (userOptional.isPresent())
                return new ResponseEntity<>(new FormErrorResponse("email already exist", null), HttpStatus.UNPROCESSABLE_CONTENT);
            else
                emailNotMatched = !form.getEmail().equalsIgnoreCase(form.getConfirmEmail());
        }

        if (!errors.hasFieldErrors("password")){
            passwordMisMatched = !form.getPassword().equals(form.getConfirmPassword());
        }

        if (errors.hasErrors()  || emailNotMatched || passwordMisMatched || isUnderAge){

            return new ResponseEntity<>(
                    new FormErrorResponse("Unable to process as 1 or more errors occured",
                            formError(errors, emailNotMatched, passwordMisMatched, isUnderAge)),
                    HttpStatusCode.valueOf(422));
        }
        User user = linkAccountToUser(form.toUser(encoder), User.class);

        URI  location = toURi("/user/{id}", user.getId());
        return ResponseEntity.created(location).build();

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id")UUID uuid){

        Optional<User> user = userRepository.findById(uuid);
        if(user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user.get());
    }
    @PostMapping("/addaccount")
    public ResponseEntity<?> addAccount(@RequestBody UUID id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            return new ResponseEntity<>(new FormErrorResponse("user does not exist", null), HttpStatus.NOT_FOUND);
        Account account = linkAccountToUser(userOptional.get(), Account.class);
        URI location = toURi("/account/{id}", account.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    ResponseEntity<List<User>> getUserLists(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


    @Transactional
    private  <T> T linkAccountToUser(User user, Class<T> c){
        UUID id = user.getId();
        Account account = new Account();
        user.addAccount(account);
        userRepository.save(user);
        accountRepository.save(account);
        if(id == null)
            return c.cast(user);
        return c.cast(account);
    }

    private URI toURi(String path, UUID id){
        URI uri = ServletUriComponentsBuilder.fromPath(path).buildAndExpand(id).toUri();
        return uri;
    }
    private boolean isUnderAge(LocalDate date){
        if(date == null)
            return true;
        LocalDate now = LocalDate.now();
        if(now.getYear() - date.getYear() < 18)
            return true;
        if(now.getYear() - date.getYear() > 18)
            return false;
        if(now.getMonthValue() > date.getMonthValue())
            return false;
        if (now.getMonthValue() < date.getMonthValue())
            return true;
        return now.getDayOfMonth() < date.getDayOfMonth();
    }

    private  FormError formError(Errors errors, boolean  emailNotMatched, boolean passwordMisMatched, boolean isUnderAge){
        FormError error = new FormError();

        if(emailNotMatched){
            error.setConfirmEmail("must be an exact match with email");
        }
        if(isUnderAge){
            error.setDob("user must be at least 18 years old to register");
        }
        if(passwordMisMatched){
            error.setConfirmPassword("must be an exact match with password");
        }
        if(errors.hasFieldErrors("firstname"))
            error.setFirstname(errors.getFieldError("firstname").getDefaultMessage());
        if (errors.hasFieldErrors("lastname"))
            error.setLastname(errors.getFieldError("lastname").getDefaultMessage());
        if (errors.hasFieldErrors("phoneNumber"))
            error.setPhoneNumber("Please enter a valid Nigeria phone number");
        if (errors.hasFieldErrors("password"))
            error.setPassword("password must be at least 8 digits long and must contain lowercase, uppercase, digit and special character");
        if (errors.hasFieldErrors("email"))
            error.setEmail(errors.getFieldError("email").getDefaultMessage());
        if (errors.hasFieldErrors("address.street"))
            error.setStreet(errors.getFieldError("address.street").getDefaultMessage());
        if (errors.hasFieldErrors("address.city.name"))
            error.setCityName(errors.getFieldError("address.city.name").getDefaultMessage());
        if (errors.hasFieldErrors("address.city.state"))
            error.setState(errors.getFieldError("address.city.state").getDefaultMessage());
        if(errors.hasFieldErrors("address.city.country"))
            error.setCountry(errors.getFieldError("address.city.country").getDefaultMessage());
        if (errors.hasFieldErrors("address.city.postalCode"))
            error.setPostalCode(errors.getFieldError("address.city.postalCode").getDefaultMessage());
        if(errors.hasFieldErrors("address"))
            error.setAddress("Address can't be null");
        if (errors.hasFieldErrors("address.city"))
            error.setCity("City can't be null");
        return error;
    }

}
