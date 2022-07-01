package clwhthr.account;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.org.apache.bcel.internal.classfile.Field;

import clwhthr.exception.FileExistException;
import clwhthr.exception.StringFormatException;
import clwhthr.io.AccountFileCreater;
import clwhthr.io.AccountFileWriter;
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
		AccountFileCreater.create(new Account(name, hash));
	}
	
}
