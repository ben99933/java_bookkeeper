package clwhthr.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import clwhthr.setting.Config;

public abstract class FileGetter {
	
	Config config;
	private String fileName;
	
	public FileGetter(String fileName) {
		this.fileName = fileName;
		config = config.getInstance();
	}
	public File getFile() throws FileNotFoundException {
		File file = new File(getFileDirectory().toString() ,fileName + "." + getFileFormat());
		if(Files.exists(file.toPath()) == false) {
			throw new FileNotFoundException();
		}
		return file;
	}
	abstract Path getFileDirectory();
	abstract String getFileFormat();
}
