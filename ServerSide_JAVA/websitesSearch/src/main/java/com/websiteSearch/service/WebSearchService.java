package com.websiteSearch.service;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import com.websiteSearch.entity.Ebay;
import com.websiteSearch.entity.Element;
import com.websiteSearch.entity.Mercadolibre;
import com.websiteSearch.exceptionHander.WebsiteSearchException;

public interface WebSearchService {

	public CompletableFuture<LinkedHashSet<Element>> test(Ebay ebay, String id) throws WebsiteSearchException;

	public LinkedHashSet<Element> getEbaySearchGlobal(Ebay ebay) throws WebsiteSearchException;	
	
	public LinkedHashSet<Element> getEbaySearchByLocation(Ebay ebay) throws WebsiteSearchException;
	
	public LinkedHashSet<Element> getMercadolibreSearch(Mercadolibre mercadolibre) throws WebsiteSearchException;
	
	public LinkedHashSet<Element> getMercadolibreSearchByLocation(Mercadolibre mercadolibre) throws WebsiteSearchException;
	
	public List<Element> getkijijiItaly(String searchKey) throws WebsiteSearchException;
	
	public JSONObject getOLXSearch(String searchKey , String location) throws WebsiteSearchException;
	
	public void ebaySearchAutomatation(Ebay ebay) throws WebsiteSearchException;
	
	public Map<String,String> ebayIdList();
	
}
