package clwhthr.form;

import java.awt.Scrollbar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import clwhthr.exception.DateFormatException;
import clwhthr.exception.FileExistException;
import clwhthr.exception.FileFormatException;
import clwhthr.form.dialog.FormAbout;
import clwhthr.form.dialog.FormAddMonth;
import clwhthr.form.dialog.FormAddRecord;
import clwhthr.form.dialog.FormEditRecord;
import clwhthr.form.dialog.FormSearchMonth;
import clwhthr.form.dialog.FormSetting;
import clwhthr.io.CSVCreater;
import clwhthr.io.CSVGetter;
import clwhthr.io.CSVReader;
import clwhthr.io.RecordFileCreater;
import clwhthr.io.RecordFileGetter;
import clwhthr.io.RecordFileWriter;
import clwhthr.io.file.CSVFile;
import clwhthr.main.Main;
import clwhthr.record.ItemRecordAdapter;
import clwhthr.record.Record;
import clwhthr.record.RecordItemCreater;
import clwhthr.record.Record.Type;
import clwhthr.resources.I18n;
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
import org.eclipse.ui.forms.editor.FormEditor;
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
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.graphics.Color;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.ICircularSeries;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.ISeriesSet;
import org.eclipse.swtchart.extensions.core.IChartSeriesData;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class FormMain {

	protected Shell shell;
	private Composite composite;
	private Composite compositeRecordDay;
	private Composite compositeOption;
	private Button buttonSearchMonth;
	private Button buttonAddRecordMonth;
	private Button buttonAddRecord;
	private Button buttonSetting;
	
	private Composite compositeRecordMonth;
	protected List<Button> buttonRecordMonth;
	private Table table;
	private TableEditor tableEditor;
	private TableColumn columnDate;
	private TableColumn columnMoney;
	private TableColumn columnSpendType;
	private TableColumn columnNote;
	private Label lableDate;
	
	private File currentRecordFile = null;
	private List<TableItem> recordItems;
	private List<TableItem>checkedItems;
	private List<Record> recordList;
	private TabFolder tabFolder;
	private TabItem tabItemBarChart;
	private TabItem tabItemTable;
	private TabItem tabItemPieChart;
	private TabItem tabItemLineChart;
	private Composite compositeTable;
	private Text textSpendSum;
	private Button buttonOperationDelete;
	private Button buttonEditRecord;
	
	
	private Composite compositeBarChart;
	private Composite compositePieChart;
	private Composite compositeLineChart;
	private Label labelAverage;
	private Text textAverage;
	private Button buttonOperationCheckAll;
	
	private Chart barChart;
	private Chart lineChart;
	private Chart pieChart;
	
	private Set<Integer> daySet = new HashSet<Integer>();
	private int currentYear = 0;
	private int currentMonth = 0;
	private int maxDay =  0;
	private Button buttonAboutUs;
	
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
		recordItems = new ArrayList<TableItem>();
		recordList = new ArrayList<Record>();
		checkedItems = new ArrayList<TableItem>();
		
		shell = new Shell(SWT.CLOSE | SWT.MIN | SWT.TITLE );
		shell.setText(I18n.format("form.main.shell.name", new Object[0]));
		shell.setSize(900, 900);
		shell.setBackground(FormHelper.BACKGROUND);
		shell.setLayout(null);
		
		composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(10, 80, 864, 765);
		composite.setBackground(FormHelper.BACKGROUND);
		
		compositeRecordDay = new Composite(composite, SWT.BORDER);
		compositeRecordDay.setBackground(FormHelper.BACKGROUND);
		compositeRecordDay.setBounds(284, 10, 566, 737);
		
		lableDate = new Label(compositeRecordDay, SWT.NONE);
		lableDate.setBackground(FormHelper.BACKGROUND);
		lableDate.setFont(SWTResourceManager.getFont("Microsoft JhengHei UI", 24, SWT.NORMAL));
		lableDate.setBounds(10, 10, 218, 45);
		lableDate.setText(I18n.format("form.main.record.label.date","0000","00"));
		
		tabFolder = new TabFolder(compositeRecordDay, SWT.NONE);
		tabFolder.setBounds(10, 61, 542, 426);
		
		tabItemTable = new TabItem(tabFolder, SWT.NONE);
		tabItemTable.setText(I18n.format("form.main.tabitem.detail.name", new Object[0]));
		
		compositeTable = new Composite(tabFolder, SWT.BORDER);
		tabItemTable.setControl(compositeTable);
		
		table = new Table(compositeTable, SWT.BORDER|SWT.CHECK);
		table.setLocation(0, 0);
		table.setSize(534, 362);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateOperationButton();
			}
		});
		
		columnDate = new TableColumn(table, SWT.CENTER);
		columnDate.setResizable(false);
		columnDate.setWidth(74);
		columnDate.setText(I18n.format("form.main.table.column.date.name", new Object[0]));
		
		columnSpendType = new TableColumn(table, SWT.CENTER);
		columnSpendType.setResizable(false);
		columnSpendType.setWidth(117);
		columnSpendType.setText(I18n.format("form.main.table.column.type.name", new Object[0]));
		
		columnMoney = new TableColumn(table, SWT.CENTER);
		columnMoney.setResizable(false);
		columnMoney.setWidth(100);
		columnMoney.setText(I18n.format("form.main.table.column.money.name", new Object[0]));
		
		columnNote = new TableColumn(table, SWT.NONE);
		columnNote.setResizable(false);
		columnNote.setWidth(147);
		columnNote.setText(I18n.format("form.main.table.column.note.name", new Object[0]));
		
		buttonOperationCheckAll = new Button(compositeTable, SWT.NONE);
		buttonOperationCheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Arrays.stream(table.getItems()).forEach(item -> item.setChecked(true));
				table.notifyListeners(SWT.Selection, new Event());
			}
		});
		buttonOperationCheckAll.setBounds(0, 361, 94, 29);
		buttonOperationCheckAll.setText(I18n.format("form.main.button.checkAll.name", new Object[0]));
		
		tabItemBarChart = new TabItem(tabFolder, SWT.NONE);
		tabItemBarChart.setText(I18n.format("form.main.tabitem.barChart.name", new Object[0]));
		
		compositeBarChart = new Composite(tabFolder, SWT.BORDER);
		tabItemBarChart.setControl(compositeBarChart);
		barChart = new Chart(compositeBarChart, SWT.NONE);
		barChart.getTitle().setText(I18n.format("form.main.chart.bar.title.name"));
		barChart.getAxisSet().getXAxis(0).getTitle().setText(I18n.format("record.date.name"));
		barChart.getAxisSet().getYAxis(0).getTitle().setText(I18n.format("record.money.name"));
		barChart.setBounds(10, 10, 510, 370);
		
		
		
		tabItemPieChart = new TabItem(tabFolder, SWT.NONE);
		tabItemPieChart.setText(I18n.format("form.main.tabitem.pieChart.name", new Object[0]));
		
		compositePieChart = new Composite(tabFolder, SWT.BORDER);
		tabItemPieChart.setControl(compositePieChart);
		pieChart = new Chart(compositePieChart, SWT.NONE);
		pieChart.getTitle().setText(I18n.format("form.main.chart.pie.title.name"));
		pieChart.setBounds(10, 10, 510, 370);
		
		
		tabItemLineChart = new TabItem(tabFolder, SWT.NONE);
		tabItemLineChart.setText(I18n.format("form.main.tabitem.lineChart.name", new Object[0]));
		
		compositeLineChart = new Composite(tabFolder, SWT.BORDER);
		tabItemLineChart.setControl(compositeLineChart);
		lineChart = new Chart(compositeLineChart, SWT.NONE);
		lineChart.getTitle().setText(I18n.format("form.main.chart.line.title.name"));
		lineChart.getAxisSet().getXAxis(0).getTitle().setText(I18n.format("record.date.name"));
		lineChart.getAxisSet().getYAxis(0).getTitle().setText(I18n.format("record.money.name"));
		lineChart.setBounds(10, 10, 510, 370);
		
		
		
		buttonOperationDelete = new Button(compositeRecordDay, SWT.NONE);
		buttonOperationDelete.setBounds(285, 10, 45, 45);
		buttonOperationDelete.setBackground(FormHelper.BACKGROUND);
		buttonOperationDelete.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/delete.png"), 45, 45));
		buttonOperationDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				List<TableItem>checked = checkedItems;
				List<Record> records = recordList;
				int len = checked.size();
				MessageBox dialog = new MessageBox(shell,SWT.ICON_QUESTION | SWT.CANCEL | SWT.OK);
				dialog.setText("");
				dialog.setMessage("請問是否要刪除這"+ len + "筆資料?");
				int result = dialog.open();
				if(result == SWT.CANCEL)return;
				
				checked.stream().forEach(item ->{
					Record record = new ItemRecordAdapter(currentYear, item).getRecord();
					Debug.log(this.getClass(), "remove " + record.toString());
					boolean del = records.remove(record);
					if(del)Debug.log(this.getClass(), "delete successful");
					else Debug.log(this.getClass(), "delete failed");
				});
				
				saveCurrentRecord(records);
				updateRecord();
				
			}
		});
		buttonOperationDelete.setEnabled(false);
		
		Label labelTitleAnalyse = new Label(compositeRecordDay, SWT.NONE);
		labelTitleAnalyse.setBounds(10, 493, 150, 25);
		labelTitleAnalyse.setFont(SWTResourceManager.getFont("Microsoft JhengHei UI", 12, SWT.NORMAL));
		labelTitleAnalyse.setText(I18n.format("form.main.record.label.statistical.name", new Object[0]));
		labelTitleAnalyse.setBackground(FormHelper.BACKGROUND);
		
		Label labelSum = new Label(compositeRecordDay, SWT.NONE);
		labelSum.setBounds(10, 524, 119, 19);
		labelSum.setText(I18n.format("form.main.record.label.sum.name"));
		labelSum.setBackground(FormHelper.BACKGROUND);
		
		labelAverage = new Label(compositeRecordDay, SWT.NONE);
		labelAverage.setBounds(10, 555, 119, 19);
		labelAverage.setText(I18n.format("form.main.record.label.average.name"));
		labelAverage.setBackground(FormHelper.BACKGROUND);
		
		textAverage = new Text(compositeRecordDay, SWT.NONE);
		textAverage.setBounds(148, 555, 80, 25);
		textAverage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textAverage.setText("0");
		textAverage.setEditable(false);
		textAverage.setBackground(FormHelper.BACKGROUND);
		
		textSpendSum = new Text(compositeRecordDay, SWT.NONE);
		textSpendSum.setBounds(148, 524, 80, 25);
		textSpendSum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textSpendSum.setText("0");
		textSpendSum.setEditable(false);
		textSpendSum.setBackground(FormHelper.BACKGROUND);
		
		buttonEditRecord = new Button(compositeRecordDay, SWT.NONE);
		buttonEditRecord.setBackground(FormHelper.BACKGROUND);
		buttonEditRecord.setBounds(234, 10, 45, 45);
		buttonEditRecord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				List<TableItem> checkItems = checkedItems;
				TableItem item = checkItems.get(0);
				Record recordOld = new ItemRecordAdapter(currentYear, item).getRecord();
				FormEditRecord dialog = new FormEditRecord(shell, recordOld);
				shell.setEnabled(false);
				Record recordNew = dialog.open();
				if(recordNew == null)return;
				
				recordList.remove(recordOld);
				recordList.add(recordNew);
				saveCurrentRecord(recordList);
				
				updateRecord();
			}
		});
		buttonEditRecord.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/edit.png"), 45, 45));
		buttonEditRecord.setEnabled(false);
		
		compositeRecordMonth = new Composite(composite, SWT.BORDER | SWT.V_SCROLL | SWT.EMBEDDED);
		compositeRecordMonth.setBounds(14, 10, 264, 737);
		compositeRecordMonth.setLayout(null);
		
		ScrollBar scrollBarMonth = compositeRecordMonth.getVerticalBar();
		scrollBarMonth.addSelectionListener(new SelectionListener() {
		    public void widgetDefaultSelected(SelectionEvent e) {}
		    public void widgetSelected(SelectionEvent e) {
		        if (e.detail == SWT.NONE || e.detail == SWT.ARROW_UP || e.detail == SWT.ARROW_DOWN) {
		            refreshRecordButton(scrollBarMonth.getSelection());
		        }
		    }
		});
		
		
		compositeOption = new Composite(shell, SWT.BORDER);
		compositeOption.setBackground(FormHelper.BACKGROUND);
		compositeOption.setBounds(10, 10, 864, 64);
		
		
		buttonSearchMonth = new Button(compositeOption, SWT.NONE);
		buttonSearchMonth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormSearchMonth dialog = new FormSearchMonth(shell);
				shell.setEnabled(false);
				Date date = dialog.open();
				if(date == null)return;
				openRecord(date.getYear(),date.getMonth());
			}
		});
		buttonSearchMonth.setBounds(76, 0, 60, 60);
		buttonSearchMonth.setBackground(FormHelper.BACKGROUND);
		buttonSearchMonth.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/search.png"), 60, 60));
		buttonSearchMonth.addMouseTrackListener(new MouseTrackListener() {
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
		
		
		buttonAddRecordMonth = new Button(compositeOption,SWT.PUSH);
		buttonAddRecordMonth.setBounds(10, 0, 60,60);
		buttonAddRecordMonth.setBackground(FormHelper.BACKGROUND);
		buttonAddRecordMonth.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/addRecord.png"), 60, 60));
		buttonAddRecordMonth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormAddMonth dialog = new FormAddMonth(shell, shell.getStyle());
				shell.setEnabled(false);
				Date date = dialog.open();
				if(date==null)return;
				
				int year = date.getYear();
				int month = date.getMonth();
				RecordFileCreater creater = new RecordFileCreater(Main.currentAccount);
				try {
					creater.createFile(year, month);
				} catch (FileExistException e1) {
					MessageBox msg = new MessageBox(shell,SWT.ICON|SWT.ICON_INFORMATION);
					msg.setText(I18n.format("msg.error.title.name"));
					msg.setMessage(I18n.format("msg.existMonth.text"));
					msg.open();
					e1.printStackTrace();
					return;
				}
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
				
				refreshRecordButton();
				
			}
		});
		
		buttonAddRecord = new Button(compositeOption, SWT.NONE);
		buttonAddRecord.setBounds(142, 0, 60, 60);
		buttonAddRecord.setBackground(FormHelper.BACKGROUND);
		buttonAddRecord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FormAddRecord dialog = new FormAddRecord(shell);
				shell.setEnabled(false);
				Record record = dialog.open();
				if(record == null)return;
				Debug.log(this.getClass(),"add record:%s",record.toString());
				try {
					File csv = new RecordFileGetter(Main.currentAccount, record.getDate().getYear(), record.getDate().getMonth()).getFile();
					Debug.log(this.getClass(), "file path:%s",csv.getPath());
					RecordFileWriter writer = new RecordFileWriter(csv,true);
					writer.write(record.toStringArray());
					writer.close();
					updateRecord();
					
				} catch (FileNotFoundException e) {
					MessageBox error = new MessageBox(shell,SWT.ICON_WARNING);
					error.setText(I18n.format("msg.error.title.name"));
					error.setMessage(I18n.format("msg.error.addrecord.fileNoFound.text"));
					error.open();
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自動產生的 catch 區塊
					e.printStackTrace();
				}
				
				
			}
		});
		buttonAddRecord.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/add.png"), 45, 45));
		
		buttonSetting = new Button(compositeOption, SWT.NONE);
		buttonSetting.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormSetting dialog = new FormSetting(shell);
				shell.setEnabled(false);
				dialog.open();
			}
		});
		buttonSetting.setBounds(208, 0, 60, 60);
		buttonSetting.setBackground(FormHelper.BACKGROUND);
		buttonSetting.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/setting.png"), 60, 60));
		
		buttonAboutUs = new Button(compositeOption, SWT.NONE);
		buttonAboutUs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FormAbout dialog = new FormAbout(shell);
				shell.setEnabled(false);
				dialog.open();
			}
		});
		buttonAboutUs.setBounds(274, 0, 60, 60);
		buttonAboutUs.setBackground(FormHelper.BACKGROUND);
		buttonAboutUs.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/info.png"), 60, 60));
		
		initRecordMonthButton();
		
	}
	private void updateOperationButton() {
		checkedItems = FormHelper.getCheckedItems(table);
		int amount = checkedItems.size();
		buttonOperationDelete.setEnabled(amount > 0 ? true : false);
		buttonEditRecord.setEnabled(amount == 1 ? true : false);
	}
	private void saveCurrentRecord(List<Record> records) {
		RecordFileWriter writer = null;
		try {
			writer = new RecordFileWriter(currentRecordFile, false);
			for (Record record : records) {
				writer.write(record.toStringArray());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		writer.close();
		
	}
	private void initRecordMonthButton() {

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
			//Debug.log(this.getClass(), "create new button:%s",button.getText());
			//Debug.log(this.getClass(), "list.length=%d",buttonRecordMonth.size());
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
			currentRecordFile = fileGetter.getFile();
		} catch (FileNotFoundException e) {
			MessageBox dialog = new MessageBox(shell);
			dialog.setText(I18n.format("msg.error.title.name"));
			dialog.setMessage(I18n.format("msg.error.fileNoFound.text"));
			int result = dialog.open();
			e.printStackTrace();
			return;
		}
		lableDate.setText(I18n.format("form.main.record.label.date",String.valueOf(year),String.valueOf(month)));
		currentMonth = month;
		currentYear = year;
		updateRecord();
	}
	private void updateRecord() {
		shell.setEnabled(false);
		daySet.clear();
		updateRecordTable();
		updateChart();
		updateOperationButton();
		tabFolder.setSelection(0);
		shell.setEnabled(true);
	}
	private void updateRecordTable() {
		clearRecordTable();
		if(currentRecordFile == null) {
			
		}else {
			Debug.log(this.getClass(), "update table : %s",FileHelper.getFileName(currentRecordFile));
			try {
				CSVReader reader = new CSVReader(currentRecordFile);
				List<String[]> list = reader.getContents();
				//Debug.log(this.getClass(), "list.size=%d",list.size());
				reader.close();
				

				int spendSum = 0;
				Iterator<String[]> iterator = list.iterator();
				while(iterator.hasNext()) {
					String[] array = iterator.next();
					Date date = new Date(Integer.valueOf(array[0]), Integer.valueOf(array[1]), Integer.valueOf(array[2]));
					Record.Type type = Record.Type.values()[Integer.valueOf(array[3])];
					int money = Integer.valueOf(array[4]);
					String note = array[5];
					recordList.add(new Record(date,type,money,note));
					if(maxDay < date.getDay())maxDay = date.getDay();
					spendSum += money;
					daySet.add(date.getDay());
				}
				
				recordList.sort(Comparator.comparing(Record::getDate).thenComparing(Record::getMoney));
				for (Record record : recordList) {
					RecordItemCreater adapter = new RecordItemCreater(record);
					TableItem item = adapter.create(table);
					
				}
				textSpendSum.setText(String.valueOf(spendSum));
				if(daySet.size() != 0)textAverage.setText(String.valueOf(Math.round((float)spendSum / (float)daySet.size())));
				else textAverage.setText("0");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void updateChart() {
		Record.Type types[] = Record.Type.values();
		double typeMoneySum[] = new double[types.length];
		for(int i = 0;i<typeMoneySum.length;i++)typeMoneySum[i] = 0;
		double moneyDailySum[] = new double[maxDay];
		for(int i = 0;i<moneyDailySum.length;i++)moneyDailySum[i] = 0;
		String typeName[] = new String[types.length];
		for(int i = 0;i<types.length;i++)typeName[i] = types[i].getLocalName();
		int moneySum = 0;
		
		if(recordList.size()!=0) {
			Record[] records = new Record[recordList.size()];
			recordList.toArray(records);
			for(int i = 0;i<records.length;i++) {
				Record record = records[i];
				moneySum += record.getMoney();
				typeMoneySum[record.getType().ordinal()] += record.getMoney();
				moneyDailySum[record.getDate().getDay()-1] += record.getMoney();
			}
		}
		//chart
		try {
			barChart.getSeriesSet().deleteSeries("bar series");
			lineChart.getSeriesSet().deleteSeries("line series");
			pieChart.getSeriesSet().deleteSeries("pie series");
		}catch (Exception e) {
			e.printStackTrace();
		}
		IBarSeries<?> barSeries = (IBarSeries<?>)barChart.getSeriesSet().createSeries(SeriesType.BAR,"bar series");
		ILineSeries<?> lineSeries = (ILineSeries<?>)lineChart.getSeriesSet().createSeries(SeriesType.LINE,"line series");
		ICircularSeries<?> pieSeries = (ICircularSeries<?>)pieChart.getSeriesSet().createSeries(SeriesType.PIE,"pie series");
		//長條圖
		barSeries.setYSeries(moneyDailySum);
		barChart.getAxisSet().adjustRange();
		Debug.log(this.getClass(), "bar chart complete");
		//折線圖
		lineSeries.setYSeries(moneyDailySum);
		lineChart.getAxisSet().adjustRange();
		Debug.log(this.getClass(), "line chart complete");
		//圓餅圖
		pieSeries.setSeries(typeName, typeMoneySum);
		
		Debug.log(this.getClass(), "pie chart complete");
		
	}
	private void clearRecordTable() {
		recordList.clear();
		checkedItems.clear();
		table.removeAll();
	}
}
