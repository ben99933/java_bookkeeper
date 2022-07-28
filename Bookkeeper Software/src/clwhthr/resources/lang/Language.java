package clwhthr.resources.lang;

import java.util.Locale;

import clwhthr.resources.I18n;

public abstract class Language {
	private Locale locale;
	public Language(Locale locale) {
		this.locale = locale;
	}
	public abstract String getName();
	public String getLocalName() {
		return I18n.format("lang."+getName()+".name", new Object[0]);
	}
	public Locale getLocale() {
		return this.locale;
	}
	@Override
	public String toString() {
		return this.getName() + locale.toString();
	}
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
}
