package io.github.learnjakartaee.test;

import java.io.File;
import java.io.PrintStream;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class WebAppWarBuilder {

	private WebArchive archive;

	public WebAppWarBuilder(String name) {
		this.archive = ShrinkWrap.create(WebArchive.class, name);
	}

	public WebAppWarBuilder packages(String... packages) {
		this.archive = archive.addPackages(true, packages);
		return this;
	}

	public WebAppWarBuilder beansXml() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
		return this;
	}

	public WebAppWarBuilder beansXmlForTesting() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans-test.xml"), "beans.xml");
		return this;
	}

	public WebAppWarBuilder beansXmlPlaceHolder() {
		this.archive = archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return this;
	}

	public WebAppWarBuilder webXml() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
		return this;
	}

	public WebAppWarBuilder persistenceXml() {
		this.archive = archive.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
		return this;
	}

	public WebAppWarBuilder jsps() {
		return addFiles("src/main/webapp", ".*\\.jsp$");
	}

	public WebAppWarBuilder htmls() {
		return addFiles("src/main/webapp", ".*\\.html$");
	}

	public WebAppWarBuilder xhtmls() {
		return addFiles("src/main/webapp", ".*\\.xhtml$");
	}

	public WebAppWarBuilder css() {
		return addFiles("src/main/webapp", ".*\\.css$");
	}

	public WebAppWarBuilder xmls() {
		return addFiles("src/main/webapp", ".*\\.xml$");
	}

	public WebAppWarBuilder tags() {
		return addFiles("src/main/webapp", ".*\\.tag$");
	}

	public WebAppWarBuilder tlds() {
		return addFiles("src/main/webapp", ".*\\.tld$");
	}

	public WebAppWarBuilder tomeeResources() {
		this.archive = archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/resources.xml"));
		return this;
	}

	public WebAppWarBuilder mavenDependencies() {
		File[] dependencies = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.importCompileAndRuntimeDependencies()
				.resolve()
				.withTransitivity()
				.asFile();
		archive = archive.addAsLibraries(dependencies);
		return this;
	}

	private WebAppWarBuilder addFiles(String directory, String filter) {
		return addFiles(directory, filter, "/");
	}

	private WebAppWarBuilder addFiles(String directory, String filter, String target) {
		archive = archive
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class)
						.importDirectory(directory)
						.as(GenericArchive.class), target, Filters.include(filter));
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