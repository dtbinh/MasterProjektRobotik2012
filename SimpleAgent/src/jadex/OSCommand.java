package jadex;

import java.io.*;

public class OSCommand implements Runnable{
	
	protected String[] command = null;
	
	// Every class of this type has it's own thread
	protected Thread thread = new Thread ( this );

	public OSCommand (String[] cmd) {
		command = cmd;
		// Automatically start own thread in constructor
		thread.start();
	}

	public String exec (String[] cmd) {
		StringBuffer result = new StringBuffer();
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ( (s = in.readLine()) != null) {
				result.append(s);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	@Override
	public void run() {
		exec(command);
	}
	public void terminate() {
		// TODO get process id
		String [] killCmd = {"/usr/bin/killall", "player"};
		exec(killCmd);
		thread.interrupt();
//		while(thread.isAlive());
	}
}