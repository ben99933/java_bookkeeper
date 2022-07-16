package clwhthr.main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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
	
	public static void main(String[] args) {
		Debug.setDebug(true);
		//if(debug()==0 || true)return;
		try {
		    serverSocket = new ServerSocket(9000);
		} catch (IOException e) {
		    MessageBox dialog = new MessageBox(new Shell(),SWT.ICON_WARNING);
		    dialog.setText("錯誤");
		    dialog.setMessage("此程式已經開啟");
		    dialog.open();
		    return;
		}
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
