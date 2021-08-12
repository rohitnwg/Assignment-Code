package com.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.MovieModel;
import com.repository.MovieRepoInterface;

@Service
public class MovieService {
	Logger logger = LogManager.getLogger(MovieService.class);
	
	@Autowired
	private MovieRepoInterface movieRepo;
	
	public Optional<MovieModel> findByTitle(String movieTitle){
		logger.debug("Inside findByTitle");
		
		return getAllMovies().stream().filter(m -> m.getAdditionalInfo().equalsIgnoreCase(movieTitle)).findFirst();
		
	}
	
	public List<MovieModel> getAllMovies(){
		logger.debug("Inside getAllMovies");
		return movieRepo.findAll();
	}
	
	public void updateRating(int rating, String movieTitle) {
		logger.debug("Inside updateRating");
		double avgRating = 0.0;
		 Optional<MovieModel> m = findByTitle(movieTitle);
		 if(m.isPresent()) {
			 avgRating = (m.get().getRating() + rating )/2;
		 } else {
			 avgRating = rating;
		 }
		 m.get().setRating(avgRating);
		 movieRepo.save( m.get());
		 
	}
}
