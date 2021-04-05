package com.websiteSearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.websiteSearch.entity.Ebay;
import com.websiteSearch.entity.Element;
import com.websiteSearch.entity.Mercadolibre;
import com.websiteSearch.entity.NotificationEmail;
import com.websiteSearch.entity.User;
import com.websiteSearch.entity.UserSearch;
import com.websiteSearch.exceptionHander.ErrorType;
import com.websiteSearch.exceptionHander.WebsiteSearchException;
import com.websiteSearch.repository.UserRepository;


@Service
public class WebSearchServiceImp implements WebSearchService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	private Map<String,String> idArrMap = ebayIdList();
	
	@Async
	public CompletableFuture<LinkedHashSet<Element>> test(Ebay ebay, String id) {
		
		String searchKey = ebay.getKeywords().replaceAll(" ", "+");
		// String url =
		// "http://svcs.ebay.com/services/search/FindingService/v1?siteid=0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=JSON&paginationInput.entriesPerPage=50&keywords=";
//		String url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
//				+ ebay.getLocation() + "&REST-PAYLOAD&keywords=" + searchKey;
		Hashtable<String, Double> rates = exchangeRateService.getRates();

		String category = "";
		String listingType = ebay.getBuyingFormat();
		if(listingType!=null) {
			System.out.println("listingType not null");
			listingType = "&itemFilter.name=ListingType&itemFilter.value=" + listingType;
		}else {
			listingType="";
		}
		if (ebay.getCategory().equals("All")) {
			System.out.println("Category All");
		} else {
			category = "&categoryId=" + ebay.getCategory();
			System.out.println("Category" + ebay.getCategory());
		}

		String url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
				+ id + category + "&REST-PAYLOAD&keywords=" + searchKey + listingType;			
		System.out.println(url);

		@SuppressWarnings("unchecked")
		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);

		JSONObject root2 = new JSONObject(objects);
		JSONArray elements = root2.getJSONArray("findItemsAdvancedResponse");
		JSONObject jsonResult = elements.getJSONObject(0);
		JSONArray jsonArr = jsonResult.getJSONArray("searchResult");
		jsonResult = jsonArr.getJSONObject(0);
		LinkedHashSet<Element> results = new LinkedHashSet<>();
		try {

			jsonArr = jsonResult.getJSONArray("item");
		} catch (Exception e) {
			System.out.println("Exception ************* "+e);
			return CompletableFuture.completedFuture(results);
			//throw new WebsiteSearchException("Test", ErrorType.NO_MATCHES);
			// TODO: handle exception
		}

		

		for (int i = 0; i < jsonArr.length(); i++) {

			Element element = new Element();
			// System.out.println("jsonArr" + jsonArr);
			jsonResult = jsonArr.getJSONObject(i);

			element.setTitle((String) jsonResult.getJSONArray("title").get(0));
			try {
				element.setImage((String) jsonResult.getJSONArray("galleryURL").get(0));
			} catch (Exception e) {
				// TODO: handle exception
			}

			element.setPrice(jsonResult.getJSONArray("sellingStatus").getJSONObject(0)
					.getJSONArray("convertedCurrentPrice").getJSONObject(0).getDouble("__value__"));
			element.setCurrency(jsonResult.getJSONArray("sellingStatus").getJSONObject(0)
					.getJSONArray("convertedCurrentPrice").getJSONObject(0).getString("@currencyId"));
			element.setPrice(exchangeRateService.getConversion(rates, element.getCurrency(), ebay.getConversionTo(),
					element.getPrice()));
			element.setCurrency(ebay.getConversionTo());
			element.setId((String) jsonResult.getJSONArray("itemId").get(0));
			element.setViewItemURL((String) jsonResult.getJSONArray("viewItemURL").get(0));
			element.setWebSource(id);
			results.add(element);

		}
		
		 List<Element> targetList = List.copyOf(results);
		 
		//return results;
		
		
		System.out.println(Thread.currentThread().getName());
		
		return CompletableFuture.completedFuture(results);
			
	}

	
	

	@Override
	public LinkedHashSet<Element> getEbaySearchGlobal(Ebay ebay) throws WebsiteSearchException {

		String searchKey = ebay.getKeywords().replaceAll("%20", "+");
		Date startTime = new Date();
		String category = "";
		String listingType = ebay.getBuyingFormat();

		if (listingType != null) {
			listingType = "&itemFilter.name=ListingType&itemFilter.value=" + listingType;
		} else {
			listingType = "";
		}
		System.out.println(listingType);
		int minPrice = ebay.getPriceRange_min();
		int maxPrice = ebay.getPriceRange_max();

		System.out.println(minPrice + "  " + maxPrice);
		if (ebay.getCategory().equals("All")) {
			System.out.println("Category All");
		} else {
			category = "&categoryId=" + ebay.getCategory();
			System.out.println("Category" + ebay.getCategory());
		}
		
		LinkedHashSet<Element> results = new LinkedHashSet<>();

		Hashtable<String, Double> rates = exchangeRateService.getRates();

		for (String id : idArrMap.keySet()) {

			// String url =
			// "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
			// + id + "&REST-PAYLOAD&keywords=" + searchKey;
			// String url =
			// "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
			// + id + category + "&REST-PAYLOAD&keywords=" + searchKey;
			// "&itemFilter.name=MaxPrice&itemFilter.value=30000000&itemFilter.name=MinPrice&itemFilter.value=0
			String url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
					+ id + "&REST-PAYLOAD=true&keywords=" + searchKey + listingType;
			System.out.println(url);

			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> objects = restTemplate.getForObject(url, Map.class);
				// process ="Found at: " + idArrMap.get(id);
				JSONObject root = new JSONObject(objects);
				// JSONArray elements2 = root2.getJSONArray("findItemsByKeywordsResponse");
				JSONArray elements2 = root.getJSONArray("findItemsAdvancedResponse");
				JSONObject jsonResult2 = elements2.getJSONObject(0);
				JSONArray jsonArr2 = jsonResult2.getJSONArray("searchResult");
				jsonResult2 = jsonArr2.getJSONObject(0);

				try {

					jsonArr2 = jsonResult2.getJSONArray("item");
				} catch (Exception e) {
					throw new WebsiteSearchException("Test", ErrorType.NO_MATCHES);
					// TODO: handle exception
				}

				for (int i = 0; i < jsonArr2.length(); i++) {

					Element element = new Element();
					// System.out.println("jsonArr" + jsonArr);
					jsonResult2 = jsonArr2.getJSONObject(i);

					element.setTitle((String) jsonResult2.getJSONArray("title").get(0));
					try {
						element.setImage((String) jsonResult2.getJSONArray("galleryURL").get(0));
					} catch (Exception e) {
						// TODO: handle exception
					}

					element.setPrice(jsonResult2.getJSONArray("sellingStatus").getJSONObject(0)
							.getJSONArray("convertedCurrentPrice").getJSONObject(0).getDouble("__value__"));
					element.setCurrency(jsonResult2.getJSONArray("sellingStatus").getJSONObject(0)
							.getJSONArray("convertedCurrentPrice").getJSONObject(0).getString("@currencyId"));

					element.setPrice(exchangeRateService.getConversion(rates, element.getCurrency(),
							ebay.getConversionTo(), element.getPrice()));
					element.setCurrency(ebay.getConversionTo());

					element.setId((String) jsonResult2.getJSONArray("itemId").get(0));

					element.setViewItemURL((String) jsonResult2.getJSONArray("viewItemURL").get(0));
					element.setWebSource(idArrMap.get(id));
					results.add(element);
				}

			} catch (Exception e) {
				System.out.println("Not Matches: " + idArrMap.get(id));

			}

			// results2.addAll(results);
		}

