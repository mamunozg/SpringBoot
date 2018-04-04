package com.marco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marco.model.Tweet;
import com.marco.repository.TweetRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TweetController {

	private TweetRepository tweetRepository;

	@Autowired
	public TweetController(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}
	
	@GetMapping("/tweets")
	public Flux<Tweet> getAllTweets() {
		return tweetRepository.findAll();		
	}
	
	
	@PostMapping("/tweets")
	public Mono<Tweet> createTweet(@Valid @RequestBody Tweet tweet) {		
		return tweetRepository.save(tweet);
	}
	
	@GetMapping("/tweets/{id}")
	public Mono<ResponseEntity<Tweet>> getTweetById(@PathVariable(value="id") String tweetId) {		
		return tweetRepository.findById(tweetId)
				.map(savedTweet -> ResponseEntity.ok(savedTweet))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/tweets/{id}")
	public Mono<ResponseEntity<Tweet>> updateTweet(@PathVariable(value="id") String tweetId, @Valid @RequestBody Tweet tweet) {
	
		return tweetRepository.findById(tweetId)
				.flatMap(existingTweet ->{
					existingTweet.setText(tweet.getText());
					return tweetRepository.save(existingTweet);
				})
				.map(updateTweet -> new ResponseEntity<>(updateTweet, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.OK));
		
	}
	
	@DeleteMapping("/tweets/{id}")
	public Mono<ResponseEntity<Void>> deleteTweet(@PathVariable(value="id") String tweetId) {
	
		return tweetRepository.findById(tweetId)
				.flatMap(existingTweet -> 
					tweetRepository.delete(existingTweet)
						.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@GetMapping(value="/stream/tweets", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tweet> streamAllTweets() {
		return tweetRepository.findAll();		
	}
}
