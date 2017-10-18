package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommandGetter {

	private final String COMMAND_INFO_FILE = "src/resources/Commands.properties";
	private final String LANGUAGES_PROPERTIES_FOLDER = "resources/languages/";
	private final String PROPERTIES_SUFFIX = ".properties";
	public static final String DEFAULT_LANGUAGE = "English";
	private final Properties COMMAND_PROPERTIES;
	private Properties languageProperties;
	private Map<String, String> commandMap = new HashMap<>();

	public CommandGetter() {
		COMMAND_PROPERTIES = new Properties();
		try {
			File f = new File(COMMAND_INFO_FILE);
			//InputStream commandPropertiesStream = getClass().getClassLoader().getResourceAsStream();
			InputStream commandPropertiesStream = new FileInputStream(f);
			COMMAND_PROPERTIES.load(commandPropertiesStream);
		} catch (IOException fileNotFound) {
			// need frontend method to launch failure dialog box
			System.out.println("Missing File!"); // TEMP
		}
		languageProperties = new Properties();
		setLanguage(DEFAULT_LANGUAGE);
	}

	public void setLanguage(String language) {
		InputStream properties = getClass().getClassLoader()
				.getResourceAsStream(LANGUAGES_PROPERTIES_FOLDER + language + PROPERTIES_SUFFIX);
		try {
			languageProperties.load(properties);
		} catch (IOException fileNotFound) {
			// need frontend method to launch failure dialog box
		}
		fillCommandMap();
	}

	public String[] getCommandInfo(String command) throws IllegalArgumentException {
		String commandInfo;
		command = command.toLowerCase();
		if (!commandMap.containsKey(command)
				|| (commandInfo = COMMAND_PROPERTIES.getProperty(commandMap.get(command))) == null) {
			throw new IllegalArgumentException();
		}
		return commandInfo.split(",");
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
	
}
