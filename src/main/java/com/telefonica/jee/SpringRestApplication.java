package com.telefonica.jee;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.telefonica.jee.model.Product;
import com.telefonica.jee.repository.ProductRepository;

@SpringBootApplication
public class SpringRestApplication implements CommandLineRunner{

	@Autowired
	private ProductRepository productRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// Create data
		productRepository.save(new Product("product1", new BigDecimal(4.99), 5));
		productRepository.save(new Product("product2", new BigDecimal(9.99), 5));
		productRepository.save(new Product("product3", new BigDecimal(29.99), 5));

	}

}
