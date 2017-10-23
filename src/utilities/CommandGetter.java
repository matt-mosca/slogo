package utilities;

import backend.Parser;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedCommandException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommandGetter {

	private final String COMMAND_INFO_FILE = "CommandNodes.properties";
	private final String LANGUAGES_PROPERTIES_FOLDER = "languages/";
	private final String PROPERTIES_SUFFIX = ".properties";
	public static final String DEFAULT_LANGUAGE = "English";
	private final Properties COMMAND_PROPERTIES;
	private Properties languageProperties;
	private Map<String, String> commandMap = new HashMap<>();

	private final String COMMAND_PARSING_FILE = "CommandParsing.properties";
	private final Class PARSER_CLASS = Parser.class;
	private final Class[] PARSE_METHOD_ARGUMENT_CLASSES = {PeekingIterator.class};
	private final Properties COMMAND_PARSING_PROPERTIES;

	public CommandGetter() {
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
			// TODO - need frontend method to launch failure dialog box
			System.out.println("Missing File!"); // TEMP
		}
	}

	public void setLanguage(String language) throws IOException  {
		InputStream properties = getClass().getClassLoader()
				.getResourceAsStream(LANGUAGES_PROPERTIES_FOLDER + language + PROPERTIES_SUFFIX);
		languageProperties.load(properties);
		fillCommandMap();
	}

	private void fillCommandMap() {
		commandMap.clear();
		Set<String> baseCommands = languageProperties.stringPropertyNames();
		for (String baseCommand : baseCommands) {
			String aliasesString = languageProperties.getProperty(baseCommand, "");
			String[] languageAliases = aliasesString.split("\\|");
			for (String alias : languageAliases) {
				commandMap.put(alias.replace("\\", ""), baseCommand);
			}
		}
	}

	public Class getCommandNodeClass(String commandName) throws SLogoException {
		String canonicalName = getCanonicalName(commandName);
		String commandNodeClassName = COMMAND_PROPERTIES.getProperty(canonicalName);
		try {
			return Class.forName(commandNodeClassName);
		} catch (ClassNotFoundException badMethod) {
			throw new UndefinedCommandException(commandName);
		}

	}

	public Method getParsingMethod(String commandName) throws SLogoException {
		System.out.println("Getting parsing method");
		String canonicalName = getCanonicalName(commandName);
		String methodName = COMMAND_PARSING_PROPERTIES.getProperty(canonicalName);
		System.out.println("Canonical Name: " + canonicalName + "; " + methodName);
		try {
			return PARSER_CLASS.getDeclaredMethod(methodName, PARSE_METHOD_ARGUMENT_CLASSES);
		} catch (NoSuchMethodException badMethod) {
			badMethod.printStackTrace();
			throw new UndefinedCommandException(commandName);
		}
	}

	private String getCanonicalName (String command) throws SLogoException {
		if (!commandMap.containsKey(command.toLowerCase())) {
			throw new UndefinedCommandException(command);
		}
		return commandMap.get(command.toLowerCase());
	}
}