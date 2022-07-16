package clwhthr.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.internal.forms.widgets.FormHeading;

import clwhthr.exception.DateFormatException;
import clwhthr.util.Date;
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FormAddMonth extends Dialog {

	protected Date result;
	protected Shell shell;
	protected Shell parent;
	protected DateTime timeBox;
	protected Button buttonCancel;
	protected Button buttonConfirm;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormAddMonth(Shell parent, int style) {
		super(parent, style);
		this.parent = parent;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Date open() {
		createContents();
		result = null;
		shell.open();
		shell.layout();
		shell.addListener(SWT.CLOSE, new Listener() {
			@Override
			public void handleEvent(Event event) {
				buttonCancel.setSelection(true);
			}
		});
		FormHelper.setCenter(shell);
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(),SWT.RESIZE | SWT.TITLE);
		shell.setSize(450, 208);
		shell.setText("\u65B0\u589E\u6708\u4EFD");
		
		buttonConfirm = new Button(shell, SWT.NONE);
		buttonConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					result = new Date(timeBox.getYear(),timeBox.getMonth()+1,timeBox.getDay());
				} catch (DateFormatException e1) {
					e1.printStackTrace();
				}
				close();
			}
		});
		buttonConfirm.setBounds(328, 122, 94, 29);
		buttonConfirm.setText("\u78BA\u8A8D");
		
		buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonCancel.setBounds(10, 122, 94, 29);
		buttonCancel.setText("\u53D6\u6D88");
		
		timeBox = new DateTime(shell, SWT.BORDER | SWT.SHORT);
		timeBox.setBounds(49, 50, 309, 37);
		Date today = Date.getToday();
		timeBox.setDate(today.getYear(), today.getMonth()+1, today.getDay());
	}
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
