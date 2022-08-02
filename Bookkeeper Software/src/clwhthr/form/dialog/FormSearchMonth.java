package clwhthr.form.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import clwhthr.exception.DateFormatException;
import clwhthr.resources.I18n;
import clwhthr.util.Date;
import clwhthr.util.form.FormHelper;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FormSearchMonth extends Dialog {

	protected Date result = null;
	protected Shell shell;
	private Shell parent;
	private DateTime dateTime;
	private Button buttonConfirm;
	private Button buttonCancel;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormSearchMonth(Shell parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Date open() {
		createContents();
		shell.open();
		shell.layout();
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
		shell = new Shell(getParent(), SWT.BORDER | SWT.TITLE);
		shell.setSize(450, 300);
		shell.setText(I18n.format("form.searchRecord.shell.name"));
		

		buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonCancel.setBounds(10, 216, 94, 29);
		buttonCancel.setText(I18n.format("form.searchRecord.button.cancel.name"));
		
		buttonConfirm = new Button(shell, SWT.NONE);
		buttonConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					result = new Date(dateTime.getYear(), dateTime.getMonth()+1, dateTime.getDay());
				} catch (DateFormatException e1) {
					// TODO 自動產生的 catch 區塊
					e1.printStackTrace();
				}
				close();
			}
		});
		buttonConfirm.setBounds(330, 216, 94, 29);
		buttonConfirm.setText(I18n.format("form.searchRecord.button.confirm.name"));
		
		dateTime = new DateTime(shell, SWT.BORDER | SWT.SHORT);
		dateTime.setBounds(58, 92, 317, 28);
		Date date = Date.getToday();
		dateTime.setDate(date.getYear(), date.getMonth()-1, date.getDay());
		
		Label labelDate = new Label(shell, SWT.NONE);
		labelDate.setBounds(55, 55, 73, 19);
		labelDate.setText(I18n.format("form.searchRecord.label.date.name"));
	}
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
