package clwhthr.util;

public final class Debug {
	private static boolean isDebugMode = false;
	public static void open() {
		isDebugMode = true;
	}
	public static void close() {
		isDebugMode = false;
	}
	public static void log(Class c,Object object) {
		if(!isDebugMode)return;
		System.out.println(c.getSimpleName() + ">>" + object);
	}
}
