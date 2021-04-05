package com.websiteSearch.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SearchDao {

	public String request(String endpoint) throws Exception {
		StringBuilder sb = new StringBuilder();

		URL url = new URL(endpoint);

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

//		Map<String, String> headers = new HashMap<>();
//
//		headers.put("Accept", "application/json");
//		headers.put("Content-type", "application/json");
//
//		for (String headerKey : headers.keySet()) {
//			urlConnection.setRequestProperty(headerKey, headers.get(headerKey));
//		}

		try {

			InputStream inputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

			// read them as characters.
			InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			// read one line at a time.
			String inputLine = bufferedReader.readLine();
			while (inputLine != null) {
				// add this to our output
				sb.append(inputLine);
				// reading the next line
				inputLine = bufferedReader.readLine();
			}
		} finally {
			urlConnection.disconnect();
		}
		return sb.toString();

	}

}
