package io.github.learnjakartaee.shared.model;

import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class Cat implements Animal {

	@Override
	public String speak() {
		return "Meow";
	}

}
