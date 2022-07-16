package clwhthr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.ws.WebServiceException;

import com.sun.xml.internal.ws.Closeable;

import clwhthr.exception.FileFormatException;
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
	public List<List<String>> getContents() throws IOException{
		List<List<String>> content = new ArrayList<List<String>>();
		String line;
		while((line = reader.readLine()) != null) {
			ArrayList<String>list = new ArrayList<String>();
			String[] array = line.split(",");
			for (String string : array) {
				list.add(string);
			}
			content.add(list);
		}
		return content;
	}
	@Override
	public void close() throws WebServiceException {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
