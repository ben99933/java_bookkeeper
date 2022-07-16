package clwhthr.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class InitializeHandler{
	
	private static boolean preExecuted = false;
	private static boolean executed = false;
	private static Set<Initializer> initList = new HashSet<Initializer>();
	private static Initializer recordInitializer;
	//main©I¥sªº
	public static void preInit() {
		if(preExecuted)return;
		recordInitializer = new RecordInitializer();
		initList.add(recordInitializer);
	}
	
	
	public static void init() {
		if(executed)return;
		executed = true;
		for (Initializer initializedObject : initList) {
			initializedObject.init();
		}
	}
	public static void register(Initializer observer) {
		if(initList.contains(observer))return;
		initList.add(observer);
	}
}
