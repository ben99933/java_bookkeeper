package clwhthr.init;

import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import clwhthr.main.Main;
import clwhthr.setting.Config;
import clwhthr.util.Debug;
import clwhthr.util.FileHelper;

public class RecordInitializer implements Initializer{
	
	public RecordInitializer() {
		
	}
	
	@Override
	public void init() {
		List files = Main.recordFiles;
		Config config = Config.getInstance();
		Path path = config.getRecordPath(Main.currentAccount);
		files.addAll(FileHelper.getAllFiles(path, "csv"));
		for (File file : Main.recordFiles) {
			String fileName = FileHelper.getFileName(file);
			if(fileName.matches("[0-9]+") == false || fileName.length() != 6)files.remove(file);
		}
		files.sort(new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.compareTo(o2);
			}
		});
		Debug.log(this.getClass(), "Record init complete");
	}

}
