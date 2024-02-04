package io.github.learnjakartaee.shared.model;

import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class Cow implements Animal {

	@Override
	public String speak() {
		return "Moo";
	}

}
