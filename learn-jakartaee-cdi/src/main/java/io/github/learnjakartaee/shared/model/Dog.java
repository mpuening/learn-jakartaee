package io.github.learnjakartaee.shared.model;

import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class Dog implements Animal {

	@Override
	public String speak() {
		return "Bow-wow";
	}

}
