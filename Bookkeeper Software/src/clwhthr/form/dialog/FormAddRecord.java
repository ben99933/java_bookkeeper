package clwhthr.form.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.forms.widgets.FormHeading;

import clwhthr.exception.DateFormatException;
import clwhthr.exception.FormatException;
import clwhthr.record.Record;
import clwhthr.record.Record.Type;
import clwhthr.resources.I18n;
import clwhthr.util.Date;
import clwhthr.util.form.FormHelper;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class FormAddRecord extends Dialog {

	protected Record result = null;
	protected Shell shell;
	protected Shell parent;
	protected Combo comboType;
	protected DateTime time;
	protected Label lableType;
	protected Button buttonConfirm;
	protected Button buttonCancel;
	private Text textMoney;
	private Text textNote;
	
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormAddRecord(Shell parent) {
		super(parent);
		this.parent = parent;
		setText(I18n.format("form.addRecord.shell.name"));
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Record open() {
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
		shell = new Shell(getParent(), SWT.BORDER | SWT.TITLE | SWT.PRIMARY_MODAL);
		shell.setSize(376, 278);
		shell.setText(I18n.format("form.addRecord.shell.name"));

		time = new DateTime(shell, SWT.BORDER);
		time.setBounds(10, 34, 112, 28);
		
		Label labelMoney = new Label(shell, SWT.NONE);
		labelMoney.setBounds(242, 10, 73, 19);
		labelMoney.setText(I18n.format("record.money.name"));
		textMoney = new Text(shell, SWT.BORDER);
		textMoney.setBounds(242, 35, 108, 25);
		
		Label labelNote = new Label(shell, SWT.NONE);
		labelNote.setBounds(10, 69, 73, 19);
		labelNote.setText(I18n.format("record.note.name"));
		
		textNote = new Text(shell, SWT.BORDER);
		textNote.setBounds(10, 94, 340, 52);
		
		
		Label lableDate = new Label(shell, SWT.NONE);
		lableDate.setBounds(10, 10, 73, 19);
		lableDate.setText(I18n.format("record.date.name"));
		
		buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonCancel.setBounds(10, 194, 94, 29);
		buttonCancel.setText(I18n.format("form.addRecord.button.cancel.name"));
		
		buttonConfirm = new Button(shell, SWT.NONE);
		buttonConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int year = time.getYear();
					int month = time.getMonth()+1;
					int day = time.getDay();
					Date date = new Date(year,month,day);
					Record.Type type = Type.values()[comboType.getSelectionIndex()];
					int money = Integer.valueOf(textMoney.getText());
					String note = textNote.getText();
					if(note.length() == 0)note = "none";
					if(note.contains(" "))throw new Exception();
					result = new Record(date,type,money,note);
					
					close();
				} catch (Exception exception) {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING);
					dialog.setText(I18n.format("msg.error.title.name"));
					dialog.setMessage(I18n.format("msg.worngFormat.text"));
					dialog.open();
					
				}
			}
		});
		buttonConfirm.setBounds(256, 194, 94, 29);
		buttonConfirm.setText(I18n.format("form.addRecord.button.confirm.name"));
		
		lableType = new Label(shell, SWT.NONE);
		lableType.setBounds(126, 10, 73, 19);
		lableType.setText(I18n.format("record.type.name"));
		
		
		comboType = new Combo(shell, SWT.NONE);
		comboType.setBounds(128, 35, 108, 27);
		comboType.setItems(Record.Type.localNames());
		comboType.select(0);
		
	}
	
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
