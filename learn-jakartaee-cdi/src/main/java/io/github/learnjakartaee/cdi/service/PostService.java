package io.github.learnjakartaee.cdi.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.github.learnjakartaee.cdi.repository.PostRepository;
import io.github.learnjakartaee.shared.model.Post;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class PostService {

	@Inject
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
		postRepository.save(newPost);
	}
}
