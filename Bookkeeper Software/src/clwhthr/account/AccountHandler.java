package clwhthr.account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.org.apache.bcel.internal.classfile.Field;

import clwhthr.exception.FileExistException;
import clwhthr.exception.StringFormatException;
import clwhthr.init.InitializeHandler;
import clwhthr.init.Initializer;
import clwhthr.io.AccountFileCreater;
import clwhthr.io.AccountFileGetter;
import clwhthr.io.AccountFileReader;
import clwhthr.io.AccountFileWriter;
import clwhthr.main.Main;
import clwhthr.setting.Config;
import clwhthr.util.hash.SHA;

public class AccountHandler {
	
	private static AccountHandler instance = new AccountHandler();
	
	private AccountHandler() {
		
	}
	public static AccountHandler getInstance() {
		return instance;
	}
	public void registerAccount(String name,String password)throws StringFormatException, FileExistException{
		int len = name.length();
		if(!(6<=len && len <=30))throw new StringFormatException("length");
		//英文開頭 由英文數字底線組成之句子
		if(name.matches("^[a-zA-Z]+\\w+$") == false)throw new StringFormatException("format");
		len = password.length();
		if(!(6<=len && len <=30))throw new StringFormatException("length");
		//英文開頭 由英文數字底線組成之句子
		if(password.matches("^[a-zA-Z]+\\w+$") == false)throw new StringFormatException("format");
		
		String hash = SHA.getResult(password);
		try {
			new AccountFileCreater().createFile(new Account(name, hash));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean login(String name, String hash) {
		try {
			AccountFileReader reader = new AccountFileReader(new AccountFileGetter(name).getFile());
			Account account = reader.getAccount();
			reader.close();
			String passHash = account.getPasswordHash();
			if(passHash.equals(hash) == false)return false;
		} catch (Exception e) {
			return false;
		}
		Main.currentAccount = new Account(name, hash);
		InitializeHandler.init();
		return true;
	}


}
