package clwhthr.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.ws.WebServiceException;

import com.sun.xml.internal.ws.Closeable;

public class RecordFileWriter extends CSVWriter{
	
	public RecordFileWriter(File file) throws IOException {
		super(file);
	}
}
