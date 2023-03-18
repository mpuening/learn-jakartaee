package io.github.learnjakartaee.test;

import java.io.File;
import java.io.PrintStream;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class RestApiWarBuilder {

	private WebArchive archive;

	public RestApiWarBuilder(String name) {
		this.archive = ShrinkWrap.create(WebArchive.class, name);
	}

	public RestApiWarBuilder packages(String... packages) {
		this.archive = archive.addPackages(true, packages);
		return this;
	}

	public RestApiWarBuilder beansXml() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
		return this;
	}

	public RestApiWarBuilder webXml() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
		return this;
	}

	public WebArchive build(boolean displayContents) {
		return displayContents ? build(System.out) : build();
	}

	public WebArchive build(PrintStream out) {
		out.println(archive.toString(true));
		return build();
	}

	public WebArchive build() {
		return archive;
	}
}
