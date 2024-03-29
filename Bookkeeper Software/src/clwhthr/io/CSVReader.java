package clwhthr.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.xml.ws.WebServiceException;


import clwhthr.exception.FileFormatException;
import clwhthr.util.Debug;
import clwhthr.util.FileHelper;

public class CSVReader implements Closeable{
	
	BufferedReader reader;
	File file;
	public CSVReader(File file) throws FileNotFoundException, FileFormatException {
		this.file = file;
		String format = FileHelper.getFileFormat(file);
		if(format.equals("csv") == false)throw new FileFormatException(format);
		reader = new BufferedReader(new FileReader(file));
	}
	public List<String[]> getContents() throws IOException{
		List<String[]> content = new ArrayList<String[]>();
		String line;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			content.add(array);
		}
		return content;
	}
	@Override
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
