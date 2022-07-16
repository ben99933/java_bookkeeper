package clwhthr.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import clwhthr.account.Account;
import clwhthr.util.Date;

public abstract class CSVGetter extends FileGetter{
	
	
	public CSVGetter(String fileName) {
		super(fileName);
	}
	

	@Override
	String getFileFormat() {
		// TODO �۰ʲ��ͪ���k Stub
		return "csv";
	}

}
