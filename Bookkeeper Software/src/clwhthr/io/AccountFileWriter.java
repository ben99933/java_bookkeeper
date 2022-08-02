package clwhthr.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.ws.WebServiceException;


public class AccountFileWriter implements Closeable{
	
	File file;
	PrintWriter outputStream;

	
	public AccountFileWriter(File file) throws FileNotFoundException {
		this.file = file;
		this.outputStream = new PrintWriter(file);
	}
	public void print(String string) {
		outputStream.print(string);
	}
	public void println(String string) {
		outputStream.println(string);
	}
	public void printf(String format, Object... args) {
		outputStream.printf(format, args);
	}
	@Override
	public void close(){
		outputStream.close();
	}
	
	
}
