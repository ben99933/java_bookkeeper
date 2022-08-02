package clwhthr.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.ws.WebServiceException;


import clwhthr.account.Account;
import clwhthr.util.FileHelper;

public class AccountFileReader implements Closeable{
	
	BufferedReader reader;
	File file;
	public AccountFileReader(File file) throws FileNotFoundException {
		this.file = file;
		this.reader = new BufferedReader(new FileReader(file));
	}
	
	public Account getAccount() throws IOException {
		String name = FileHelper.getFileName(file);
		String hash = reader.readLine();
		return new Account(name, hash);
	}

	@Override
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}
}

