package clwhthr.io.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomFile extends File{

	public CustomFile(Path path,String fileName,String format) {
		super(Paths.get(path.toString(),fileName + "." + format).toString());
	}
}
