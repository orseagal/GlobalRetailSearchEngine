package com.websiteSearch.controller;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.websiteSearch.service.ExchangeRateService;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateRestController {


	@Autowired
	private ExchangeRateService exchangeRateService;
	
	
	@GetMapping("/convert/{convertFrom}/{convertTo}/{amount}")
	public double getConversionRates(@RequestBody Hashtable<String, Double> tableRates, @PathVariable String convertFrom, @PathVariable String convertTo,@PathVariable double amount) {

		return exchangeRateService.getConversion(tableRates, convertFrom, convertTo,amount);
	}
	
	
	@GetMapping("/getRates")
	public Hashtable<String, Double> ratesFromApi() {
	
		return exchangeRateService.getRates();
	}
	
}
