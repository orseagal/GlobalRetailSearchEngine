package com.websiteSearch.service;

import java.util.Hashtable;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.websiteSearch.exceptionHander.ErrorType;
import com.websiteSearch.exceptionHander.WebsiteSearchException;

@Service
public class ExchangeRateServiceImp implements ExchangeRateService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Hashtable<String, Double> getRates() throws WebsiteSearchException {
	String url = "https://api.exchangeratesapi.io/latest";
		
		@SuppressWarnings("unchecked")
		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);
		Hashtable<String, Double> table = new Hashtable<String, Double>();
		JSONObject elements;
		
		JSONObject root = new JSONObject(objects);
		if (root.getJSONObject("rates").isEmpty()) {
			throw new WebsiteSearchException(ErrorType.NO_MATCHES);
		} else {
			elements = root.getJSONObject("rates");
			for(String key : elements.keySet()) {
				table.put(key, elements.getDouble(key));		
			}
		}
		
		return table;
	}
	

	@Override
	public double getConversion(Hashtable<String, Double> rates, String convertFrom, String convertTo ,double amount)
			throws WebsiteSearchException {

		double result = 0;
		double convertFromValue = 0;
		double convertToValue = 0;
		//System.out.println(rates);
		
			if (convertFrom.equals("EUR")) {
				convertFromValue = 1;
				convertToValue = (double) rates.get(convertTo);
			} else if(convertTo.equals("EUR")){
				convertToValue = 1;
				convertFromValue = (double) rates.get(convertFrom);
			} else {
				convertFromValue = (double) rates.get(convertFrom);
				convertToValue = (double) rates.get(convertTo);
			}

	
			result = (convertToValue / convertFromValue);
			result = result * amount;
//			System.out.println(result);

		
		return result;
	}

	
	
	
	
		
}
