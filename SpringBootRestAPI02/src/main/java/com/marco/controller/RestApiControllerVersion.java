package com.marco.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marco.model.User;
import com.marco.service.UserService;
import com.marco.util.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/apiVersion")
@Api(tags= {"RestApiControllerVersion"})
public class RestApiControllerVersion {

	public static final Logger logger = LoggerFactory.getLogger(RestApiControllerVersion.class);

	@Autowired
	private UserService userService;

		
//	@RequestMapping(value = "/user/", method = RequestMethod.GET, produces="application/json")
	 @GetMapping({"/v1.0", "/v1.1", "/v1.2"})
	@ApiOperation(value="Search a list of users", notes="Without params", response=User.class, responseContainer="List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=404, message="The resource not found"),			
			})
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

//	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces="application/json")
	@GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
	@ApiOperation(value="Search a user by Id", notes="id with identify of user", response=User.class)
	@ApiResponses(value= {
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=404, message="The resource not found"),			
			})
	public @ResponseBody ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/v1.2/{id}")
	@ApiOperation(value="Search a user by Id", notes="id with identify of user", response=User.class)
	@ApiResponses(value= {
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=404, message="The resource not found"),			
			})
	public @ResponseBody ResponseEntity<?> getUser2(@PathVariable("id") long id) {
		return getUser(id);
	}

	
	@PostMapping({"/v1.0", "/v1.1"})
	//@RequestMapping(value = "/user/")
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Unable to create. A User with name " + user.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	
	@PostMapping("/v1.2")
	//@RequestMapping(value = "/user/")
	public ResponseEntity<?> createUser2(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		return createUser(user, ucBuilder);
	}

	@PutMapping("/v1.0")
	@Deprecated
	//@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	@PutMapping({"/v1.1/{id}", "/v1.2/{id}"})
	//@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser2(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	@DeleteMapping({"/v1.0/{id}", "/v1.1/{id}", "/v1.2/{id}"})
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping({"/v1.0", "/v1.1", "/v1.2"})
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
	

}
