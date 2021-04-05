package com.websiteSearch.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.websiteSearch.entity.Ebay;
import com.websiteSearch.entity.Element;
import com.websiteSearch.entity.Mercadolibre;
import com.websiteSearch.exceptionHander.WebsiteSearchException;
import com.websiteSearch.service.WebSearchService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
@RequestMapping("/search")
public class WebsiteSearchRestController {

	@Autowired
	private WebSearchService webSearchService;



	@PostMapping("/ebaySearchAutomatation")
	public void ebaySearchAutomatation(@RequestBody Ebay ebay) throws WebsiteSearchException {

		// webSearchService.test(list);
		webSearchService.ebaySearchAutomatation(ebay);
	}

	@PostMapping("/ebaySearchTest")
	public LinkedHashSet<Element> ebaySearchTest(@RequestBody Ebay ebay) throws WebsiteSearchException {
		LinkedHashSet<Element> results = new LinkedHashSet<>();

		System.out.println(ebay);
		if (ebay.getLocation().equals("EBAY-GLOBAL")) {
			results = getEbaySearchGlobal(ebay);
			System.out.println("All");
		} else {
			try {
				results = getEbaySearchByLocation(ebay);
				System.out.println("getEbaySearchByLocation");
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return results;
	}

	@PostMapping("/mercadolibreSearchTest")
	public LinkedHashSet<Element> mercadolibreSearchTest(@RequestBody Mercadolibre mercadolibre)
			throws WebsiteSearchException {
		LinkedHashSet<Element> results = new LinkedHashSet<>();

		System.out.println(mercadolibre);
		if (mercadolibre.getLocation().equals("Mercadolibre-GLOBAL")) {
			results = getMercadolibreSearch(mercadolibre);
			System.out.println("All");
		} else {
			try {
				results = getMercadolibreSearchByLoaction(mercadolibre);
				System.out.println("getMercadolibreSearchByLoaction");
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return results;
	}

	@GetMapping("/kijijiItaly/{searchKey}")
	public List<Element> getItalianWeb(@PathVariable String searchKey) throws WebsiteSearchException {

		return webSearchService.getkijijiItaly(searchKey);
	}

	@GetMapping("/ebayGlobal")
	public LinkedHashSet<Element> getEbaySearchGlobal(Ebay ebay) throws WebsiteSearchException {

		return webSearchService.getEbaySearchGlobal(ebay);
	}

	@GetMapping("/ebayByLocation")
	public LinkedHashSet<Element> getEbaySearchByLocation(Ebay ebay) throws WebsiteSearchException {

		return webSearchService.getEbaySearchByLocation(ebay);
	}

	@GetMapping("/mercadolibre")
	public LinkedHashSet<Element> getMercadolibreSearchByLoaction(@RequestBody Mercadolibre mercadolibre)
			throws WebsiteSearchException {

		return webSearchService.getMercadolibreSearchByLocation(mercadolibre);
	}

	@PostMapping("/mercadolibreTotal")
	public LinkedHashSet<Element> getMercadolibreSearch(@RequestBody Mercadolibre mercadolibre)
			throws WebsiteSearchException {

		return webSearchService.getMercadolibreSearch(mercadolibre);
	}

	@GetMapping("/OLX/{location}/{searchKey}")
	public JSONObject getOLXSearch(@PathVariable String location, @PathVariable String searchKey)
			throws WebsiteSearchException {

		return webSearchService.getOLXSearch(location, searchKey);
	}
	
	
//	@PostMapping("/test")
//	public LinkedHashSet<Element> test(@RequestBody Ebay ebay)
//			throws WebsiteSearchException, InterruptedException, ExecutionException {
//			
//		CompletableFuture<LinkedHashSet<Element>> future1 = webSearchService.test(ebay, "EBAY-AT");
//		CompletableFuture<LinkedHashSet<Element>> future2 = webSearchService.test(ebay, "EBAY-AU");
//		CompletableFuture<LinkedHashSet<Element>> future3 = webSearchService.test(ebay, "EBAY-CH");
//		CompletableFuture<LinkedHashSet<Element>> future4 = webSearchService.test(ebay, "EBAY-DE");
//		CompletableFuture<LinkedHashSet<Element>> future5 = webSearchService.test(ebay, "EBAY-ENCA");
//		CompletableFuture<LinkedHashSet<Element>> future6 = webSearchService.test(ebay, "EBAY-ES");
//		CompletableFuture<LinkedHashSet<Element>> future7 = webSearchService.test(ebay, "EBAY-FR");
//		CompletableFuture<LinkedHashSet<Element>> future8 = webSearchService.test(ebay, "EBAY-FRBE");
//		CompletableFuture<LinkedHashSet<Element>> future9 = webSearchService.test(ebay, "EBAY-FRCA");
//		CompletableFuture<LinkedHashSet<Element>> future10 = webSearchService.test(ebay, "EBAY-GB");
//		CompletableFuture<LinkedHashSet<Element>> future11 = webSearchService.test(ebay, "EBAY-HK");
//		CompletableFuture<LinkedHashSet<Element>> future12 = webSearchService.test(ebay, "EBAY-IE");
//		CompletableFuture<LinkedHashSet<Element>> future13 = webSearchService.test(ebay, "EBAY-IN");
//		CompletableFuture<LinkedHashSet<Element>> future14 = webSearchService.test(ebay, "EBAY-IT");
//		CompletableFuture<LinkedHashSet<Element>> future15 = webSearchService.test(ebay, "EBAY-MY");
//		CompletableFuture<LinkedHashSet<Element>> future16 = webSearchService.test(ebay, "EBAY-NL");
//		CompletableFuture<LinkedHashSet<Element>> future17 = webSearchService.test(ebay, "EBAY-NLBE");
//		CompletableFuture<LinkedHashSet<Element>> future18 = webSearchService.test(ebay, "EBAY-PH");
//		CompletableFuture<LinkedHashSet<Element>> future19 = webSearchService.test(ebay, "EBAY-PL");
//		CompletableFuture<LinkedHashSet<Element>> future20 = webSearchService.test(ebay, "EBAY-SG");
//		CompletableFuture<LinkedHashSet<Element>> future21 = webSearchService.test(ebay, "EBAY-US");
//		
//		CompletableFuture.allOf(future1, future2, future3,future4,future5,future6,future7,future8,future9,future10
//				,future11,future12,future13,future14,future15,future16,future17,future18,future19,future20,future21).join();
//		
//		LinkedHashSet<Element> arr = new LinkedHashSet<>();
//		arr.addAll(future1.get());
//		arr.addAll(future2.get());
//		arr.addAll(future3.get());
//		arr.addAll(future4.get());
//		arr.addAll(future5.get());
//		arr.addAll(future6.get());
//		arr.addAll(future7.get());
//		arr.addAll(future8.get());
//		arr.addAll(future9.get());
//		arr.addAll(future10.get());
//		arr.addAll(future11.get());
//		arr.addAll(future12.get());
//		arr.addAll(future13.get());
//		arr.addAll(future14.get());
//		arr.addAll(future15.get());
//		arr.addAll(future16.get());
//		arr.addAll(future17.get());
//		arr.addAll(future18.get());
//		arr.addAll(future19.get());
//		arr.addAll(future20.get());
//		arr.addAll(future21.get());
//		
//		return arr;
//	}

}
