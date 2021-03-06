package clwhthr.form;

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

public class FormRegister {

	protected Shell shell;
	protected Shell parent;
	private Text textAccount;
	private Text textPassword;
	private Text textPassword2;
	private Button buttonBack;
	private Button buttonRegister;
	private Text textInfo;


	public FormRegister(Shell parent) {
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
		shell = new Shell(SWT.BORDER | SWT.TITLE);
		shell.setMinimumSize(new Point(400, 270));
		shell.setSize(500, 270);
		shell.setText("\u8A3B\u518A\u5E33\u865F");
		shell.addListener(SWT.CLOSE, new Listener() {
			@Override
			public void handleEvent(Event event) {
				buttonBack.setSelection(true);
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
		
		buttonRegister = new Button(shell, SWT.NONE);
		buttonRegister.setLocation(380, 186);
		buttonRegister.setSize(94, 29);
		buttonRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				register();
			}
		});
		buttonRegister.setText("\u8A3B\u518A");
		
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
		buttonBack.setText("\u8FD4\u56DE");
		
		textInfo = new Text(shell, SWT.MULTI);
		textInfo.setEditable(false);
		textInfo.setText("\u5E33\u865F\u53CA\u5BC6\u78BC\u7684\u9577\u5EA6\u70BA6\u523030\u500B\u5B57\r\n\u50C5\u80FD\u5305\u542B\u82F1\u6587 \u6578\u5B57 \u53CA \u5E95\u7DDA\r\n\u958B\u982D\u5FC5\u9808\u70BA\u82F1\u6587");
		textInfo.setBounds(268, 35, 216, 140);

	}
	
	private void register() {
		if(textAccount.getText().length() == 0 || textPassword.getText().length() == 0 ||textPassword2.getText().length() == 0) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("???~");
			dialog.setMessage("???????i??????!");
			dialog.open();
			return;
		}
		if(textPassword.getText().equals(textPassword2.getText()) == false) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("???~");
			dialog.setMessage("?z???J???????K?X????????\n???A???@??");
			dialog.open();
			return;
		}
		
		try {
			AccountHandler.getInstance().registerAccount(textAccount.getText(), textPassword.getText());
		} catch (StringFormatException e) {
			// TODO ?????????? catch ????
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("???~");
			dialog.setMessage("?b?????K?X???J???????~!");
			dialog.open();
			return;
		}catch (FileExistException e) {
			MessageBox dialog = new MessageBox(this.shell, SWT.ICON_WARNING|SWT.ICON_CANCEL);
			dialog.setText("???~");
			dialog.setMessage("???b???w?g?s?b");
			dialog.open();
			return;
		}
		
		MessageBox dialog = new MessageBox(this.shell, SWT.ICON|SWT.ICON_INFORMATION);
		dialog.setText("???U???\");
		dialog.setMessage("?z???b???w???U????\n???n?J");
		int result = dialog.open();
		buttonBack.notifyListeners(SWT.Selection, new Event());
	}

}
