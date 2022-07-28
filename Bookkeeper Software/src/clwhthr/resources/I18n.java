package clwhthr.resources;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import clwhthr.resources.lang.Language;

public class I18n {

	private static ResourceBundle resource;
	private static Language language;
    private static Locale locale;
    
    static void setLanguage(Language lang) {
    	language = lang;
    	locale = language.getLocale();
    	resource = ResourceBundle.getBundle("assets.clwhthr.lang.lang", locale);
    }

    /**
     * format(a, b) is equivalent to String.format(translate(a), b). Args: translationKey, params...
     */
    public static String format(String format, Object ... parameter)
    {
    	try {
    		MessageFormat messageFormat = new MessageFormat(resource.getString(format));
    		return messageFormat.format(parameter);
    	}catch (MissingResourceException e) {
    		return format;
		}
    	
    }
    public static String format(String format)
    {
    	return format(format,new Object[0]);
    }
}
