package io.github.learnjakartaee.spring.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.learnjakartaee.shared.model.Post;
import io.github.learnjakartaee.spring.repository.PostRepository;
import jakarta.transaction.Transactional;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	public String getPostListing() {
		AtomicInteger index = new AtomicInteger();
		return postRepository.findAll().stream()
				.map(post -> String.valueOf(index.incrementAndGet()) + " " + post.getMessage())
				.collect(Collectors.joining("\n"));
	}

	@Transactional
	public void publishPost(String message) {
		Post newPost = new Post();
		newPost.setMessage(message);
		newPost.setPublishDate(LocalDate.now());
		postRepository.publishPost(newPost);
	}
}
