package clwhthr.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;
import clwhthr.util.form.FormHelper;

public class Record{
	
	private Date date;
	private int money;
	private Type type;
	private String note;
	public Record(Date date,Type type,int money,String note) {
		this.date = date;
		this.money = money;
		this.type = type;
		this.note = note;
		if(note.length() == 0 || note.equals(""))note = "none";
	}
	public Date getDate() {
		return this.date;
	}
	public int getMoney() {
		return this.money;
	}
	public Type getType() {
		return this.type;
	}
	public String getNote() {
		return this.note;
	}
	public String[] toStringArray() {
		List<String>list = new ArrayList<String>();
		list.add(String.valueOf(date.getYear()));
		list.add(String.valueOf(date.getMonth()));
		list.add(String.valueOf(date.getDay()));
		list.add(String.valueOf(type.ordinal()));
		list.add(String.valueOf(money));
		if(note != null || note.equals("") == false)list.add(note);
		else list.add("none");
		
		String array[] = new String[list.size()];
		list.toArray(array);
		return array;
	}
	@Override
	public String toString() {
		return date.toString() + type.toString() + money + note;
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Record == false)return false;
		return this.hashCode() == obj.hashCode();
	}
	public enum Type{
		
		food(FormHelper.GREEN),
		traffic(FormHelper.BLUE),
		social(FormHelper.RED),
		shopping(FormHelper.YELLOW),
		entertainment(FormHelper.PURPLE),
		other(FormHelper.ORANGE);
		
		private Color color = null;
		private Type(Color color) {
			this.color = color;
		}
		public static String[] names() {
			Type[] types = Type.values();
			String[] list = new String[types.length];
			for(int i = 0;i<list.length;i++) {
				list[i] = types[i].name();
			}
			return list;
		}
		public String getLocalName() {
			return this.name();
		}
		public Color getColor() {
			return this.color;
		}
		
	}
	
}
