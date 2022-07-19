package clwhthr.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.ws.WebServiceException;

import com.sun.xml.internal.ws.Closeable;

import clwhthr.util.Debug;

public class CSVWriter implements Closeable{

	BufferedWriter writer;
	File file;
	public CSVWriter(File file,boolean append) throws IOException {
		this.file = file;
		writer = new BufferedWriter(new FileWriter(file,append));
	}
	@Override
	public void close() throws WebServiceException {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}
	public void write(String...args) throws IOException{
		boolean flag = false;
		for(int i = 0;i<args.length;i++) {
			if(flag) {
				writer.write(",");
			}else flag = true;
			writer.write(args[i]);
		}
		writer.newLine();
	}
}
