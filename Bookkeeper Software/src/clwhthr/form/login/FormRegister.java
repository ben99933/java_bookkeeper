package clwhthr.form.login;

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
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;
import clwhthr.util.hash.MD5;
import sun.launcher.resources.launcher;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class FormRegister {

	protected Shell shell;
	protected Shell parent;
	private Text textAccount;
	private Text textPassword;
	private Text textPassword2;
	private Button buutonBack;
	private Text textInfo;


	public FormRegister(Shell parent) {
		this.parent = parent;
	}

	/**
	 * Open the window.
	 */
	protected void open() {
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
		shell = new Shell(SWT.DIALOG_TRIM);
		shell.setMinimumSize(new Point(400, 270));
		shell.setSize(500, 270);
		shell.setText("\u8A3B\u518A\u5E33\u865F");
		shell.addListener(SWT.CLOSE, new Listener() {
			@Override
			public void handleEvent(Event event) {
				parent.setEnabled(true);
			}
		});
		shell.setLayout(null);
		
		Label label = new Label(shell, SWT.NONE);
		label.setLocation(10, 10);
		label.setSize(40, 19);
		label.setText("\u5E33\u865F");
		
		textAccount = new Text(shell, SWT.BORDER);
		textAccount.setLocation(10, 35);
		textAccount.setSize(220, 25);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setLocation(10, 65);
		label_1.setSize(40, 19);
		label_1.setText("\u5BC6\u78BC");
		
		textPassword = new Text(shell, SWT.BORDER);
		textPassword.setLocation(10, 90);
		textPassword.setSize(220, 25);
		
		textPassword2 = new Text(shell, SWT.BORDER);
		textPassword2.setLocation(10, 145);
		textPassword2.setSize(220, 25);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setLocation(10, 120);
		label_2.setSize(129, 19);
		label_2.setText("\u518D\u6B21\u8F38\u5165\u60A8\u7684\u5BC6\u78BC");
		
		Button buttonRegister = new Button(shell, SWT.NONE);
		buttonRegister.setLocation(380, 186);
		buttonRegister.setSize(94, 29);
		buttonRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				register();
			}
		});
		buttonRegister.setText("\u8A3B\u518A");
		
		buutonBack = new Button(shell, SWT.NONE);
		buutonBack.setLocation(10, 186);
		buutonBack.setSize(94, 29);
		buutonBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				parent.setEnabled(true);
				shell.close();
			}
		});
		buutonBack.setText("\u8FD4\u56DE");
		
		textInfo = new Text(shell, SWT.MULTI);
		textInfo.setEditable(false);
		textInfo.setText("\u5E33\u865F\u53CA\u5BC6\u78BC\u7684\u9577\u5EA6\u70BA6\u523030\u500B\u5B57\r\n\u50C5\u80FD\u5305\u542B\u82F1\u6587 \u6578\u5B57 \u53CA \u5E95\u7DDA\r\n\u958B\u982D\u5FC5\u9808\u70BA\u82F1\u6587");
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
		buutonBack.notifyListeners(SWT.Selection, new Event());
	}

}
