package clwhthr.record;

import org.eclipse.swt.widgets.TableItem;

import clwhthr.exception.DateFormatException;
import clwhthr.util.Date;
import clwhthr.util.Debug;

public class ItemRecordAdapter{

	private Record record;
	public ItemRecordAdapter(int year, TableItem item) {
		int month = Integer.valueOf(item.getText(0).split("/")[0]);
		int day = Integer.valueOf(item.getText(0).split("/")[1]);
		Date date = null;
		try {
			date = new Date(year, month, day);
		} catch (DateFormatException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		Record.Type type = Record.Type.valueOfLocalname(item.getText(1));
		int money = Integer.valueOf(item.getText(2));
		String note = item.getText(3);
		if(note.length() == 0 || note.equals(""))note = "none";
		Record record = new Record(date, type, money, note);
		this.record = record;
	}
	public Record getRecord() {
		Debug.log(this.getClass(), record.toString());
		return this.record;
	}

}
