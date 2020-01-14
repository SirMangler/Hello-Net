package Utilities.Files;

public abstract class HelloFile {

	String path;
	
	public HelloFile(String path) {
		this.path = path;
	}
	
	abstract void writeDefaults();
	
	public String getPath() {
		return path;
	}
}
