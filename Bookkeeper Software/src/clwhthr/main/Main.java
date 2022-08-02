package clwhthr.main;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import clwhthr.account.Account;
import clwhthr.form.FormLogin;
import clwhthr.init.InitializeHandler;
import clwhthr.io.file.CSVFile;
import clwhthr.setting.Config;
import clwhthr.util.Debug;



public class Main {
	
	public static Account currentAccount;
	public static ServerSocket serverSocket = null;
	public static List<CSVFile> recordFiles = new ArrayList<CSVFile>();
	public static final String Version = "1.0";
	
	public static void main(String[] args) {
		
		Debug.setDebug(true);
		try {
		    serverSocket = new ServerSocket(9000);
		} catch (IOException e) {
		    MessageBox dialog = new MessageBox(new Shell(),SWT.ICON_WARNING);
		    dialog.setText("錯誤");
		    dialog.setMessage("此程式已經開啟");
		    dialog.open();
		    return;
		}
		InitializeHandler.prepare();
		InitializeHandler.preInit();
		Config config = Config.getInstance();
		
		try {
			FormLogin formLogin = new FormLogin();
			formLogin.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static int debug() {
		return 0;
	}
	
}
