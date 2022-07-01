package clwhthr.util.form;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class FormHelper {
	
	enum ScreenSize{
		small(960,540),medium(1280, 720),large(1600,900);
		int width;
		int height;
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
