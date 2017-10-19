package utilities;

import commands.AbstractCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommandGetter {

	private final String COMMAND_INFO_FILE = "Commands.properties";
	private final String LANGUAGES_PROPERTIES_FOLDER = "languages/";
	private final String PROPERTIES_SUFFIX = ".properties";
	public static final String DEFAULT_LANGUAGE = "English";
	private final Properties COMMAND_PROPERTIES;
	private Properties languageProperties;
	private Map<String, String> commandMap = new HashMap<>();

	private final String DOUBLE_CLASS_NAME = "java.lang.Double";
	private final String CLASS_TYPE_DELIMITER = ",";

	private final int COMMAND_CLASS_NAME_INDEX = 0;
	private final int COMMAND_METHOD_NAME_INDEX = 1;
	private final int COMMAND_METHOD_PARAMETERS_CLASSES_INDEX = 2;


	public CommandGetter() {
		COMMAND_PROPERTIES = new Properties();
		try {
			//File file = new File(COMMAND_INFO_FILE);
			InputStream commandPropertiesStream = getClass().getClassLoader().getResourceAsStream(COMMAND_INFO_FILE);
			//InputStream commandPropertiesStream = new FileInputStream(f);
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

	private String[] getCommandInfo(String command) throws IllegalArgumentException {
		String commandInfo;
		command = command.toLowerCase();
		if (!commandMap.containsKey(command)
				|| (commandInfo = COMMAND_PROPERTIES.getProperty(commandMap.get(command))) == null) {
			throw new IllegalArgumentException();
		}
		return commandInfo.split("/");
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

	public AbstractCommand getCommandFromName(String command) throws ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		String[] commandInfo = getCommandInfo(command);
		Class commandType = Class.forName(commandInfo[0]);
		Class[] commandConstructorParameterClasses = new Class[] {Method.class};
		Method commandMethod = getMethodFromCommandInfo(commandType, commandInfo);
		return (AbstractCommand) commandType
				.getConstructor(commandConstructorParameterClasses).newInstance(commandMethod);
	}

	private Method getMethodFromCommandInfo(Class commandType, String[] commandInfo)
			throws ClassNotFoundException, NoSuchMethodException {
		String[] argumentTypeStrings = commandInfo[COMMAND_METHOD_PARAMETERS_CLASSES_INDEX].split(CLASS_TYPE_DELIMITER);
		Class[] commandParameterClasses = getParameterClasses(argumentTypeStrings);
		return commandType.getDeclaredMethod(commandInfo[COMMAND_METHOD_NAME_INDEX], commandParameterClasses);
	}

	private Class[] getParameterClasses(String[] argumentTypeStrings) throws ClassNotFoundException {
		if (argumentTypeStrings.length == 0) {
			return new Class[]{};
		}
		Class[] commandParameterClasses;
		commandParameterClasses = new Class[argumentTypeStrings.length];
		for (int i = 0; i < argumentTypeStrings.length; i++) {
			if (argumentTypeStrings[i].equals(DOUBLE_CLASS_NAME)) {
				commandParameterClasses[i] = double.class;
			} else {
				commandParameterClasses[i] = Class.forName(argumentTypeStrings[i]);
			}
		}
		return commandParameterClasses;
	}
	
}
