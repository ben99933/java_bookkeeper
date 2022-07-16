package clwhthr.io.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVFile extends CustomFile{
	public CSVFile(Path path,String fileName) {
		super(path,fileName,"csv");
	}
}
