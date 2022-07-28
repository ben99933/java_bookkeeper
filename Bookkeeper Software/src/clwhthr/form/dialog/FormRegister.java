package clwhthr.form.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;

import clwhthr.account.AccountHandler;
import clwhthr.exception.FileExistException;
import clwhthr.exception.StringFormatException;
import clwhthr.resources.I18n;
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;
import clwhthr.util.hash.MD5;
import sun.launcher.resources.launcher;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;

public class FormRegister extends Dialog{

	protected Shell shell;
	protected Shell parent;
	private Text textAccount;
	private Text textPassword;
	private Text textPassword2;
	private Button buttonBack;
	private Button buttonRegister;
	private Text textInfo;


	public FormRegister(Shell parent) {
		super(parent);
		this.parent = parent;
	}
	public static void main(String[] args) {
		try {
			FormRegister window = new FormRegister(new Shell());
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
		FormHelper.setCenter(shell);
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
		shell = new Shell(SWT.BORDER | SWT.TITLE);
		shell.setMinimumSize(new Point(400, 270));
		shell.setSize(500, 270);
		shell.setText(I18n.format("form.register.shell.name", new Object[0]));
		shell.addListener(SWT.CLOSE, new Listener() {
			@Override
			public void handleEvent(Event event) {
				buttonBack.setSelection(true);
			}
		});
		shell.setLayout(null);
		
		Label labelAccount = new Label(shell, SWT.NONE);
		labelAccount.setLocation(10, 10);
		labelAccount.setSize(220, 19);
		labelAccount.setText(I18n.format("form.register.label.account.name", new Object[0]));
		
		textAccount = new Text(shell, SWT.BORDER);
		textAccount.setLocation(10, 35);
		textAccount.setSize(220, 25);
		
		Label labelPassword = new Label(shell, SWT.NONE);
		labelPassword.setLocation(10, 65);
		labelPassword.setSize(220, 19);
		labelPassword.setText(I18n.format("form.register.label.password.name", new Object[0]));
		
		textPassword = new Text(shell, SWT.BORDER);
		textPassword.setLocation(10, 90);
		textPassword.setSize(220, 25);
		
		textPassword2 = new Text(shell, SWT.BORDER);
		textPassword2.setLocation(10, 145);
		textPassword2.setSize(220, 25);
		
		Label labelPassword2 = new Label(shell, SWT.NONE);
		labelPassword2.setLocation(10, 120);
		labelPassword2.setSize(220, 19);
		labelPassword2.setText(I18n.format("form.register.label.password2.name", new Object[0]));
		
		buttonRegister = new Button(shell, SWT.NONE);
		buttonRegister.setLocation(380, 186);
		buttonRegister.setSize(94, 29);
		buttonRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				register();
			}
		});
		buttonRegister.setText(I18n.format("form.register.button.register.name", new Object[0]));
		
		buttonBack = new Button(shell, SWT.NONE);
		buttonBack.setLocation(10, 186);
		buttonBack.setSize(94, 29);
		buttonBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				parent.setEnabled(true);
				shell.close();
			}
		});
		buttonBack.setText(I18n.format("form.register.button.back.name", new Object[0]));
		
		textInfo = new Text(shell, SWT.WRAP);
		textInfo.setEditable(false);
		textInfo.setText(I18n.format("form.register.label.info.text", new Object[0]));
		textInfo.setBounds(268, 35, 216, 140);

	}
	
	private void register() {
		if(textAccount.getText().length() == 0 || textPassword.getText().length() == 0 ||textPassword2.getText().length() == 0) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("錯誤");
			dialog.setMessage("欄位不可為空白!");
			dialog.open();
			return;
		}
		if(textPassword.getText().equals(textPassword2.getText()) == false) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("錯誤");
			dialog.setMessage("您輸入的兩個密碼並不相符\n請再試一次");
			dialog.open();
			return;
		}
		
		try {
			AccountHandler.getInstance().registerAccount(textAccount.getText(), textPassword.getText());
		} catch (StringFormatException e) {
			// TODO 自動產生的 catch 區塊
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("錯誤");
			dialog.setMessage("帳號或密碼輸入格式有誤!");
			dialog.open();
			return;
		}catch (FileExistException e) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("錯誤");
			dialog.setMessage("該帳號已經存在");
			dialog.open();
			return;
		}
		
		MessageBox dialog = new MessageBox(this.shell, SWT.ICON|SWT.ICON_INFORMATION);
		dialog.setText("註冊成功");
		dialog.setMessage("您的帳號已註冊完成\n請登入");
		int result = dialog.open();
		buttonBack.notifyListeners(SWT.Selection, new Event());
	}

}
