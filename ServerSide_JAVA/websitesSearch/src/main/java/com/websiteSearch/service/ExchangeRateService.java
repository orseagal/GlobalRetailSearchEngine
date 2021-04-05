package com.websiteSearch.service;

import java.util.Hashtable;

import com.websiteSearch.exceptionHander.WebsiteSearchException;

public interface ExchangeRateService {

	
	public Hashtable<String, Double> getRates() throws WebsiteSearchException ;
	
	public double getConversion(Hashtable<String, Double> rates ,String convertFrom, String convertTo,double amount) throws WebsiteSearchException ;
	
	
	
}
