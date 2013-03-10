package com.test9.irc.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;

import com.test9.irc.engine.SSL.SSLDefaultTrustManager;
import com.test9.irc.engine.SSL.SSLIRCConnection;


public class ConnectionEngine {

	private static ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();

	public static String settingsDir = ""; 
	public static final String fileSeparator = System.getProperty("file.separator");
	private String name = "", host = "", pass="", nick="", username="", realname="", encoding="";
	private int port;
	private boolean ssl;
	private boolean loadedConnection = false;

	public ConnectionEngine() throws IOException {

		System.out.println(System.getProperty("user.home"));
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");

		if(os.contains("mac os x")) {
			settingsDir = userHome+"/Library/Application Support/JIRCC";
		} else if (os.contains("windows")) {
			settingsDir = userHome+"\\Documents\\JIRCC";
		} else if (os.contains("linux")) {
			settingsDir = userHome + "/JIRCC/connections";
		} else if (os.contains("bsd")) {
			settingsDir = userHome +"/JIRCC/connections";
		}

		File dir = new File(settingsDir);
		System.out.println(dir.getAbsolutePath());

		if(!dir.exists()) {
			System.out.println("making new directory");
			dir.mkdir();
		}		

		loadConnection();

		//public IRCConnection(String host, int port, String pass, String nick, 
		//		String username, String realname, String encoding) 
	}

	public static void beginSSLIRCConnection(String name, String host, int port, String pass, 
			String nick, String username, String realname, String encoding) {
		SSLIRCConnection sslc = new SSLIRCConnection(name, host, port, pass, nick, 
				username, realname, encoding);
		connections.add(sslc);
		sslc.addIRCEventListener(new IRCEventAdapter(sslc));
		sslc.addTrustManager(new SSLDefaultTrustManager());

		try {
			sslc.connect();
		} catch (IOException e) {
			System.err.println("Could not start connection ["+host+"]");
		}
	}

	public static void beginIRCConnection(String name, String host, int port, String pass, String nick, 
			String username, String realname, String encoding) {
		IRCConnection irc = new IRCConnection(name, host, port, pass, nick, 
				username, realname, encoding);
		connections.add(irc);
		irc.addIRCEventListener(new IRCEventAdapter(irc));
		try {
			irc.connect();
		} catch (IOException e) {
			System.err.println("Could not start connection ["+host+"]");
		}
	}

	private void loadConnection() {


		Properties properties = new Properties();

		File connectionsDir = new File(settingsDir+fileSeparator+"connections");
		
		if(!connectionsDir.exists()){
			System.out.println("making new connecitons directory");
			connectionsDir.mkdir();
		}

		File[] files = new File(connectionsDir.getPath()).listFiles();
		for(File n : files) {
			if(Character.isLetterOrDigit(n.getName().charAt(0))) {
				loadedConnection = true;
				System.out.println(n.getAbsolutePath());
				FileInputStream in;
				try {
					in = new FileInputStream(n);
					properties.load(in);
					in.close();
				} catch (FileNotFoundException e) {
					System.err.println("You seem to not have a settings file. Please create one.");
				} catch (IOException e) {
					System.err.println("Error loading file into properties.");
				}

				name = properties.getProperty("name", "");
				host = properties.getProperty("host", "");
				pass = properties.getProperty("pass", "");
				nick = properties.getProperty("nick", "");
				username = properties.getProperty("username", "");
				realname = properties.getProperty("realname", "");
				encoding = properties.getProperty("encoding", "");
				port = Integer.parseInt(properties.getProperty("port"));
				ssl = Boolean.parseBoolean(properties.getProperty("ssl"));

				if(ssl) {
					beginSSLIRCConnection(name, host, port, pass, nick, 
							username, realname, encoding);
				} else {
					beginIRCConnection(name, host, port, pass, nick, 
							username, realname, encoding);
				}
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {

				}
			}
		}
		if(!loadedConnection) {
			new NewServerConfigWindow();
		}
	}
	
	/**
	 * @return the connection
	 */
	public static IRCConnection getConnection(String host) {
		System.out.println("getconnectionshost:"+host);
		for(IRCConnection temp : connections) {
			if(temp.getHost().equals(host)) {
				System.out.println("hostmatch");
				return temp;
			}
		}
		return null;
	}
}
