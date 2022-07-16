package clwhthr.util;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

	public static String getFileName(File file) {
		String name = file.getName();
		
		return name.substring(0, name.lastIndexOf('.'));
	}
	
	public static String getFileFormat(File file) {
		String name = file.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}
	public static List getAllFiles(Path directory) {
		List list = new ArrayList<File>();
		File folder = directory.toFile();
		File[] files = folder.listFiles();
		for(int i = 0;i<files.length;i++) {
			if(files[i].isDirectory() == false)list.add(files[i]);
		}
		return list;
	}
	public static List getAllFiles(Path directory,String format) {
		List list = new ArrayList<File>();
		File folder = directory.toFile();
		File[] files = folder.listFiles();
		if(files != null) {
			for(int i = 0;i<files.length;i++) {
				if(files[i].isDirectory() == false && getFileFormat(files[i]).equals(format))list.add(files[i]);
			}
		}
		return list;
	}
}
