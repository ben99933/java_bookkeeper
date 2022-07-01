package clwhthr.form.login;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.forms.widgets.FormHeading;

import com.sun.glass.ui.Screen;

import clwhthr.account.Account;
import clwhthr.io.AccountFileGetter;
import clwhthr.io.AccountFileReader;
import clwhthr.main.Main;
import clwhthr.setting.Config;
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;
import clwhthr.util.hash.SHA;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;

public class FormLogin {

	protected Shell shell;
	private Text textAccount;
	private Text textPassword;
	private Button buttonRemember;
	private FormRegister formRegister;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormLogin window = new FormLogin();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		FormHelper.setCenter(this.shell);
		formRegister = new FormRegister(shell);
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Config config = Config.getInstance();
		shell = new Shell(SWT.DIALOG_TRIM);
		shell.setSize(372, 237);
		shell.setText("\u767B\u5165");
		shell.setLocation(0, 0);
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("\u5E33\u865F");
		label.setBounds(10, 10, 40, 19);
		
		textAccount = new Text(shell, SWT.BORDER);
		textAccount.setBounds(10, 29, 220, 25);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText("\u5BC6\u78BC");
		label_1.setBounds(10, 60, 40, 19);
		
		textPassword = new Text(shell, SWT.BORDER|SWT.PASSWORD);
		textPassword.setBounds(10, 79, 220, 25);
		
		Button buttonLogin = new Button(shell, SWT.NONE);
		buttonLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(login()) {
					MessageBox dialog = new MessageBox(shell);
					dialog.setText("");
					dialog.setMessage("登入成功!");
					dialog.open();
					
					FormMenu menu = new FormMenu();
					shell.close();
					menu.open();
				}else {
					MessageBox dialog = new MessageBox(shell,SWT.ICON_WARNING);
					dialog.setText("錯誤");
					dialog.setMessage("帳號或密碼輸入錯誤!");
					dialog.open();
				}
			}
		});
		buttonLogin.setBounds(250, 151, 94, 29);
		buttonLogin.setText("\u767B\u5165");
		
		
		Button buttonRegister = new Button(shell, SWT.NONE);
		buttonRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.setEnabled(false);
				formRegister.open();
			}
		});
		buttonRegister.setBounds(10, 151, 94, 29);
		buttonRegister.setText("\u8A3B\u518A\u5E33\u865F");
		
		buttonRemember = new Button(shell, SWT.CHECK);
		buttonRemember.setBounds(10, 110, 94, 19);
		buttonRemember.setText("\u8A18\u4F4F\u5E33\u865F");
		if(config.getRememberPassword()) {
			buttonRemember.setSelection(true);
			textAccount.setText(config.getLastAccountName());
		}

	}
	
	private boolean login() {
		String name = textAccount.getText();
		String passhash = SHA.getResult(textPassword.getText());
		try {
			
			AccountFileReader reader = new AccountFileReader(AccountFileGetter.getFile(name));
			Account account = reader.getAccount();
			reader.close();
			if(account.getPasswordHash().equals(passhash) == false)return false;
			
		} catch (FileNotFoundException e) {
			return false;
		}
		Main.currentAccount = new Account(name, passhash);
		Config config = Config.getInstance();
		boolean remember = buttonRemember.getSelection();
		config.setRememberPassword(remember);
		config.setLastAccountName(textAccount.getText());
		try {
			config.save();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		return true;
	}
}
