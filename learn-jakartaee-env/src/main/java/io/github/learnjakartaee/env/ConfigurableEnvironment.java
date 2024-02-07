package io.github.learnjakartaee.env;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to support evaluating expressions in properties.
 */
public class ConfigurableEnvironment implements Environment, ExpressionEvaluator {

	protected final Logger LOG = LoggerFactory.getLogger(ConfigurableEnvironment.class);

	public static final String EXPRESSION_PREFIX_DEFAULT = "EVAL(";
	public static final String EXPRESSION_SUFFIX_DEFAULT = ")";

	protected final String expressionPrefix;
	protected final String expressionSuffix;

	protected final List<Properties> properties = new ArrayList<Properties>();

	protected final ExpressionEvaluator evaluator;

	public ConfigurableEnvironment(String[] baseFilenames, ExpressionEvaluator evaluator, String expressionPrefix, String expressionSuffix) {
		String activeProfiles = Environment.getActiveProfiles();
		
		ClassLoader classLoader = this.getClass().getClassLoader();

		LOG.info("CURRENT ACTIVE PROFILES: " + activeProfiles);

		for (String baseFilename : baseFilenames) {
			Arrays.asList(activeProfiles.split(",")).stream().map(s -> s.trim()).forEach(profile -> {
				// We are only supporting files on the classpath
				String filename = String.format("%s-%s.properties", baseFilename, profile);
				readProperties(classLoader, filename);
			});
			// Default values will be in this file
			readProperties(classLoader, baseFilename + ".properties");
		}

		this.evaluator = evaluator;
		this.expressionPrefix = expressionPrefix;
		this.expressionSuffix = expressionSuffix;
	}

	public ConfigurableEnvironment(String[] baseFilenames, ExpressionEvaluator evaluator) {
		this(baseFilenames, evaluator, EXPRESSION_PREFIX_DEFAULT, EXPRESSION_SUFFIX_DEFAULT);
	}

	public ConfigurableEnvironment(ExpressionEvaluator evaluator, String expressionPrefix, String expressionSuffix) {
		this(new String[] { "application" }, evaluator, EXPRESSION_PREFIX_DEFAULT, EXPRESSION_SUFFIX_DEFAULT);
	}

	public ConfigurableEnvironment(ExpressionEvaluator evaluator) {
		this(new String[] { "application" }, evaluator, EXPRESSION_PREFIX_DEFAULT, EXPRESSION_SUFFIX_DEFAULT);
	}

	protected void readProperties(ClassLoader classLoader, String filename) {
		try (InputStream input = classLoader.getResourceAsStream(filename)) {
			if (input != null) {
				LOG.info("LOADING PROFILE PROPERTIES FILE: " + filename);
				Properties profileProperties = new Properties();
				profileProperties.load(input);
				properties.add(profileProperties);
			} else {
				LOG.info("MISSING PROFILE PROPERTIES FILE: " + filename);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param description  Human-readable name of property (good for debugging)
	 * @param key          Property key / name
	 * @param defaultValue
	 */
	@Override
	public String getProperty(String description, String key, String defaultValue) {
		String propertyValue = properties.stream().filter(p -> p.containsKey(key)).map(p -> String.valueOf(p.get(key)))
				.findFirst().orElse(defaultValue);
		return (propertyValue != null && isExpression(propertyValue))
				? evaluateExpression(description, peelExpression(propertyValue))
				: propertyValue;
	}

	protected boolean isExpression(String propertyValue) {
		return propertyValue.startsWith(this.expressionPrefix) && propertyValue.endsWith(expressionSuffix);
	}

	protected String peelExpression(String propertyValue) {
		return propertyValue.substring(this.expressionPrefix.length(),
				propertyValue.length() - expressionSuffix.length());
	}

	@Override
	public String evaluateExpression(String description, String expression) {
		return this.evaluator.evaluateExpression(description, expression);
	}
}
