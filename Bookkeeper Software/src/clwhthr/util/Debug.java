package clwhthr.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;




public final class Debug {

	private static boolean isDebugMode = false;
	public static void setDebug(boolean bool) {
		isDebugMode = bool;
		if(checkDebug() == false)return;
		log(Debug.class,"Debug mode opened.");
	}
	public static void debugFormCloseEvent(Event event) {
		if(event.detail != SWT.Close)return;
	}
	private static boolean checkDebug() {
		if(isDebugMode == false)return false;
		return true;
	}
	public static void log(Class c,Object object) {
		if(checkDebug() == false)return;
		System.out.println(c.getSimpleName() + ">>" + object);
	}
	public static void log(Class c, String format,Object ... args) {
		if(checkDebug() == false)return;
		System.out.print(c.getSimpleName() + ">>");
		System.out.printf(format + "\n",args);
	}
	
}
