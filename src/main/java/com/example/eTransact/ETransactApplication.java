package com.example.eTransact;


import com.example.eTransact.Model.Address;
import com.example.eTransact.Model.City;
import com.example.eTransact.Model.RegistrationForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.moneta.MonetaMoneyModule;

import java.time.LocalDate;

@SpringBootApplication
public class ETransactApplication {


	public static void main(String[] args) {
		 SpringApplication.run(ETransactApplication.class, args);

	}

	@Bean
	public ObjectMapper mapper(){
		return JsonMapper.builder().addModule(new MonetaMoneyModule()).build();
	}

}
