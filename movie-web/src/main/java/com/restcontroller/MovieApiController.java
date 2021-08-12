package com.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



@RestController
@RequestMapping(path = "/movie")
public class MovieApiController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@GetMapping("/check")
	public void checkClient() throws RestClientException, IOException{
		List<ServiceInstance> ls = discoveryClient.getInstances("MOVIE-SERVICE-EUREKA-CLIENT");
		System.out.println(ls.get(0).getUri());
		restTemplate.exchange(ls.get(0).getUri() + "/movie/top/3",
				HttpMethod.GET,getHeaders(),ArrayList.class).getBody();
	}
	
	private static HttpEntity<?> getHeaders() throws IOException{
		HttpHeaders headers = new HttpHeaders();
		return new HttpEntity<>(headers);
	}
}
