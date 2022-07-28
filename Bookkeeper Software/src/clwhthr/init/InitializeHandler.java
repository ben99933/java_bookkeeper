package clwhthr.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import clwhthr.util.Debug;

public class InitializeHandler{
	
	private static boolean prepareExecuted = false;
	private static boolean preInitExecuted = false;
	private static boolean executed = false;
	private static Set<Initializer> initList = new HashSet<Initializer>();
	private static Set<PreInitializer> preInitList = new HashSet<PreInitializer>();
	private static Initializer recordInitializer;
	private static PreInitializer localizeInitializer;
	
	//main©I¥sªº
	public static void prepare() {
		if(prepareExecuted)return;
		prepareExecuted = false;
		recordInitializer = new RecordInitializer();
		localizeInitializer = new LocalizationInitializer();
		initList.add(recordInitializer);
		preInitList.add(localizeInitializer);
	}
	
	public static void preInit() {
		if(preInitExecuted)return;
		preInitExecuted = true;
		for (PreInitializer initializedObject : preInitList) {
			initializedObject.preInit();
		}
		Debug.log(InitializeHandler.class, "Pre init complete");
	}
	public static void init() {
		if(executed)return;
		executed = true;
		for (Initializer initializedObject : initList) {
			initializedObject.init();
		}
		Debug.log(InitializeHandler.class, "Init complete");
	}
	public static void register(Initializer observer) {
		if(initList.contains(observer))return;
		initList.add(observer);
	}
}
