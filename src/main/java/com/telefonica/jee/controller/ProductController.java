package com.telefonica.jee.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.telefonica.jee.bean.Manufacturer;
import com.telefonica.jee.bean.Quote;
import com.telefonica.jee.model.Product;
import com.telefonica.jee.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	private final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;


	@GetMapping("/products")
	public List<Product> getAllProducts() {
		log.debug("request to get Products");
		return productService.findAll();
	}

	/** 
	 * It calls to another RESTful web service in order to get quote object
	 * @return
	 */
	@GetMapping("/quotes")
	public Quote getRemoteQuotes() {
		 RestTemplate restTemplate = new RestTemplate();
	     Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
	     log.info(quote.toString());
	     return quote;
	}
	
	/**
	 * It calls to another RESTful web service in order to get quote object
	 * @return
	 */
	@GetMapping("/manufacturers/{id}")
	public Manufacturer getRemoteManufacturer(@PathVariable Long id) {
		 RestTemplate restTemplate = new RestTemplate();
	     Manufacturer man = restTemplate.getForObject("http://localhost:8081/manufacturers/" + id, Manufacturer.class);
	     log.info(man.toString());
	     return man;
	}
	

	/**
	 * GET /products/:id : get the "id" product.
	 *
	 * @param id the id of the product to retrieve
	 * @return
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		log.debug("request to get Product : {}", id);
		Optional<Product> product = productService.findOne(id);
		if (product.isPresent()) {
			return ResponseEntity.ok().body(product.get());
		} 

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * GET /products/:id : get the "id" product.
	 *
	 * @param id the id of the product to retrieve
	 * @return
	 * @throws URISyntaxException 
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) throws URISyntaxException {
		log.debug("request to save Product : {}", product);
		if (product.getId() != null) {
			// TODO throw exception product already exists
			return null;
		}
		Product result = productService.save(product);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
                .body(result);
	}
	
    @PutMapping("/products")
    public ResponseEntity<Product> updateCronjob(@RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
			// TODO throw exception product not exists
			return null;        
		}
        
		Product result = productService.save(product);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
                .body(result);
    }
    
    @GetMapping("/products/count")
    public ResponseEntity<Long> countProducts() {
        log.debug("REST request to count Products");
        return ResponseEntity.ok().body(productService.count());
    }
    

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteCronjob(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
	

}
