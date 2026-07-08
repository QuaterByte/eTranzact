package com.example.eTransact;

import com.example.eTransact.Model.*;
import com.example.eTransact.controller.UserController;
import com.example.eTransact.repository.AccountRepository;
import com.example.eTransact.repository.UserRepository;
import com.example.eTransact.response.FormErrorResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ETransactApplicationTests extends BaseClass{
	@Test
	void contextLoads() {
	}

	@Test
	void shouldCreateUserWithAccount(){
		City city = new City("Lagos", "Lagos", "102102", "Nigeria");
		Address address = new Address("Mile 2", city);
		RegistrationForm form = new RegistrationForm("Mowale", "Kareem", "mowale@gmail.com", "mowale@gmail.com", "C5136ca1$", "C5136ca1$", address, LocalDate.ofYearDay(1999, 44), "09074431987");

		client.post().uri("/user/create").body(form).retrieve().toBodilessEntity();

		City city1 = new City("Boston", "Massachusett", "202101", "United State");
		Address address1 = new Address("Havard Road", city1);
		RegistrationForm form1 = new RegistrationForm("Salah", "Muhammed", "salah@gmail.com", "salah@gmail.com", "C5136ca1$", "C5136ca1$", address1, LocalDate.of(1993, 7, 23), "09098765432");

		client.post().uri("/user/create").body(form1).retrieve().toBodilessEntity();

		City city2 = new City("New York", "New York", "105104", "United State");
		Address address2 = new Address("Ithaca", city2);
		RegistrationForm form2 = new RegistrationForm("Ronald", "Reagan", "ronald@gmail.com", "ronald@gmail.com", "C5136ca1$", "C5136ca1$", address2, LocalDate.of(1975, 3, 24), "09123456780");

		client.post().uri("/user/create").body(form2).retrieve().toBodilessEntity();

		List<User> users = client.get().uri("/user")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("ronald@gmail.com", "C5136ca1$"))
				.retrieve()
				.body(new ParameterizedTypeReference<List<User>>() {
			@Override
			public Type getType() {
				return super.getType();
			}
		});

		assertAll(
				()-> assertEquals(3, users.size()),
				()-> assertEquals(1, users.getFirst().getAccounts().size()),
				()-> assertEquals(1, users.getLast().getAccounts().size()),
				()-> assertEquals("Mowale", users.getFirst().getFirstname()),
				()-> assertEquals(city, users.getFirst().getAddress().getCity())
		);
	}

	@Test
	void shouldLinkNewAccountToAnExistingUser(){
		List<User> users = client.get().uri("/user")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("ronald@gmail.com", "C5136ca1$"))
				.retrieve().body(new ParameterizedTypeReference<List<User>>() {
			@Override
			public Type getType() {
				return super.getType();
			}
		});
		client.post().uri("/user/addaccount")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("ronald@gmail.com", "C5136ca1$"))
				.body(users.getFirst().getId()).retrieve().toBodilessEntity();

		User use = client.get().uri("/user/{id}", users.getFirst().getId())
				.headers(httpHeaders -> httpHeaders.setBasicAuth("ronald@gmail.com", "C5136ca1$"))
				.retrieve().body(User.class);
		assertAll(
				()-> assertEquals(2, use.getAccounts().size()),
				()-> assertEquals(1, users.getLast().getAccounts().size())

		);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionIfFormIsNull(){


		assertAll(
				()-> assertThrows(IllegalArgumentException.class, ()-> client.post().uri("/user/create").body(null).retrieve().toBodilessEntity())
		);
	}

	@Test
	void shouldReturn422WhenAddressIsNull(){
		RegistrationForm form = new RegistrationForm("Abed", "Troy", "ab@gmail.com", "ab@gmail.com", "C5136ca1$", "C5136ca1$", new Address(), LocalDate.of(1999, 11, 30), "09098765432");
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, ()->
				client.post().uri("/user/create").body(form).retrieve().toEntity(FormErrorResponse.class)
				);

		assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, exception.getStatusCode());
	}
}
