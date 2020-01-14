package Utilities.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class testt extends PrintWriter {

	public testt(File path) throws FileNotFoundException {
		super(path);
	}

}
