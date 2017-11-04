package utilities;

import backend.Parser;
import backend.SyntaxNode;
import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedCommandException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Uses reflection to determine the parsing classes for particular commands and the node classes that should be
 * constructed for them.
 *
 * @author Ben Schwennesen
 */
public class CommandGetter {

	private final String LANGUAGES_PROPERTIES_FOLDER = "resources/languages/";
	private final String PROPERTIES_SUFFIX = ".properties";
	private final String DEFAULT_LANGUAGE = "English";
	private Properties languageProperties;

	private final String COMMAND_INFO_FILE = "resources/CommandNodes.properties";
	private final Properties COMMAND_PROPERTIES;

	private final String COMMAND_PARSING_FILE = "resources/CommandParsing.properties";
	private final Class[] PARSE_METHOD_ARGUMENT_CLASSES = {PeekingIterator.class};
	private final Class PARSER_CLASS = Parser.class;
	private final Properties COMMAND_PARSING_PROPERTIES;

	private Map<String, String> commandMap = new HashMap<>();
	private Map<String, String> reverseCommandMap = new HashMap<>();

	/**
	 * Construct the command getter for use by the parser.
	 *
	 * @throws SLogoException
	 */
	public CommandGetter() throws SLogoException {
		COMMAND_PROPERTIES = new Properties();
		COMMAND_PARSING_PROPERTIES = new Properties();
		languageProperties = new Properties();
		try {
			InputStream commandPropertiesStream = getClass().getClassLoader().getResourceAsStream(COMMAND_INFO_FILE);
			COMMAND_PROPERTIES.load(commandPropertiesStream);
			
			InputStream commandParsingStream = getClass().getClassLoader().getResourceAsStream(COMMAND_PARSING_FILE);
			COMMAND_PARSING_PROPERTIES.load(commandParsingStream);
						
			setLanguage(DEFAULT_LANGUAGE);

		} catch (IOException fileNotFound) {
			throw new ProjectBuildException();
		}
	}

	private void fillCommandMap() {
		commandMap.clear();
		Set<String> baseCommands = languageProperties.stringPropertyNames();
		for (String baseCommand : baseCommands) {
			String aliasesString = languageProperties.getProperty(baseCommand, "");
			String[] languageAliases = aliasesString.split("\\|");
			String firstAlias = languageAliases[0];
			for (String alias : languageAliases) {
				commandMap.put(alias.replace("\\", ""), baseCommand);
			}
			reverseCommandMap.put(baseCommand, firstAlias);
		}
	}

	private String getCanonicalName (String command) throws SLogoException {
		if (!commandMap.containsKey(command.toLowerCase())) {
			throw new UndefinedCommandException(command);
		}
		return commandMap.get(command.toLowerCase());
	}

	/**
	 * Retrieve via reflection the syntax node class to use for constructing a particular command's part of the syntax
	 * tree.
	 *
	 * @param commandName - the command as entered by the user
	 * @return the syntax node class the parser should create an instance of
	 * @throws SLogoException - in the case that the string does not represent a command defined in the current language
	 */
	public Class getCommandNodeClass(String commandName) throws SLogoException {
		String canonicalName = getCanonicalName(commandName);
		String commandNodeClassName = COMMAND_PROPERTIES.getProperty(canonicalName);
		try {
			return Class.forName(commandNodeClassName);
		} catch (ClassNotFoundException badMethod) {
			throw new UndefinedCommandException(commandName);
		}

	}

	/**
	 * Determine via reflection the method the parser should use to parse a particular command.
	 *
	 * @param commandName - the command as entered by the user
	 * @return the method the parser should use to parse the entered command
	 * @throws SLogoException - in the case that the string does not represent a command defined in the current language
	 */
	public Method getParsingMethod(String commandName) throws SLogoException {
		String canonicalName = getCanonicalName(commandName);
		String methodName = COMMAND_PARSING_PROPERTIES.getProperty(canonicalName);
		try {
			return PARSER_CLASS.getDeclaredMethod(methodName, PARSE_METHOD_ARGUMENT_CLASSES);
		} catch (NoSuchMethodException badMethod) {
			badMethod.printStackTrace();
			throw new UndefinedCommandException(commandName);
		}
	}

	/**
	 * Change the language used to parse commands.
	 *
	 * @param language - the desired language as a string
	 * @throws IOException - in the case that the language properties file is not found
	 */
	public void setLanguage(String language) throws IOException  {
		InputStream properties = getClass().getClassLoader()
				.getResourceAsStream(LANGUAGES_PROPERTIES_FOLDER + language + PROPERTIES_SUFFIX);
		languageProperties.load(properties);
		fillCommandMap();
	}
}