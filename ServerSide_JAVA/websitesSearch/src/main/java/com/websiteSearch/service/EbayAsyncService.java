package com.websiteSearch.service;

import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.websiteSearch.entity.Ebay;
import com.websiteSearch.entity.Element;
import com.websiteSearch.exceptionHander.WebsiteSearchException;
import com.websiteSearch.repository.UserRepository;

@Service
public class EbayAsyncService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private WebSearchService webSearchService;

	@Async
	public LinkedHashSet<Element> getEbaySearchGlobalTest(Ebay ebay)
			throws WebsiteSearchException, InterruptedException, ExecutionException {

		CompletableFuture<LinkedHashSet<Element>> future1 = webSearchService.test(ebay, "EBAY-AT");
		CompletableFuture<LinkedHashSet<Element>> future2 = webSearchService.test(ebay, "EBAY-AU");
		CompletableFuture<LinkedHashSet<Element>> future3 = webSearchService.test(ebay, "EBAY-CH");
		CompletableFuture<LinkedHashSet<Element>> future4 = webSearchService.test(ebay, "EBAY-DE");
		CompletableFuture<LinkedHashSet<Element>> future5 = webSearchService.test(ebay, "EBAY-ENCA");
		CompletableFuture<LinkedHashSet<Element>> future6 = webSearchService.test(ebay, "EBAY-ES");
		CompletableFuture<LinkedHashSet<Element>> future7 = webSearchService.test(ebay, "EBAY-FR");
		CompletableFuture<LinkedHashSet<Element>> future8 = webSearchService.test(ebay, "EBAY-FRBE");
		CompletableFuture<LinkedHashSet<Element>> future9 = webSearchService.test(ebay, "EBAY-FRCA");
		CompletableFuture<LinkedHashSet<Element>> future10 = webSearchService.test(ebay, "EBAY-GB");
		CompletableFuture<LinkedHashSet<Element>> future11 = webSearchService.test(ebay, "EBAY-HK");
		CompletableFuture<LinkedHashSet<Element>> future12 = webSearchService.test(ebay, "EBAY-IE");
		CompletableFuture<LinkedHashSet<Element>> future13 = webSearchService.test(ebay, "EBAY-IN");
		CompletableFuture<LinkedHashSet<Element>> future14 = webSearchService.test(ebay, "EBAY-IT");
		CompletableFuture<LinkedHashSet<Element>> future15 = webSearchService.test(ebay, "EBAY-MY");
		CompletableFuture<LinkedHashSet<Element>> future16 = webSearchService.test(ebay, "EBAY-NL");
		CompletableFuture<LinkedHashSet<Element>> future17 = webSearchService.test(ebay, "EBAY-NLBE");
		CompletableFuture<LinkedHashSet<Element>> future18 = webSearchService.test(ebay, "EBAY-PH");
		CompletableFuture<LinkedHashSet<Element>> future19 = webSearchService.test(ebay, "EBAY-PL");
		CompletableFuture<LinkedHashSet<Element>> future20 = webSearchService.test(ebay, "EBAY-SG");
		CompletableFuture<LinkedHashSet<Element>> future21 = webSearchService.test(ebay, "EBAY-US");

//		CompletableFuture<List<String>> future2  EBAY-GB EBAY-AU EBAY-US
//		  = CompletableFuture.supplyAsync(() -> webSearchService.test());
//		CompletableFuture<List<String>> future3  
//		  = CompletableFuture.supplyAsync(() -> webSearchService.test());
//	

		CompletableFuture.allOf(future1, future2, future3, future4, future5, future6, future7, future8, future9,
				future10, future11, future12, future13, future14, future15, future16, future17, future18, future19,
				future20, future21).join();

		// ...

//		String combined = Stream.of(future1, future2, future3)
//				  .map(CompletableFuture::join).collect(Collectors.joining(" "));

		// combinedFuture.get();
//		System.out.println(future1.get());
//		System.out.println(future2.get());
//		System.out.println(future3.get());

		LinkedHashSet<Element> arr = new LinkedHashSet<>();
		arr.addAll(future1.get());
		arr.addAll(future2.get());
		arr.addAll(future3.get());
		arr.addAll(future4.get());
		arr.addAll(future5.get());
		arr.addAll(future6.get());
		arr.addAll(future7.get());
		arr.addAll(future8.get());
		arr.addAll(future9.get());
		arr.addAll(future10.get());
		arr.addAll(future11.get());
		arr.addAll(future12.get());
		arr.addAll(future13.get());
		arr.addAll(future14.get());
		arr.addAll(future15.get());
		arr.addAll(future16.get());
		arr.addAll(future17.get());
		arr.addAll(future18.get());
		arr.addAll(future19.get());
		arr.addAll(future20.get());
		arr.addAll(future21.get());
		System.out.println(arr);
		return arr;

	}
}
