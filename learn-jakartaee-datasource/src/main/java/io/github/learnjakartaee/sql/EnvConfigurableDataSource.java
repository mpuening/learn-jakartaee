package io.github.learnjakartaee.sql;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.env.ExpressionEvaluator;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment, or evaluate the value
 * as an expression.
 */
public class EnvConfigurableDataSource extends ConfigurableDataSource {

	public static final String ENV_PREFIX_DEFAULT = "ENV(";
	public static final String ENV_SUFFIX_DEFAULT = ")";

	public static final String EXPRESSION_PREFIX_DEFAULT = "EVAL(";
	public static final String EXPRESSION_SUFFIX_DEFAULT = ")";

	protected final String envPrefix;
	protected final String envSuffix;

	protected final String expressionPrefix;
	protected final String expressionSuffix;

	private final Environment environment;
	private final ExpressionEvaluator evaluator;

	public EnvConfigurableDataSource(Environment environment, ExpressionEvaluator evaluator,
			String envPrefix, String envSuffix,
			String expressionPrefix, String expressionSuffix) {
		this.environment = environment;
		this.evaluator = evaluator;
		this.envPrefix = envPrefix;
		this.envSuffix = envSuffix;
		this.expressionPrefix = expressionPrefix;
		this.expressionSuffix = expressionSuffix;
	}

	public EnvConfigurableDataSource(Environment environment, ExpressionEvaluator evaluator) {
		this(environment, evaluator,
				ENV_PREFIX_DEFAULT, ENV_SUFFIX_DEFAULT,
				EXPRESSION_PREFIX_DEFAULT, EXPRESSION_SUFFIX_DEFAULT);
	}

	@Override
	protected String evaluateValue(String description, String value) {
		// LOG.debug("Evaluate " + description + ": " + value);
System.out.println("----------------");
System.out.println(description);
System.out.println(value);
		value = (value != null && isEnvProperty(value))
				? environment.getProperty(description, peelEnvProperty(value))
				: (value != null && isExpression(value))
					? evaluator.evaluateExpression(description, peelExpression(value))
					: value;
System.out.println(value);
		return value;
	}

	protected boolean isEnvProperty(String value) {
		return value.startsWith(this.envPrefix) && value.endsWith(envSuffix);
	}

	protected String peelEnvProperty(String value) {
		return value.substring(this.envPrefix.length(),
				value.length() - envSuffix.length());
	}

	protected boolean isExpression(String value) {
		return value.startsWith(this.expressionPrefix) && value.endsWith(expressionSuffix);
	}

	protected String peelExpression(String value) {
		return value.substring(this.expressionPrefix.length(),
				value.length() - expressionSuffix.length());
	}
}
