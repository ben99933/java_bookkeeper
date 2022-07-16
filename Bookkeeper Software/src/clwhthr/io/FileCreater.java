package clwhthr.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import clwhthr.exception.FileExistException;
import clwhthr.setting.Config;

public abstract class FileCreater {
	Config config = Config.getInstance();
	
	
	public void createFile(String fileName) throws FileExistException {
		Config config = Config.getInstance();
		Path filePath = Paths.get(getFileDirectory().toString(), fileName +"."+getFormat());
		if(Files.exists(filePath))throw new FileExistException("accountFileHasExist");
		try {
			Files.createFile(filePath);
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}
	abstract Path getFileDirectory();
	abstract String getFormat();
}
