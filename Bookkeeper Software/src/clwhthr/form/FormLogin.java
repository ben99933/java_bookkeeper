package clwhthr.form;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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
import clwhthr.account.AccountHandler;
import clwhthr.form.dialog.FormRegister;
import clwhthr.io.AccountFileGetter;
import clwhthr.io.AccountFileReader;
import clwhthr.main.Main;
import clwhthr.resources.I18n;
import clwhthr.setting.Config;
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;
import clwhthr.util.hash.SHA;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;

public class FormLogin {

	protected Shell shell;
	private Text textAccount;
	private Text textPassword;
	private Button buttonRemember;
	private Button buttonLogin;
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
		shell.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent event) {
				 if (event.detail == SWT.TRAVERSE_RETURN){ 
					 if(event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {
						 buttonLogin.notifyListeners(SWT.Selection, new Event());
					 }
				 }
			}
		});
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
		shell.setText(I18n.format("form.login.shell.name", new Object[0]));
		shell.setLocation(0, 0);
		
		Label labelAccount = new Label(shell, SWT.NONE);
		labelAccount.setText(I18n.format("form.login.label.account.name", new Object[0]));
		labelAccount.setBounds(10, 10, 220, 19);
		
		textAccount = new Text(shell, SWT.BORDER);
		textAccount.setBounds(10, 29, 220, 25);
		
		Label labelPassword = new Label(shell, SWT.NONE);
		labelPassword.setText(I18n.format("form.login.label.password.name", new Object[0]));
		labelPassword.setBounds(10, 60, 220, 19);
		
		textPassword = new Text(shell, SWT.BORDER|SWT.PASSWORD);
		textPassword.setBounds(10, 79, 220, 25);
		
		buttonLogin = new Button(shell, SWT.NONE);
		buttonLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(login()) {
					MessageBox dialog = new MessageBox(shell);
					dialog.setText(I18n.format("msg.loginSucceed.title.name", new Object[0]));
					dialog.setMessage(I18n.format("msg.loginSucceed.text", new Object[0]));
					dialog.open();
					
					FormMain menu = new FormMain();
					shell.close();
					menu.open();
				}else {
					MessageBox dialog = new MessageBox(shell,SWT.ICON_WARNING);
					dialog.setText(I18n.format("msg.error.title.name", new Object[0]));
					dialog.setMessage(I18n.format("msg.loginFailed.text", new Object[0]));
					dialog.open();
				}
			}
		});
		buttonLogin.setBounds(250, 151, 94, 29);
		buttonLogin.setText(I18n.format("form.login.button.login.name", new Object[0]));
		
		
		Button buttonRegister = new Button(shell, SWT.NONE);
		buttonRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.setEnabled(false);
				formRegister.open();
			}
		});
		buttonRegister.setBounds(10, 151, 94, 29);
		buttonRegister.setText(I18n.format("form.login.button.register.name", new Object[0]));
		
		buttonRemember = new Button(shell, SWT.CHECK);
		buttonRemember.setBounds(10, 110, 220, 19);
		buttonRemember.setText(I18n.format("form.login.button.remember.name", new Object[0]));
		if(config.getRememberPassword()) {
			buttonRemember.setSelection(true);
			textAccount.setText(config.getLastAccountName());
		}

	}
	
	private boolean login() {
		String name = textAccount.getText();
		String password = textPassword.getText();
		String hash = SHA.getResult(password);
		if(AccountHandler.getInstance().login(textAccount.getText(),hash) == false)return false;
		Main.currentAccount = new Account(name, hash);
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