//		for (String results2 : result.split(",")) {
//			
//			System.out.println(results2);
//		}
//			for (Long element : itemIdList) {
//				
//				System.out.println(itemIdList.contains(element));
//			}

		if (minPrice == 0 && maxPrice == 0) {
			return results;

		} else {
			LinkedHashSet<Element> results2 = new LinkedHashSet<>();
			for (Element element : results) {
				if (element.getPrice() > minPrice && element.getPrice() < maxPrice) {
					results2.add(element);

				}

			}

			
			return results2;
		}

	}

	@Override
	public LinkedHashSet<Element> getEbaySearchByLocation(Ebay ebay) throws WebsiteSearchException {

		String searchKey = ebay.getKeywords().replaceAll(" ", "+");
		// String url =
		// "http://svcs.ebay.com/services/search/FindingService/v1?siteid=0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=JSON&paginationInput.entriesPerPage=50&keywords=";
//		String url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
//				+ ebay.getLocation() + "&REST-PAYLOAD&keywords=" + searchKey;
		Hashtable<String, Double> rates = exchangeRateService.getRates();

		String category = "";
		String listingType = ebay.getBuyingFormat();
		if(listingType!=null) {
			System.out.println("listingType not null");
			listingType = "&itemFilter.name=ListingType&itemFilter.value=" + listingType;
		}else {
			listingType="";
		}
		if (ebay.getCategory().equals("All")) {
			System.out.println("Category All");
		} else {
			category = "&categoryId=" + ebay.getCategory();
			System.out.println("Category" + ebay.getCategory());
		}

		String url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&RESPONSE-DATA-FORMAT=JSON&GLOBAL-ID="
				+ ebay.getLocation() + category + "&REST-PAYLOAD&keywords=" + searchKey + listingType;			
		System.out.println(url);

		@SuppressWarnings("unchecked")
		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);

		JSONObject root2 = new JSONObject(objects);
		JSONArray elements = root2.getJSONArray("findItemsAdvancedResponse");
		JSONObject jsonResult = elements.getJSONObject(0);
		JSONArray jsonArr = jsonResult.getJSONArray("searchResult");
		jsonResult = jsonArr.getJSONObject(0);

		try {

			jsonArr = jsonResult.getJSONArray("item");
		} catch (Exception e) {
			throw new WebsiteSearchException("Test", ErrorType.NO_MATCHES);
			// TODO: handle exception
		}

		LinkedHashSet<Element> results = new LinkedHashSet<>();

		for (int i = 0; i < jsonArr.length(); i++) {

			Element element = new Element();
			// System.out.println("jsonArr" + jsonArr);
			jsonResult = jsonArr.getJSONObject(i);

			element.setTitle((String) jsonResult.getJSONArray("title").get(0));
			try {
				element.setImage((String) jsonResult.getJSONArray("galleryURL").get(0));
			} catch (Exception e) {
				// TODO: handle exception
			}

			element.setPrice(jsonResult.getJSONArray("sellingStatus").getJSONObject(0)
					.getJSONArray("convertedCurrentPrice").getJSONObject(0).getDouble("__value__"));
			element.setCurrency(jsonResult.getJSONArray("sellingStatus").getJSONObject(0)
					.getJSONArray("convertedCurrentPrice").getJSONObject(0).getString("@currencyId"));
			element.setPrice(exchangeRateService.getConversion(rates, element.getCurrency(), ebay.getConversionTo(),
					element.getPrice()));
			element.setCurrency(ebay.getConversionTo());
			element.setId((String) jsonResult.getJSONArray("itemId").get(0));
			element.setViewItemURL((String) jsonResult.getJSONArray("viewItemURL").get(0));
			element.setWebSource(ebay.getLocation());
			results.add(element);

		}
		return results;
	}

	public void ebaySearchAutomatation(Ebay ebay) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.getByUsername(userName);

		LinkedHashSet<Element> results = getEbaySearchGlobal(ebay);
		ArrayList<Element> resultsListToMail = new ArrayList<>();
		String resultId = "";
		String blacklist = getBlacklist();
		ArrayList<Long> blacklistArr = new ArrayList<>();
		if (!blacklist.equals("")) {
			for (String id : blacklist.split(",")) {
				blacklistArr.add(Long.parseLong(id));
			}
		}

		

		for (Element results2 : results) {

			// System.out.println(results2);
			boolean con = blacklistArr.contains(Long.parseLong(results2.getId()));
			if (con) {
				System.out.println(true + " " + results2.getId());

			} else {
				System.out.println(false + " " + results2.getId());
				resultId = resultId + results2.getId() + ",";
				resultsListToMail.add(results2);
				// System.out.println(resultId);

			}

		}
		// ----- Send Email -------
		if (resultsListToMail.size() > 0) {
			mailService.sendMailWithList(new NotificationEmail("Hi we have found new items for you!", user.getEmail(),
					null, resultsListToMail));
			System.out.println("Email sent");
		} else {
			System.out.println("No results this time");
		}

		// System.out.println("resultId " + resultId);
		// System.out.println("results" + results);
		blacklist = blacklist + resultId;
		if (!blacklist.equals(blacklist + resultId)) {
			System.out.println(blacklist);
			saveResultListId(blacklist);
		}

	}
	// }

	@Override
	public LinkedHashSet<Element> getMercadolibreSearch(Mercadolibre mercadolibre) throws WebsiteSearchException {

		String searchKey = mercadolibre.getKeywords().replaceAll(" ", "+");
		// String url = "https://api.mercadolibre.com/sites/" + countryCode +
		// "/search?q=" + searchKey;

		Map<String, String> idArrMap = new HashMap<>();
		idArrMap.put("MLA", "Argentina");
		idArrMap.put("MLM", "Mexico");
		idArrMap.put("MPY", "Paraguay");
		idArrMap.put("MCR", "Costa Rica");
		idArrMap.put("MBO", "Bolivia");
		idArrMap.put("MCU", "Cuba");
		idArrMap.put("MLC", "Chile");
		idArrMap.put("MSV", "El Salvador");
		idArrMap.put("MRD", "Dominicana");
		idArrMap.put("MLV", "Venezuela");
		idArrMap.put("MLB", "Brasil");
		idArrMap.put("MEC", "Ecuador");
		idArrMap.put("MLU", "Uruguay");
		idArrMap.put("MHN", "Honduras");
		idArrMap.put("MPE", "Perú");
		idArrMap.put("MPA", "Panamá");
		idArrMap.put("MNI", "Nicaragua");
		idArrMap.put("MGT", "Guatemala");
		idArrMap.put("MCO", "Colombia");

		LinkedHashSet<Element> results = new LinkedHashSet<Element>();
		int counter = 0;
		for (String countryCode : idArrMap.keySet()) {

			String url = "https://api.mercadolibre.com/sites/" + countryCode + "/search?q=" + searchKey;
			System.out.println(url);
			try {

				@SuppressWarnings("unchecked")
				Map<String, Object> objects = restTemplate.getForObject(url, Map.class);

				JSONObject root2 = new JSONObject(objects);

				JSONArray elementsArray = root2.getJSONArray("results");

				for (int i = 0; i < elementsArray.length(); i++) {
					counter++;
					Element element = new Element();
					element.setTitle(elementsArray.getJSONObject(i).getString("title"));
					element.setId(elementsArray.getJSONObject(i).getString("id"));
					element.setPrice(elementsArray.getJSONObject(i).getDouble(("price")));
					element.setCurrency(elementsArray.getJSONObject(1).getJSONObject("prices").getJSONArray("prices")
							.getJSONObject(0).getString("currency_id"));
					element.setImage(elementsArray.getJSONObject(i).getString("thumbnail"));
					element.setWebSource(idArrMap.get(countryCode));
					element.setCounter(counter);
					results.add(element);

				}

			} catch (Exception e) {
				System.out.println("Not Matches: " + idArrMap.get(countryCode));
			}

		}
		System.out.println("Number of results: " + counter);
		return results;

	}

	@Override
	public LinkedHashSet<Element> getMercadolibreSearchByLocation(Mercadolibre mercadolibre)
			throws WebsiteSearchException {

		String searchKey = mercadolibre.getKeywords().replaceAll(" ", "+");
		String countryCode = mercadolibre.getLocation();
		// String
		// url="https://listado.mercadolibre.com.uy/antiguedades-electrodomesticos-antiguos-ventiladores/ventilador-antiguo";
		// String url =
		// "https://api.mercadolibre.com/sites/MLU/search?q=Ventilador%20Antiguo";
		String url = "https://api.mercadolibre.com/sites/" + countryCode + "/search?q=" + searchKey;
		System.out.println(url);

		Map<String, String> idArrMap = new HashMap<>();
		idArrMap.put("MLA", "Argentina");
		idArrMap.put("MLM", "Mexico");
		idArrMap.put("MPY", "Paraguay");
		idArrMap.put("MCR", "Costa Rica");
		idArrMap.put("MBO", "Bolivia");
		idArrMap.put("MCU", "Cuba");
		idArrMap.put("MLC", "Chile");
		idArrMap.put("MSV", "El Salvador");
		idArrMap.put("MRD", "Dominicana");
		idArrMap.put("MLV", "Venezuela");
		idArrMap.put("MLB", "Brasil");
		idArrMap.put("MEC", "Ecuador");
		idArrMap.put("MLU", "Uruguay");
		idArrMap.put("MHN", "Honduras");
		idArrMap.put("MPE", "Perú");
		idArrMap.put("MPA", "Panamá");
		idArrMap.put("MNI", "Nicaragua");
		idArrMap.put("MGT", "Guatemala");
		idArrMap.put("MCO", "Colombia");

		url = url + searchKey;
		@SuppressWarnings("unchecked")
		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);

		JSONObject root2 = new JSONObject(objects);

		JSONArray elementsArray = root2.getJSONArray("results");

		LinkedHashSet<Element> results = new LinkedHashSet<Element>();
		for (int i = 0; i < elementsArray.length(); i++) {

			Element element = new Element();
			element.setTitle(elementsArray.getJSONObject(i).getString("title"));
			element.setId(elementsArray.getJSONObject(i).getString("id"));
			element.setPrice(elementsArray.getJSONObject(i).getDouble(("price")));
			element.setImage(elementsArray.getJSONObject(i).getString("thumbnail"));
			element.setWebSource(idArrMap.get(countryCode));
			results.add(element);
		}

		// System.out.println("Title: " + root.get("title"));

