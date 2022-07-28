package clwhthr.form.dialog;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import clwhthr.resources.I18n;
import clwhthr.resources.LanguageManeger;
import clwhthr.resources.lang.Language;
import clwhthr.resources.lang.Languages;
import clwhthr.setting.Config;
import clwhthr.util.Debug;
import clwhthr.util.form.FormHelper;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Combo;

public class FormSetting extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Shell parent;
	private Combo combo;
	private Button buttonApply;
	private Button buttonBack;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormSetting(Shell parent) {
		super(parent, SWT.NONE);
		setText(I18n.format("form.setting.shell.name", new Object[0]));
		this.parent = parent;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
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
		shell.setText(I18n.format("form.setting.shell.name", new Object[0]));
		
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(110, 10, 286, 27);
		String[] names = Languages.getLocalNames();
		combo.setItems(names);
		combo.select(Arrays.asList(names).lastIndexOf(LanguageManeger.currentLanguage().getLocalName()));
		
		
		buttonApply = new Button(shell, SWT.NONE);
		buttonApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
				close();
			}
		});
		buttonApply.setBounds(330, 216, 94, 29);
		buttonApply.setText(I18n.format("form.setting.button.apply.name"));

		buttonBack = new Button(shell, SWT.NONE);
		buttonBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonBack.setBounds(10, 216, 94, 29);
		buttonBack.setText(I18n.format("form.setting.button.back.name"));
		
		Label labelLang = new Label(shell, SWT.NONE);
		labelLang.setBounds(10, 10, 94, 19);
		labelLang.setText(I18n.format("form.settting.label.lang.name"));
		
	}
	private void save() {
		Config config = Config.getInstance();
		String langName = combo.getItem(combo.getSelectionIndex());
		Language language = Languages.getByLocalName(langName);
		
		try {
			config.setLanguage(language);
			config.save();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		MessageBox dialog = new MessageBox(shell,SWT.None);
		dialog.setText("");
		dialog.setMessage(I18n.format("msg.setting.info.text"));
		dialog.open();
	}
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
