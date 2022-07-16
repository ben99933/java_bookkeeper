package clwhthr.util;

import java.io.Serializable;

public class Record{
	
	private Date date;
	private int money;
	private Type type;
	
	public Record(Date date,int money,Type type) {
		this.date = date;
		this.money = money;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return date.toString() + type.toString() + money;
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
		food,traffic,social,shopping,entertainment,other;
	}
}
