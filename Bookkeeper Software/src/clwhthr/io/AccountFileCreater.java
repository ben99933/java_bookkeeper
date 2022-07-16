package clwhthr.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import clwhthr.account.Account;
import clwhthr.exception.FileExistException;
import clwhthr.setting.Config;

public class AccountFileCreater extends FileCreater{
	
	public void createFile(String name, String passwordHash) throws FileExistException, FileNotFoundException {
		super.createFile(name);
		File file = new AccountFileGetter(name).getFile();
		AccountFileWriter writer = new AccountFileWriter(file);
		writer.println(passwordHash);
		writer.close();
	}
	public void createFile(Account account) throws FileExistException, FileNotFoundException {
		createFile(account.getName(),account.getPasswordHash());
	}
	@Override
	Path getFileDirectory() {
		// TODO 自動產生的方法 Stub
		return config.getAccountPath();
	}
	@Override
	String getFormat() {
		// TODO 自動產生的方法 Stub
		return "account";
	}
}
