package com.java.rest.webservices.restfulwebservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.java.rest.webservices.restfulwebservices.model.User;
import com.java.rest.webservices.restfulwebservices.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {

		return userRepository.save(user);

	}

	public User getUser(Integer id) {
		User user = userRepository.findById(id).orElse(null);

		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}

		return user;
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(Integer id) {

		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("id-" + id);
		}

		userRepository.deleteById(id);
	}

}
