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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import clwhthr.io.CSVCreater;
import clwhthr.io.CSVGetter;
import clwhthr.io.CSVReader;
import clwhthr.io.RecordFileCreater;
import clwhthr.io.RecordFileGetter;
import clwhthr.io.RecordFileWriter;
import clwhthr.io.file.CSVFile;
import clwhthr.main.Main;
import clwhthr.setting.Config;
import clwhthr.util.Date;
import clwhthr.util.Debug;
import clwhthr.util.FileHelper;
import clwhthr.util.Record;
import clwhthr.util.Record.Type;
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
import org.eclipse.swt.events.TouchListener;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class FormMain {

	protected Shell shell;
	protected Composite composite;
	protected Composite compositeRecordDay;
	protected Composite compositeOption;
	protected Button buttonSearch;
	protected Button buttonAddRecordMonth;
	private Button buttonAddRecord;
	
	
	protected List<Button> buttonRecordMonth;
	private Table table;
	private TableColumn columnDate;
	private TableColumn columnMoney;
	private TableColumn columnSpendType;
	private TableColumn columnIndex;
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
	
	
	private Composite compositeBarChart;
	private Composite compositePieChart;
	private Composite compositeLineChart;
	private Label labelAverage;
	private Text textAverage;
	private Button buttonOperationCheckAll;
	
	
	
	
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
		recordItems = new ArrayList<TableItem>();
		recordList = new ArrayList<Record>();
		checkedItems = new ArrayList<TableItem>();
		
		shell = new Shell(SWT.CLOSE | SWT.MIN | SWT.TITLE );
		shell.setText("Bookkeeper");
		shell.setSize(size.width, size.height);
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
		lableDate.setText("0000\u5E7400\u6708");
		
		tabFolder = new TabFolder(compositeRecordDay, SWT.NONE);
		tabFolder.setBounds(10, 61, 542, 662);
		
		tabItemTable = new TabItem(tabFolder, SWT.NONE);
		tabItemTable.setText("\u8A73\u7D30\u8CC7\u6599");
		
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
				checkedItems = getCheckedItems(table);
				buttonOperationDelete.setEnabled(checkedItems.size() > 0 ? true : false);
			}
		});
		
		
		columnIndex = new TableColumn(table, SWT.CENTER);
		columnIndex.setResizable(false);
		columnIndex.setWidth(63);
		
		columnDate = new TableColumn(table, SWT.CENTER);
		columnDate.setResizable(false);
		columnDate.setWidth(74);
		columnDate.setText("\u65E5\u671F");
		
		columnSpendType = new TableColumn(table, SWT.CENTER);
		columnSpendType.setResizable(false);
		columnSpendType.setWidth(117);
		columnSpendType.setText("\u6D88\u8CBB\u985E\u5225");
		
		columnMoney = new TableColumn(table, SWT.CENTER);
		columnMoney.setResizable(false);
		columnMoney.setWidth(100);
		columnMoney.setText("\u91D1\u984D");
		
		columnNote = new TableColumn(table, SWT.NONE);
		columnNote.setResizable(false);
		columnNote.setWidth(147);
		columnNote.setText("\u5099\u8A3B");
		
		Label labelTitleAnalyse = new Label(compositeTable, SWT.NONE);
		labelTitleAnalyse.setFont(SWTResourceManager.getFont("Microsoft JhengHei UI", 12, SWT.NORMAL));
		labelTitleAnalyse.setBounds(10, 396, 80, 25);
		labelTitleAnalyse.setText("\u7D71\u8A08\u8CC7\u6599");
		
		Label labelSum = new Label(compositeTable, SWT.NONE);
		labelSum.setBounds(10, 427, 45, 19);
		labelSum.setText("\u7E3D\u82B1\u8CBB");
		
		textSpendSum = new Text(compositeTable, SWT.NONE);
		textSpendSum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textSpendSum.setText("0");
		textSpendSum.setEditable(false);
		textSpendSum.setBounds(97, 427, 114, 25);
		
		labelAverage = new Label(compositeTable, SWT.NONE);
		labelAverage.setText("\u5E73\u5747\u82B1\u8CBB");
		labelAverage.setBounds(10, 458, 60, 19);
		
		textAverage = new Text(compositeTable, SWT.NONE);
		textAverage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textAverage.setText("0");
		textAverage.setEditable(false);
		textAverage.setBounds(97, 458, 114, 25);
		
		buttonOperationCheckAll = new Button(compositeTable, SWT.NONE);
		buttonOperationCheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Arrays.stream(table.getItems()).forEach(item -> item.setChecked(true));
				table.notifyListeners(SWT.Selection, new Event());
			}
		});
		buttonOperationCheckAll.setBounds(0, 361, 94, 29);
		buttonOperationCheckAll.setText("\u5168\u90E8\u9078\u53D6");
		
		tabItemBarChart = new TabItem(tabFolder, SWT.NONE);
		tabItemBarChart.setText("\u9577\u689D\u5716");
		
		compositeBarChart = new Composite(tabFolder, SWT.BORDER);
		tabItemBarChart.setControl(compositeBarChart);
		
		tabItemPieChart = new TabItem(tabFolder, SWT.NONE);
		tabItemPieChart.setText("\u5713\u9905\u5716");
		
		compositePieChart = new Composite(tabFolder, SWT.BORDER);
		tabItemPieChart.setControl(compositePieChart);
		
		tabItemLineChart = new TabItem(tabFolder, SWT.NONE);
		tabItemLineChart.setText("\u6298\u7DDA\u5716");
		
		compositeLineChart = new Composite(tabFolder, SWT.BORDER);
		tabItemLineChart.setControl(compositeLineChart);
		
		buttonOperationDelete = new Button(compositeRecordDay, SWT.NONE);
		buttonOperationDelete.setBounds(234, 10, 45, 45);
		buttonOperationDelete.setBackground(FormHelper.BACKGROUND);
		buttonOperationDelete.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/delete.png"), 45, 45));
		buttonOperationDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				List<TableItem>checked = checkedItems;
				int len = checked.size();
				MessageBox dialog = new MessageBox(shell,SWT.ICON_QUESTION | SWT.CANCEL | SWT.OK);
				dialog.setText("");
				dialog.setMessage("請問是否要刪除這"+ len + "筆資料?");
				int result = dialog.open();
				if(result == SWT.CANCEL)return;
				List<Record> records = recordList;
				int year = Integer.valueOf(FileHelper.getFileName(currentRecordFile))/100;
				checked.stream().forEach(item ->{
					String string = item.getText(0)+item.getText(1)+item.getText(2)+item.getText(3)+item.getText(4)+item.getText(5)+item.getText(6);
					Debug.log(this.getClass(), "item=" + string);
					int index = Integer.valueOf(item.getText(0));
					int month = Integer.valueOf(item.getText(1).split("/")[0]);
					int day = Integer.valueOf(item.getText(1).split("/")[1]);
					Date date = null;
					try {
						date = new Date(year, month, day);
					} catch (DateFormatException e) {
						// TODO 自動產生的 catch 區塊
						e.printStackTrace();
					}
					Record.Type type = Record.Type.valueOf(item.getText(2));
					int money = Integer.valueOf(item.getText(3));
					String note = item.getText(4);
					if(note.length() == 0 || note.equals(""))note = "none";
					Record record = new Record(date, type, money, note);
					Debug.log(this.getClass(), "remove " + record.toString());
					boolean del = records.remove(record);
					if(del)Debug.log(this.getClass(), "delete successful");
					else Debug.log(this.getClass(), "delete failed");
				});
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
				
				updateRecordTable();
				
			}
		});
		buttonOperationDelete.setEnabled(false);
		
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
		compositeOption.setBackground(FormHelper.BACKGROUND);
		compositeOption.setBounds(10, 10, 864, 64);
		
		
		buttonSearch = new Button(compositeOption, SWT.NONE);
		buttonSearch.setBounds(76, 0, 60, 60);
		buttonSearch.setBackground(FormHelper.BACKGROUND);
		buttonSearch.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/search.png"), 60, 60));
		buttonSearch.addMouseTrackListener(new MouseTrackListener() {
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
		
		buttonAddRecord = new Button(compositeOption, SWT.NONE);
		buttonAddRecord.setBounds(142, 0, 60, 60);
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
					updateRecordTable();
					
				} catch (FileNotFoundException e) {
					MessageBox error = new MessageBox(shell,SWT.ICON_WARNING);
					error.setText("錯誤");
					error.setMessage("該月份之紀錄檔不存在\n請新增");
					error.open();
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自動產生的 catch 區塊
					e.printStackTrace();
				}
				
				
			}
		});
		buttonAddRecord.setImage(ImageHelper.resizeImage(shell.getDisplay(), SWTResourceManager.getImage(FormMain.class, "/assets/clwhthr/gui/image/add.png"), 45, 45));
		
		
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
					msg.setText("錯誤");
					msg.setMessage("該月份已經存在");
					msg.open();
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
		lableDate.setText(year + "年" + month + "月");
		RecordFileGetter fileGetter = new RecordFileGetter(Main.currentAccount, year,month);
		try {
			currentRecordFile = fileGetter.getFile();
		} catch (FileNotFoundException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		
		updateRecordTable();
		
	}
	private void updateRecordTable() {
		clearRecordTable();
		if(currentRecordFile == null) {
			
		}else {
			Debug.log(this.getClass(), "update table : %s",FileHelper.getFileName(currentRecordFile));
			try {
				CSVReader reader = new CSVReader(currentRecordFile);
				List<String[]> list = reader.getContents();
				Debug.log(this.getClass(), "list.size=%d",list.size());
				reader.close();
				int count = 0;
				int totalday = 0;
				int spendSum = 0;
				Iterator<String[]> iterator = list.iterator();
				while(iterator.hasNext()) {
					String[] array = iterator.next();
					Date date = new Date(Integer.valueOf(array[0]), Integer.valueOf(array[1]), Integer.valueOf(array[2]));
					Record.Type type = Record.Type.values()[Integer.valueOf(array[3])];
					int money = Integer.valueOf(array[4]);
					String note = array[5];
					recordList.add(new Record(date,type,money,note));
					TableItem recorditem = new TableItem(table, SWT.NONE);
					recorditem.setText(0, String.valueOf(count));
					recorditem.setText(1, date.getMonth() + "/" + date.getDay());
					recorditem.setText(2, type.name());
					recorditem.setText(3,String.valueOf(money));
					
					if(note.equals("none") == false)recorditem.setText(4,note);
					else recorditem.setText(4,"");
					
					count++;
					if(totalday < date.getDay())totalday = date.getDay();
					spendSum += money;
				}
				
				textSpendSum.setText(String.valueOf(spendSum));
				textAverage.setText(String.valueOf(Math.round((float)spendSum / (float)totalday)));
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	private void clearRecordTable() {
		recordList.clear();
		checkedItems.clear();
		table.removeAll();
	}
	private List<TableItem> getCheckedItems(Table table) {
		return Arrays.stream(table.getItems()).filter(item -> item.getChecked()).collect(Collectors.toList());
	}
}
