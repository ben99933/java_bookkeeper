package clwhthr.resources.lang;

import java.util.ArrayList;
import java.util.Locale;

import clwhthr.util.Debug;

public class Languages {
	
	private static ArrayList<Language>langList = new ArrayList<Language>();
	
	public static Language en_US;
	public static Language zh_TW;
	
	public static void init() {
		en_US = new Language(new Locale("en", "US")) {
			@Override
			public String getName() {
				// TODO 自動產生的方法 Stub
				return "en_US";
			}
		};

		zh_TW = new Language(new Locale("zh","TW")) {
			@Override
			public String getName() {
				// TODO 自動產生的方法 Stub
				return "zh_TW";
			}
		};
		langList.add(en_US);
		langList.add(zh_TW);
	}
	
	public static Language[] getLanguages() {
		Language[] array = new Language[langList.size()];
		langList.toArray(array);
		return array;
	}
	public static String[] getLocalNames() {
		int len = langList.size();
		String names[] = new String[len];
		for(int i = 0;i<len;i++){
			names[i] = langList.get(i).getLocalName();
		}
		return names;
	}
	public static Language getByName(String langName) {
		for(Language lang : langList) {
			if(lang.getName().equals(langName))return lang;
		}
		return null;
	}
	public static Language getByLocalName(String localName) {
		for(Language lang : langList) {
			if(lang.getLocalName().equals(localName))return lang;
		}
		return null;
	}

}
