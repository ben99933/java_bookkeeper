package clwhthr.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import clwhthr.account.Account;
import clwhthr.setting.Config;

public class AccountFileGetter {
	
	static Config config = Config.getInstance();
	static Path pathAccount = config.getAccountPath();
	
	@SuppressWarnings("null")
	public static Set<Account> getAllAccounts(){
		Set<Account>set = new HashSet<Account>();
		File folder = new File(pathAccount.toString());
		File files[] = folder.listFiles();
		for (File file : files) {
			if(file.isDirectory())continue;
			String fileName = file.getName();
			String format = fileName.substring(fileName.lastIndexOf('.') + 1);
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			if(format != "account")continue;
			AccountFileReader reader = null;
			try {
				reader = new AccountFileReader(file);
			} catch (FileNotFoundException e) {
				reader.close();
				e.printStackTrace();
				continue;
			}
			set.add(reader.getAccount());
			reader.close();
		}
		return set;
	}
	public static File getFile(Account account) {
		return getFile(account.getName());
	}
	public static File getFile(String name) {
		try {
			File file = new File(pathAccount.toString() ,name + ".account");
			return file;
		} catch (NullPointerException e) {
			return null;
		}
		
	}
}
