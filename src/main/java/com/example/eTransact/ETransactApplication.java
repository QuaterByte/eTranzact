package com.example.eTransact;

import com.example.eTransact.Model.Account;
import com.example.eTransact.repository.AccountRepository;
import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ETransactApplication {

	public static void main(String[] args) {
		SpringApplication.run(ETransactApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(AccountRepository accountRepository){
		return args -> {
			Account account = new Account();
			//account.setBalance(Money.of(BigDecimal.TEN, "USD"));
			Account saved = accountRepository.save(account);
			System.out.println(saved.getAccountNo());
			System.out.println(saved.getId());
			System.out.println(saved.getBalance());

		};
	}


}
