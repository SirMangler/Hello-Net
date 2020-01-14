package Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Loggable {
	
	protected static Thread log_thr;
	protected static Queue<String> log_queue = new LinkedList<String>();
	
	
	protected void info(String info, String... args) {
		log(info, "INFO", getName(), false, args);
	}
	
	protected void severe(String severe, String... args) {
		log(severe, "SEVERE", getName(), true, args);
	}
	
	protected void warning(String severe, String... args) {
		log(severe, "WARNING", getName(), true, args);
	}
	
	private static void log(String log, String level, String name, boolean isError, String[] args) {
		String timestamp = new SimpleDateFormat("HH.mm.ss")
								.format(new Date());
		if (args.length != 0)
			for (int i = 0; args.length < i; i++)
				log = log.replace("%"+i, args[i]);
		
		log = timestamp+" <"+level+"> ["+name+"]" + log;
		
		if (isError)
			System.err.println(log);
		else 
			System.out.println(log);
		
		QueueLine(log);
	}
	
	private static void QueueLine(String line)
	{
		if (log_queue == null)
			log_queue = new LinkedList<String>();
		
		log_queue.add(line);
		
		if (log_thr == null || !log_thr.isAlive()) {
			log_thr = new Thread(() -> {
				try {
					beginLogThread();
				} catch (IOException e) {
					log("Failed to begin Logger", "SEVERE", "System-Logger", true, null);
					e.printStackTrace();
				}
			});
			
			log_thr.start();
		}
	}
	
	private static void beginLogThread() throws IOException 
	{
		BufferedWriter w = new BufferedWriter(new FileWriter("HelloNet-System.log"));
		PrintWriter pw = new PrintWriter(w);
		
		String line;
		while ((line = log_queue.poll()) != null)
		{
			pw.write(line);
			pw.flush();
		}
		
		pw.close();
		w.close();
	}
	
	abstract String getName();
}
