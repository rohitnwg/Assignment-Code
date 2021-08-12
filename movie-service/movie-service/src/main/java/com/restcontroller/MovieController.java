package com.restcontroller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.MovieModel;
import com.service.MovieService;


@RestController
@RequestMapping(path = "/movie")
public class MovieController {
	Logger logger = LogManager.getLogger(MovieService.class);
	
	@Autowired
	private MovieService service;
	
	
	@GetMapping(path = "/wonoscur/{movieTitle}")
	public boolean wonOscur(@PathVariable String movieTitle) {
		logger.debug("Inside wonOscur");
		
		Optional<MovieModel> model = service.findByTitle(movieTitle);
		if(model.isPresent()) {
			return model.get().isWon();
		}
		return false;
	}
	@GetMapping(path ="/top/{count}")
	List<String> getTop10Movies(@PathVariable long count){
		logger.debug("Inside getTop10Movies");
		List<MovieModel> ls = service.getAllMovies();
		return ls.stream().sorted((m1,m2) -> Double.compare(m2.getRating(), m1.getRating())).limit(count)
				.sorted((m1,m2)-> m2.getBoxOfficeValue().compareTo(m1.getBoxOfficeValue()))
				.map(MovieModel::getAdditionalInfo).collect(Collectors.toList());
	}
	
	
	@PutMapping(path = "update-rating/{movieTitle}/{rating}")
	ResponseEntity<String> updateRating(@PathVariable int rating, @PathVariable String movieTitle){
		logger.debug("Inside updateRating");
		service.updateRating(rating, movieTitle);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
}
