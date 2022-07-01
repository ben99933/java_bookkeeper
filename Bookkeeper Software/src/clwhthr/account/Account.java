package clwhthr.account;

public class Account {
	
	private String name;
	private String passwordHash;
	
	public Account(String name,String passwordHash) {
		this.name = name;
		this.passwordHash = passwordHash;
	}
	
	public String toStirng() {
		return name;
	}
	@Override
	public int hashCode() {
		return (((name.hashCode()) % 27644437) + ((name.hashCode()) % 27644437))% 27644437;
	}
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	public String getName() {
		return this.name;
	}
	public String getPasswordHash() {
		return this.passwordHash;
	}
	public boolean verified(String name,String passwordHash) {
		if(this.name==name && this.passwordHash == passwordHash)return true;
		return false;
	}
}
