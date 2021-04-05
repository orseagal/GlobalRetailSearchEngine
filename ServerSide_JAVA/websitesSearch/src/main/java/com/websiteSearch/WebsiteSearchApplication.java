package com.websiteSearch;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
//import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.websiteSearch.config.SwaggerConfiguration;


@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class WebsiteSearchApplication {

//	@Bean
//	public RestTemplate getRestTemplte(){
//		return new RestTemplate();	
//	}
	
//	@Bean(name = "fastJsonRestTemplate")
//	public RestTemplate fastJsonRestTemplate() {
//	    RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
//
//	    HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//
//	    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//	    List<MediaType> fastMediaTypes = new ArrayList<>();
//	    fastMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//	    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//	    fastConverter.setSupportedMediaTypes(fastMediaTypes);
//	    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
//	    converters.add(1,converter);
//	    converters.add(fastConverter);
//	    return restTemplate;
//	}
	
	@Bean
	public RestTemplate getRestTemplte() {
		HttpHost host = new HttpHost("localhost", 8080, "http");
		   final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
	   List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
  
		   MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		   converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		   messageConverters.add(converter);
		   restTemplate.setMessageConverters(messageConverters);

		   return restTemplate;
		}
	
	public class HttpComponentsClientHttpRequestFactoryBasicAuth 
	  extends HttpComponentsClientHttpRequestFactory {
		
	    HttpHost host;

	    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host) {
	        super();
	        this.host = host;
	    }

	    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
	        return createHttpContext();
	    }
	    
	    private HttpContext createHttpContext() {
	        AuthCache authCache = new BasicAuthCache();

	        BasicScheme basicAuth = new BasicScheme();
	        authCache.put(host, basicAuth);

	        BasicHttpContext localcontext = new BasicHttpContext();
	        localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
	        return localcontext;
	    }
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(WebsiteSearchApplication.class, args);
	}

}
