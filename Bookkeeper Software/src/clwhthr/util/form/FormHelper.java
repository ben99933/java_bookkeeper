package clwhthr.util.form;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class FormHelper {
	
	public static final Color BACKGROUND = SWTResourceManager.getColor(255, 235, 205);
	public static final Color RED = SWTResourceManager.getColor(243, 45, 10);
	public static final Color GREEN = SWTResourceManager.getColor(0, 200, 0);
	public static final Color BLUE = SWTResourceManager.getColor(0, 100, 255);
	public static final Color YELLOW = SWTResourceManager.getColor(255, 235, 0);
	public static final Color ORANGE = SWTResourceManager.getColor(255, 138, 0);
	public static final Color PURPLE = SWTResourceManager.getColor(161,0,255);
	
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
	public static List<TableItem> getCheckedItems(Table table) {
		return Arrays.stream(table.getItems()).filter(item -> item.getChecked()).collect(Collectors.toList());
	}
	
}
