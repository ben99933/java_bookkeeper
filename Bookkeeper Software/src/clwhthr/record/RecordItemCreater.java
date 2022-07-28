package clwhthr.record;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import clwhthr.util.Date;

public class RecordItemCreater {
	
	private Record record;
	public RecordItemCreater(Record record) {
		this.record = record;
	}
	//§ârecordÂà´«¦¨TableItem
	public TableItem create(Table table) {
		TableItem item = new TableItem(table, SWT.NONE);
		Date date = record.getDate();
		item.setText(0, date.getMonth() + "/" + date.getDay());
		item.setText(1, record.getType().getLocalName());
		item.setText(2,String.valueOf(record.getMoney()));
		if(record.getNote().equals("none") == false)item.setText(3,record.getNote());
		else item.setText(3,"");
		return item;
	}
}
