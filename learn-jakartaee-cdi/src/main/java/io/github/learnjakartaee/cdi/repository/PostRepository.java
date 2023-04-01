package io.github.learnjakartaee.cdi.repository;

import java.util.List;

import io.github.learnjakartaee.shared.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class PostRepository {

	@PersistenceContext
	EntityManager entityManager;

	public List<Post> findAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);
		// Root<Post> posts
		cq.from(Post.class);
		TypedQuery<Post> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public void publishPost(Post newPost) {
		entityManager.persist(newPost);
	}

}
