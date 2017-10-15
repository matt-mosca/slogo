package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommandGetter {

    private final String COMMAND_INFO_FILE = "Commands.properties";
    private final Properties COMMAND_PROPERTIES;
    private Properties languageProperties;
    private Map<String, String> commandMap = new HashMap<>();

    public CommandGetter (String languageFile) {
        COMMAND_PROPERTIES = new Properties();
        InputStream commandPropertiesStream = getClass().getClassLoader().getResourceAsStream(COMMAND_INFO_FILE);
        try {
            COMMAND_PROPERTIES.load(commandPropertiesStream);
        } catch (IOException fileNotFound){
            // need frontend method to launch failure dialog box
        }
        languageProperties = new Properties();
        setLanguage(languageFile);
    }

    public void setLanguage(String languageFile) {
        InputStream properties = getClass().getClassLoader().getResourceAsStream(languageFile);
        try {
            languageProperties.load(properties);
        } catch (IOException fileNotFound) {
            // need frontend method to launch failure dialog box
        }
        fillCommandMap();
    }

    private void fillCommandMap() {
        commandMap.clear();
        Set<String> baseCommands = languageProperties.stringPropertyNames();
        for (String baseCommand : baseCommands) {
            String aliasesString = languageProperties.getProperty(baseCommand, "");
            String[] languageAliases = languageProperties.getProperty(baseCommand, "").split("\\|");
            for (String alias : languageAliases) {
                commandMap.put(alias.replace("\\", ""), baseCommand);
            }
        }
    }

    public String[] getCommandInfo(String command) throws IllegalArgumentException{
        String commandInfo;
        System.out.println(commandMap.keySet());
        if (!commandMap.containsKey(command)
                || (commandInfo =  COMMAND_PROPERTIES.getProperty(commandMap.get(command))) == null) {
            throw new IllegalArgumentException();
        }
        System.out.println(commandInfo);
        return commandInfo.split(",");
    }
}
