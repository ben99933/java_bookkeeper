package clwhthr.io;

import java.nio.file.Path;

import clwhthr.account.Account;
import clwhthr.util.Date;

public class RecordFileGetter extends CSVGetter{

	
	Account account;
	
	public RecordFileGetter(Account account, int year,int month) {
		super(String.valueOf(year*100+month));
		this.account = account;
	}

	@Override
	Path getFileDirectory() {
		// TODO �۰ʲ��ͪ���k Stub
		return config.getRecordPath(account);
	}

}
