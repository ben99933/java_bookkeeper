package clwhthr.main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import clwhthr.account.Account;
import clwhthr.form.login.FormLogin;
import clwhthr.setting.Config;
import clwhthr.util.Debug;

public class Main {
	
	
	
	public static Account currentAccount;
	public static ServerSocket serverSocket = null;
	
	public static void main(String[] args) {
		Debug.open();
		try {
		    serverSocket = new ServerSocket(9000);
		} catch (IOException e) {
		    MessageBox dialog = new MessageBox(new Shell(),SWT.ICON_WARNING);
		    dialog.setText("錯誤");
		    dialog.setMessage("此程式已經開啟");
		    dialog.open();
		    return;
		}
		
		Config config = Config.getInstance();
		
		try {
			FormLogin formLogin = new FormLogin();
			formLogin.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
