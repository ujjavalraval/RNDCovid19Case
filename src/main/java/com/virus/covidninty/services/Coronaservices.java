//Its service for create you can get data when application launch
//we need get data and parse itv
package com.virus.covidninty.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.virus.covidninty.model.LocationState;

@Service 
public class Coronaservices {
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	
	//create a method for a get data from the URL when application will be loded 
	//now set that request  to HTTP
	
	private List <LocationState> allStates = new ArrayList<>();
	
	//@PostConstruct hey spring boot load this service and execute this method when application loded 
	//Scheduled() its update method for a daily basic and some periodic basic
	//Hy Scheduled run this method for a every secand its can becuse of cron
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	private void fetchVirusData() throws IOException,InterruptedException {
		
		//
		List <LocationState> newStates = new ArrayList<>();
		
			
		
		//create new http client and call the HTTP
		HttpClient client = HttpClient.newHttpClient();
	
		//create request using Builder pattern 
		//need convert string to the uri 
		HttpRequest request =  HttpRequest.newBuilder()
					.uri(URI.create(VIRUS_DATA_URL))
					.build();	
				
		//send a responce get back to the client
	HttpResponse<String>  httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
	
	 //print out this output 
	//System.out.println(httpResponse.body());
	
	//instance of reader parse string  
	StringReader csvBodyReader = new StringReader(httpResponse.body());
	
	
	Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
/**	for (CSVRecord record : records) {
	    String state = record.get("Province/State");
	    System.out.println(state);
//	    String customerNo = record.get("CustomerNo");
//	    String name = record.get("Name");
	} */
	
	for (CSVRecord record : records) {
		LocationState locationState = new LocationState();
		locationState.setState(record.get("Province/State")); 
		locationState.setCountry(record.get("Country/Region"));
		locationState.setLatestTotalCases((Integer.parseInt(record.get(record.size()-1))));
		System.out.println(locationState);
		newStates.add(locationState);
	}
	
		this.allStates = newStates;
		
	}

	public List<LocationState> getAllStates() {
		return allStates;
	}


}
