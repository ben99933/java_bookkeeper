package clwhthr.form.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import clwhthr.exception.DateFormatException;
import clwhthr.record.Record;
import clwhthr.record.Record.Type;
import clwhthr.resources.I18n;
import clwhthr.util.Date;
import clwhthr.util.form.FormHelper;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;

public class FormEditRecord extends Dialog {

	protected Record result = null;
	protected Shell shell;
	protected Shell parent;
	private Record record;
	private Text textDate;
	private Text textMoney;
	private Text textNote;
	private Combo comboType;
	private Spinner spinnerDay;
	private int year;
	private int month;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormEditRecord(Shell parent, Record record) {
		super(parent, SWT.NONE);
		setText("SWT Dialog");
		this.parent = parent;
		this.record = record;
		Date date = record.getDate();
		this.year = date.getYear();
		this.month = date.getMonth();
		
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
		shell = new Shell(getParent(), SWT.TITLE);
		shell.setSize(450, 310);
		shell.setText(I18n.format("form.editRecord.shell.name"));

		Button buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				close();
			}
		});
		buttonCancel.setBounds(10, 226, 94, 29);
		buttonCancel.setText(I18n.format("form.button.cancel.name"));
		
		Button buttonConfirm = new Button(shell, SWT.NONE);
		buttonConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int day = spinnerDay.getSelection();
					Date date = new Date(year,month,day);
					Record.Type type = Type.values()[comboType.getSelectionIndex()];
					int money = Integer.valueOf(textMoney.getText());
					String note = textNote.getText();
					if(note.length() == 0)note = "none";
					if(note.contains(" "))throw new Exception();
					result = new Record(date,type,money,note);
					close();
				} 
				catch (Exception e1) {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING);
					dialog.setText(I18n.format("msg.error.title.name"));
					dialog.setMessage(I18n.format("msg.worngFormat.text"));
					dialog.open();
					
				}
			}
		});
		buttonConfirm.setBounds(330, 226, 94, 29);
		buttonConfirm.setText(I18n.format("form.button.confirm.name"));
		
		Label labelDay = new Label(shell, SWT.NONE);
		labelDay.setBounds(10, 10, 73, 19);
		labelDay.setText(I18n.format("record.date.name"));
		
		textDate = new Text(shell, SWT.BORDER);
		textDate.setText(year+"/"+month);
		textDate.setEditable(false);
		textDate.setBounds(10, 35, 73, 25);
		
		spinnerDay = new Spinner(shell, SWT.BORDER);
		spinnerDay.setBounds(89, 35, 57, 26);
		spinnerDay.setMinimum(1);
		spinnerDay.setMaximum(31);
		spinnerDay.setSelection(record.getDate().getDay());
		spinnerDay.setIncrement(1);
		spinnerDay.setPageIncrement(1);
		

		Label labelMoney = new Label(shell, SWT.NONE);
		labelMoney.setBounds(288, 10, 73, 19);
		labelMoney.setText(I18n.format("record.money.name"));
		
		textMoney = new Text(shell, SWT.BORDER);
		textMoney.setBounds(288, 35, 136, 25);
		textMoney.setText(String.valueOf(record.getMoney()));
		
		comboType = new Combo(shell, SWT.NONE);
		comboType.setBounds(176, 35, 92, 27);
		String typeLocalNames[] = Record.Type.localNames();
		comboType.setItems(typeLocalNames);
		int index = 0;
		for(int i =0;i<typeLocalNames.length;i++)if(typeLocalNames[i].equals(record.getType().getLocalName()))comboType.select(i);
		
		
		Label labelType = new Label(shell, SWT.NONE);
		labelType.setBounds(176, 10, 73, 19);
		labelType.setText(I18n.format("record.type.name"));
		
		Label labelNote = new Label(shell, SWT.NONE);
		labelNote.setBounds(10, 77, 73, 19);
		labelNote.setText(I18n.format("record.note.name"));
		
		textNote = new Text(shell, SWT.BORDER);
		textNote.setBounds(10, 102, 414, 93);
		
	}
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
