package clwhthr.io;

import java.nio.file.Path;

import clwhthr.account.Account;
import clwhthr.exception.FileExistException;
import clwhthr.util.Debug;

public class RecordFileCreater extends CSVCreater{

	Account account;
	public  RecordFileCreater(Account account) {
		this.account = account;
	}
	
	@Override
	Path getFileDirectory() {
		// TODO 自動產生的方法 Stub
		return config.getRecordPath(account);
	}

	public void createFile(int year,int month) throws FileExistException {
		String name = String.valueOf(year*100 + month);
		super.createFile(name);
	}

}
