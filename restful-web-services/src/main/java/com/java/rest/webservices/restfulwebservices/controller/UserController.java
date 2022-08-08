package com.java.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.java.rest.webservices.restfulwebservices.model.Post;
import com.java.rest.webservices.restfulwebservices.model.User;
import com.java.rest.webservices.restfulwebservices.service.PostService;
import com.java.rest.webservices.restfulwebservices.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		System.out.println(user.toString());
		User savedUser = userService.saveUser(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public EntityModel<User> getUser(@PathVariable Integer id) {
		User user = userService.getUser(id);
		EntityModel<User> userEntity = EntityModel.of(user);

		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
		userEntity.add(linkTo.withRel("all-users"));

		return userEntity;
	}

	@GetMapping()
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return "User Deleted";
	}

	@PostMapping("/{userId}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer userId, @RequestBody Post post) {

		User user = userService.getUser(userId);

		post.setUser(user);

		postService.savePost(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{userId}/posts")
	public List<Post> getPosts(@PathVariable Integer userId) {
		User user = userService.getUser(userId);

		return user.getPosts();

	}

}