//		String rawJson = searchDAO.request(url);
//
//		JSONObject root = new JSONObject(rawJson);
//
//		JSONArray elements = root.getJSONArray("results");
		// JSONObject jsonResult = elements.getJSONObject(0);
//		JSONArray jsonArr = jsonResult.getJSONArray("searchResult");
//		jsonResult = jsonArr.getJSONObject(0);
//		jsonArr = jsonResult.getJSONArray("item");

//		for (int i = 0; i < elements.length(); i++) {
//
//			Element element = new Element();
		// System.out.println("jsonArr" + jsonArr);
		// root = elements.getJSONObject(i);
//			System.out.println("Title: " + root.get("title"));
//			System.out.println("Id: " + root.get("id"));
//			System.out.println("Price: " + root.get("price"));
//			System.out.println("URLThumbnail: " + root.get("thumbnail"));
//			System.out.println("Link: " + root.get("permalink"));
//			System.out.println("Site Location: " + root.get("site_id"));
//			System.out.println("Currency: " + root.get("currency_id"));
//			System.out.println();
//			System.out.println();
		// System.out.println(root);
		// }

		// System.out.println(jsonResult);

		return results;
	}

	@Override
	public List<Element> getkijijiItaly(String searchKey) throws WebsiteSearchException {

		searchKey = searchKey.replaceAll(" ", "+");
		String url = "https://www.kijiji.it/async/treebay/search?q=" + searchKey
				+ "&categoryId=0&locationId=0&pageNumber=1%3Cdiv%3E%3Cinput+type%3D%22hidden%22+url%3D%22L21hcmVsbGkrdmVudGlsYXRvcmUvP2VudHJ5UG9pbnQ9c2I%3D%22%3E%3C%2Fdiv%3E&pageSize=3000";
		System.out.println(url);
		// String url =
		// "https://api.couponsystemproject.com/couponSystem/api/general/getAll";
		// String url =
		// "http://svcs.ebay.com/services/search/FindingService/v1?siteid=0&SECURITY-APPNAME=orsegal-finding-PRD-e7cee19be-ec53ad7a&OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=JSON&keywords=harry%20potter%20phoenix";
		// JSONArray[] objects = restTemplate.getForObject(url, JSONArray[].class);

		@SuppressWarnings("unchecked")
		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);

		JSONArray elements;

		JSONObject root = new JSONObject(objects);
		if (root.getJSONArray("elements").isEmpty()) {
			throw new WebsiteSearchException(ErrorType.NO_MATCHES);
		} else {
			elements = root.getJSONArray("elements");
		}

		List<Element> results = new ArrayList<Element>();
		for (int i = 0; i < elements.length(); i++) {

			Element element = new Element();
			JSONObject jsonResult = elements.getJSONObject(i);

			element.setId(String.valueOf(jsonResult.getJSONObject("id").get("id")));
			element.setTitle(jsonResult.getString("title"));
			element.setPrice(jsonResult.getJSONObject("price").getLong("value"));
			element.setCurrency(jsonResult.getJSONObject("price").getString("currency"));
			element.setImage(jsonResult.getJSONArray("images").getJSONObject(0).getString("url"));
			element.setWebSource("www.kijiji.it");
			results.add(element);

		}
		System.out.println(results);
		return results;

	}

	@Override
	public JSONObject getOLXSearch(String location, String searchKey) throws WebsiteSearchException {

//		String url="";
//		switch (location) {
//		case "Argentina":
//			url = "https://www.olx.com.ar/api/relevance/search?facet_limit=100&location=1000001&location_facet_limit=20&query=ventilador%20antiguo&spellcheck=true&user=17638a5eb79x6b64cfe3";
//			break;
//			
//		case "India":
//			url = "https://www.olx.in/api/relevance/v2/search?facet_limit=100&location=1000001&location_facet_limit=20&platform=web-desktop&query=antique%20electric%20fan&spellcheck=true&user=17638e205ecx45558b5d&lang=en";
//			break;
//		default:
//			break;
//		}

		String url = "https://www.olx.com.ar/api/relevance/search?facet_limit=100&location=1000001&location_facet_limit=20&query=ventilator%20antigue&spellcheck=true&user=17638a5eb79x6b64cfe3";
		String url2 = "https://www.olx.in/api/relevance/v2/search?facet_limit=100&location=1000001&location_facet_limit=20&platform=web-desktop&query=antique%20electric%20fan&spellcheck=true&user=17638e205ecx45558b5d&lang=en";
		System.out.println(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("User-Agent", "Mozilla/5.0");
//		headers.add("Host", "LocalHost:8080");
//		headers.set("user-key", "your-password-123"); // optional - in case you auth in headers
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> objects = restTemplate.exchange(url2, HttpMethod.GET, entity, Map.class);

//		@SuppressWarnings("unchecked")
//		Map<String, Object> objects = restTemplate.getForObject(url, Map.class);
//		JSONArray elements;

		JSONObject root = new JSONObject(objects);
		System.out.println(root);
//		if (root.getJSONArray("suggested_data").isEmpty()) {
//			throw new WebsiteSearchException(ErrorType.NO_MATCHES);
//		} else {
//			elements = root.getJSONArray("suggested_data");
//		}

//		List<Element> results = new ArrayList<Element>();
//		for (int i = 0; i < elements.length(); i++) {
//
//			Element element = new Element();
//			JSONObject jsonResult = elements.getJSONObject(i);

//			element.setId(String.valueOf(jsonResult.getJSONObject("id").get("id")));
//			element.setTitle(jsonResult.getString("title"));
//			element.setPrice(jsonResult.getJSONObject("price").getLong("value"));
//			element.setCurrency(jsonResult.getJSONObject("price").getString("currency"));
//			element.setImage(jsonResult.getJSONArray("images").getJSONObject(0).getString("url"));
//			element.setWebSource("www.kijiji.it");
//			results.add(element);

		// }
		// System.out.println(results);
		return root;

	}

	@Transactional
	public void saveResultListId(String idList) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.getByUsername(userName);
		// user.setUserSearch(new UserSearch("abcd", "eBay", idList));
		if (user != null) {

			UserSearch userSearch = user.getUserSearch();
			String blacklist = userSearch.getBlacklist();
			if (blacklist.equals("")) {
				userSearch.setBlacklist(blacklist + idList);
				user.setUserSearch(userSearch);
				userRepository.save(user);
			} else {
				userSearch.setBlacklist(idList);
				user.setUserSearch(userSearch);
				userRepository.save(user);
			}

			// System.out.println(user);
		}

	}

	public String getBlacklist() {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.getByUsername(userName);

		if (user != null) {

			return user.getUserSearch().getBlacklist();
		}

		throw new WebsiteSearchException(ErrorType.NO_MATCHES);

	}
	
	public Map<String,String> ebayIdList() {
		
		Map<String, String> idArrMap = new HashMap<>();
		idArrMap.put("EBAY-AT", "eBay Austria");
		idArrMap.put("EBAY-AU", "eBay Australia");
		idArrMap.put("EBAY-CH", "eBay Switzerland");
		idArrMap.put("EBAY-DE", "eBay Germany");
		idArrMap.put("EBAY-ENCA", "eBay Canada (English)");
		idArrMap.put("EBAY-ES", "eBay Spain");
		idArrMap.put("EBAY-FR", "eBay France");
		idArrMap.put("EBAY-FRBE", "eBay Belgium (French)");
		idArrMap.put("EBAY-FRCA", "eBay Canada (French)");
		idArrMap.put("EBAY-GB", "eBay UK"); 
		idArrMap.put("EBAY-HK", "eBay Hong Kong");
		idArrMap.put("EBAY-IE", "eBay Ireland");
		idArrMap.put("EBAY-IN", "eBay India");
		idArrMap.put("EBAY-IT", "eBay Italy");
		idArrMap.put("EBAY-MY", "eBay Malaysia");
		idArrMap.put("EBAY-NL", "eBay Netherlands");
		idArrMap.put("EBAY-NLBE", "eBay Belgium (Dutch)");
		idArrMap.put("EBAY-PH", "eBay Philippines");
		idArrMap.put("EBAY-PL", "eBay Poland");
		idArrMap.put("EBAY-SG", "eBay Singapore");
		idArrMap.put("EBAY-US", "eBay United States");
		System.out.println("idArrMap created");
		return idArrMap;
	}

}
