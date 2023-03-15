package io.github.learnjakartaee.test;

import java.io.PrintStream;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class EjbJarBuilder {

	private JavaArchive archive;

	public EjbJarBuilder(String name) {
		this.archive = ShrinkWrap.create(JavaArchive.class, name);
	}

	public EjbJarBuilder packages(String... packages) {
		this.archive = archive.addPackages(true, packages);
		return this;
	}

	public JavaArchive build(boolean displayContents) {
		return displayContents ? build(System.out) : build();
	}

	public JavaArchive build(PrintStream out) {
		out.println(archive.toString(true));
		return build();
	}

	public JavaArchive build() {
		return archive;
	}
}
