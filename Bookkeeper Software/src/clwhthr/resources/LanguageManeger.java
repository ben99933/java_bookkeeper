package clwhthr.resources;

import java.util.Locale;

import clwhthr.resources.lang.Language;
import clwhthr.resources.lang.Languages;
import clwhthr.setting.Config;

public class LanguageManeger {
	
	private static Language currentLang; 
	
	
	public static void init() {
		Config config = Config.getInstance();
		String language = config.getLanguageName();
		if(language.equals("en_US"))currentLang = Languages.en_US;
		else if(language.equals("zh_TW"))currentLang = Languages.zh_TW;
		else currentLang = Languages.en_US;
		
		I18n.setLanguage(currentLang);
	}
	public static Language currentLanguage() {
		return currentLang;
	}
}
