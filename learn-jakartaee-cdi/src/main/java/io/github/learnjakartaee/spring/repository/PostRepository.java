package io.github.learnjakartaee.spring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.learnjakartaee.shared.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Repository
public class PostRepository {

	// Why does @PersistenceContext not work?
	// @PersistenceContext
	@Autowired
	EntityManager entityManager;

	public List<Post> findAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);
		// Root<Post> posts =
		cq.from(Post.class);
		TypedQuery<Post> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public void publishPost(Post newPost) {
		entityManager.persist(newPost);
	}
}
