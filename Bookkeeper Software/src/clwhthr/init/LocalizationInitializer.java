package clwhthr.init;

import clwhthr.resources.LanguageManeger;
import clwhthr.resources.lang.Language;
import clwhthr.resources.lang.Languages;
import clwhthr.util.Debug;

public class LocalizationInitializer implements PreInitializer{

	@Override
	public void preInit() {
		Languages.init();
		LanguageManeger.init();
		Debug.log(this.getClass(), "Localization complete");
	}
}
