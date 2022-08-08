package com.java.rest.webservices.restfulwebservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.java.rest.webservices.restfulwebservices.model.Post;
import com.java.rest.webservices.restfulwebservices.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post savePost(Post post) {

		return postRepository.save(post);

	}

	public Post getPost(Integer id) {
		Post post = postRepository.findById(id).orElse(null);

		if (post == null) {
			throw new PostNotFoundException("id-" + id);
		}

		return post;
	}

	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	public void deletePost(Integer id) {

		if (!postRepository.existsById(id)) {
			throw new PostNotFoundException("id-" + id);
		}

		postRepository.deleteById(id);
	}

}
