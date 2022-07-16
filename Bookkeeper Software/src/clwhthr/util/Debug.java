package clwhthr.util;

public final class Debug {
	private static boolean isDebugMode = false;
	public static void setDebug(boolean bool) {
		isDebugMode = bool;
		log(Debug.class,"Debug mode opened.");
	}
	public static void log(Class c,Object object) {
		if(!isDebugMode)return;
		System.out.println(c.getSimpleName() + ">>" + object);
	}
	public static void log(Class c, String format,Object ... args) {
		if(!isDebugMode)return;
		System.out.print(c.getSimpleName() + ">>");
		System.out.printf(format + "\n",args);
	}
}
