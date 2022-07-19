package clwhthr.util.form;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class FormHelper {
	
	public static final Color BACKGROUND = SWTResourceManager.getColor(255, 235, 205);
	
	public enum ScreenSize{
		small(700,700),medium(800, 800),large(900,900);
		public final int width;
		public final int height;
		private ScreenSize(int width, int height) {
			this.height = height;
			this.width = width;
		}
	}
	
	public static void setCenter(Shell shell) {
		Display display = Display.getDefault();
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		shell.setLocation(bounds.x + (bounds.width - rect.width) / 2, bounds.y + (bounds.height - rect.height) / 2);
	}
	
}
