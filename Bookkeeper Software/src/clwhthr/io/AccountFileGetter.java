package clwhthr.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import clwhthr.account.Account;
import clwhthr.setting.Config;

public class AccountFileGetter extends FileGetter{

	public AccountFileGetter(String fileName) {
		super(fileName);
	}
	public AccountFileGetter(Account account) {
		super(account.getName());
	}
	@Override
	Path getFileDirectory() {
		return config.getAccountPath();
	}
	@Override
	String getFileFormat() {
		// TODO 自動產生的方法 Stub
		return "account";
	}
	
}
