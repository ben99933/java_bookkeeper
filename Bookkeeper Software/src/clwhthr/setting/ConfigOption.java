package clwhthr.setting;

import java.io.Serializable;

public class ConfigOption<T>{
	
	private String name;
	private String value;
	
	public ConfigOption(String name, String value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return name + "=" + value.toString();
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ConfigOption))return false;
		return this.hashCode() == obj.hashCode();
	}
}
