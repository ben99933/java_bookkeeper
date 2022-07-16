package clwhthr.form;

import java.awt.Scrollbar;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import clwhthr.exception.FileExistException;
import clwhthr.io.CSVCreater;
import clwhthr.io.RecordFileCreater;
import clwhthr.io.RecordFileGetter;
import clwhthr.main.Main;
import clwhthr.setting.Config;
import clwhthr.util.Date;
import clwhthr.util.Debug;
import clwhthr.util.FileHelper;
import clwhthr.util.form.FormHelper;
import clwhthr.util.form.ImageHelper;
import clwhthr.util.form.FormHelper.ScreenSize;

import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;

public class FormMain {

	protected Shell shell;
	protected Composite composite;
	protected Composite compositeRecordDay;
	protected Composite compositeOption;
	protected Button buttonOptionSearch;
	protected Button buttonOptionAnalyze;
	protected Button buttonAddNewMonth;
	
	protected List<Button> buttonRecordMonth;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormMain window = new FormMain();
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
		refreshRecordButton();
		Debug.log(this.getClass(), "list.length=%d",buttonRecordMonth.size());
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
		ScreenSize size = ScreenSize.values()[config.getScreenSize()];
		buttonRecordMonth = new ArrayList<Button>();
		
		shell = new Shell(SWT.CLOSE | SWT.MIN | SWT.TITLE );
		shell.setText("Bookkeeper");
		shell.setSize(size.width, size.height);
		shell.setBackground(SWTResourceManager.getColor(255, 235, 205));
		shell.setLayout(null);
		
		composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(10, 80, 864, 765);
		composite.setBackground(SWTResourceManager.getColor(255, 235, 205));
		
		compositeRecordDay = new Composite(composite, SWT.BORDER);
		compositeRecordDay.setBounds(284, 10, 566, 741);
		
		
		Composite compositeRecordMonth = new Composite(composite, SWT.BORDER | SWT.V_SCROLL | SWT.EMBEDDED);
		compositeRecordMonth.setBounds(14, 10, 264, 737);
		compositeRecordMonth.setLayout(null);
		
		ScrollBar barMonth = compositeRecordMonth.getVerticalBar();
		barMonth.addSelectionListener(new SelectionListener() {
		    public void widgetDefaultSelected(SelectionEvent e) {}

		    public void widgetSelected(SelectionEvent e) {
		        if (e.detail == SWT.NONE || e.detail == SWT.ARROW_UP || e.detail == SWT.ARROW_DOWN) {
		            refreshRecordButton(barMonth.getSelection());
		        }
		    }
		});
		
		
		compositeOption = new Composite(shell, SWT.BORDER);
		compositeOption.setBackground(SWTResourceManager.getColor(255, 235, 205));
		compositeOption.setBounds(10, 10, 864, 64);
		
		
		buttonOptionSearch = new Button(compositeOption, SWT.NONE);
		buttonOptionSearch.setBounds(142, 0, 60, 60);
		buttonOptionSearch.setBackground(SWTResourceManager.getColor(255, 235, 205));
		buttonOptionSearch.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/search.png"), 60, 60));
		buttonOptionSearch.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				
			}
		});
		
		buttonOptionAnalyze = new Button(compositeOption, SWT.NONE);
		buttonOptionAnalyze.setBounds(76, 0, 60, 60);
		buttonOptionAnalyze.setBackground(SWTResourceManager.getColor(255, 235, 205));
		buttonOptionAnalyze.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/analyse.png"), 60, 60));
		
		
		buttonAddNewMonth = new Button(compositeOption,SWT.PUSH);
		buttonAddNewMonth.setBounds(10, 0, 60,60);
		buttonAddNewMonth.setBackground(SWTResourceManager.getColor(255, 235, 205));
		buttonAddNewMonth.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/addRecord.png"), 60, 60));
		buttonAddNewMonth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormAddMonth dialog = new FormAddMonth(shell, shell.getStyle());
				shell.setEnabled(false);
				Date date = dialog.open();
				if(date==null)return;
				int year = date.getYear();
				int month = date.getMonth();
				Button button = new Button(compositeRecordMonth,SWT.PUSH);
				button.setBounds(5, 68 + (buttonRecordMonth.size()) * 58, 228, 48);
				button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 20, 20));
				button.setText(year + " / " + month);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						openRecord(year,month);
					}
				});
				buttonRecordMonth.add(button);
				
				RecordFileCreater creater = new RecordFileCreater(Main.currentAccount);
				try {
					creater.createFile(year, month);
					Debug.log(this.getClass(), "create new month:%s",button.getText());
					Debug.log(this.getClass(), "list.length=%d",buttonRecordMonth.size());
				} catch (FileExistException e1) {
					MessageBox msg = new MessageBox(shell,SWT.ICON|SWT.ICON_INFORMATION);
					msg.setText("錯誤");
					msg.setMessage("該月份已經存在");
					msg.open();
				}
				refreshRecordButton();
				
			}
		});
		
		
		for (File file : Main.recordFiles) {
			String fileName = FileHelper.getFileName(file);
			if(fileName.matches("[0-9]+") == false || fileName.length() != 6)continue;
			Button button = new Button(compositeRecordMonth,SWT.PUSH);
			int num = Integer.valueOf(fileName);
			int year = num /100;
			int month = num % 100;
			button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 20, 20));
			button.setText(year + " / " + month);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					openRecord(year,month);
				}
			});
			buttonRecordMonth.add(button);
			
			
			Debug.log(this.getClass(), "create new button:%s",button.getText());
			Debug.log(this.getClass(), "list.length=%d",buttonRecordMonth.size());
		}
	}
	private void refreshRecordButton() {
		buttonRecordMonth.sort(new Comparator<Button>() {
			@Override
			public int compare(Button o1, Button o2) {
				String arr1[] = o1.getText().split(" / ");
				String arr2[] = o2.getText().split(" / ");
				int year1 = Integer.valueOf(arr1[0]);
				int year2 = Integer.valueOf(arr2[0]);
				int month1 = Integer.valueOf(arr1[1]);
				int month2 = Integer.valueOf(arr2[1]);
				int result = -Integer.compare(year1, year2);
				if(result == 0)return -Integer.compare(month1, month2);
				return result;
			}
		});
		refreshRecordButton(0);
	}
	private void refreshRecordButton(int scrollRate) {
		int len = buttonRecordMonth.size();
		int maxHeight = 10 + len*58;
		int delta = maxHeight - 268;
		int i = 0;
		if(delta == 0) {
			for (Button button : buttonRecordMonth) {
				button.setBounds(5, (10 + i* 58)  , 228, 48);
				i++;
			}
		}else {
			for (Button button : buttonRecordMonth) {
				//Debug.log(this.getClass(), "button=%s",button.getText());
				button.setBounds(5, (int)((10 + i* 58) - ((delta * scrollRate)/100)) , 228, 48);
				i++;
			}
		}
		
	}
	
	private void openRecord(int year, int month) {
		Debug.log(this.getClass(), "open record(%d/%d)",year,month);
		RecordFileGetter fileGetter = new RecordFileGetter(Main.currentAccount, year,month);
		try {
			File file = fileGetter.getFile();
		} catch (FileNotFoundException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		
	}
	
}
