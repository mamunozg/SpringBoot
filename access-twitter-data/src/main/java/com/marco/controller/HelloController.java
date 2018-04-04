package com.marco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

	private Twitter twitter;
	
	private ConnectionRepository connectionRepository;

	@Autowired
	public HelloController(Twitter twitter, ConnectionRepository connectionRepository) {
		this.twitter = twitter;
		this.connectionRepository = connectionRepository;
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String helloTwitter(Model model) {
	
		if(connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			
			return "redirect:/connect/twitter";
			
		}
		
		model.addAttribute(twitter.userOperations().getUserProfile());
		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
		model.addAttribute("friends", friends);
		return "hello";
		
 	}
	
}
