package clwhthr.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.xml.ws.WebServiceException;

import com.sun.xml.internal.ws.Closeable;

import clwhthr.account.Account;

public class AccountFileReader implements Closeable{
	
	Scanner scanner;
	File file;
	public AccountFileReader(File file) throws FileNotFoundException {
		this.file = file;
		this.scanner = new Scanner(new FileInputStream(file));	
	}
	
	public Account getAccount() {
		String string = file.getName();
		String name = string.substring(0,string.lastIndexOf('.'));
		String hash = scanner.nextLine();
		return new Account(name, hash);
	}

	@Override
	public void close() throws WebServiceException {
		scanner.close();
	}
}

