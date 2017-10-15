package backend;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Translator {

	public static final String RESOURCE_PACKAGE = "resources/";
	// ResourceBundle for locale-specific customization
	private ResourceBundle myResources;
	private Map<String, String> stringToKeyMapping;
	
	public Translator(Locale defaultLocale) {
		loadStringsForLocale(defaultLocale);
	}

	public void loadStringsForLocale(Locale locale) {
		myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE + locale);
		for (String key : myResources.keySet()) {
			// TODO - NOT COMPLETE, NEED TO REGEX MANIPULATION
			stringToKeyMapping.put(myResources.getString(key), key);
		}
	}
	
	public String getCanonicalCommandFromLocaleString(String localeString) throws IllegalArgumentException {
		if (!stringToKeyMapping.containsKey(localeString)) {
			throw new IllegalArgumentException();
		}
		return stringToKeyMapping.get(localeString);
	}
	
}
