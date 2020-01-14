package Utilities.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Utilities.Loggable;

public abstract class HelloConfig extends Loggable {
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	File config_file;
	
	public HelloConfig(String path)
	{
		config_file = new File(path);
		
		loadConfig();
	}
	
	public void writeVariable(String key, Object var)
	{
		
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	
	public String getString(String key) {
		Object val = data.get(key);
		
		if (val instanceof String) 
			return (String) data.get(key);
		else {
			warning("Could not convert '%0' to a String", key);
			return "null";
		}
	}
	
	public int getInt(String key) {
		Object val = data.get(key);
		
		if (val instanceof Integer) 
			return (Integer) data.get(key);
		else {
			warning("Could not convert '%0' to an Integer", key);
			return -1;
		}
	}
	
	private boolean loadConfig() {
		if (!config_file.exists())
			return createDefaults();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(config_file));
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] keyval = line.split("=", 2);
				
				data.put(keyval[0], keyval[1]);
				info("Loaded key '%0' with value '%1'", keyval[0], keyval[1]);
			}
			
			reader.close();
		
		} catch (IOException e) {
			severe("Couldn't load HelloNet.cfg!");
			e.printStackTrace();
		}
	
		info("Successfully loaded the configuration!");
		
		return false;
	}
	
	abstract boolean createDefaults();
}
