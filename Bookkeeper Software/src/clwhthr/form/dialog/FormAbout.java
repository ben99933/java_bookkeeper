package clwhthr.form.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import clwhthr.main.Main;
import clwhthr.resources.I18n;
import clwhthr.util.form.FormHelper;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;

public class FormAbout extends Dialog {

	protected Object result;
	protected Shell shell;
	private Shell parent;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FormAbout(Shell parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		setText("SWT Dialog");
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
		shell.setSize(300, 300);
		shell.setText(I18n.format("form.about.shell.name"));
		
		Button buttonClose = new Button(shell, SWT.NONE);
		buttonClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonClose.setBounds(180, 216, 94, 29);
		buttonClose.setText(I18n.format("form.about.button.close.name"));
		
		Label labelAuthor = new Label(shell, SWT.NONE);
		labelAuthor.setBounds(10, 10, 264, 19);
		labelAuthor.setText(I18n.format("form.about.label.author.name"));

		Label labelVersion = new Label(shell, SWT.NONE);
		labelVersion.setBounds(10, 35, 264, 19);
		labelVersion.setText(I18n.format("form.about.label.version.name",Main.Version));
		
		Label labelIconResource = new Label(shell, SWT.WRAP);
		labelIconResource.setBounds(10, 60, 264, 150);
		labelIconResource.setText(I18n.format("form.about.label.iconResource.name"));
		

	}
	private void close() {
		parent.setEnabled(true);
		shell.close();
	}
}
