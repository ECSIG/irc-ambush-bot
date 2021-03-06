package com.test9.irc.parser;

import java.util.Arrays;


/**
 * Used to parse messages that come from the server.
 *
 * <message>  ::= [':' <prefix> <SPACE> ] <command> <params> <crlf> 
 * <prefix>   ::= <servername> | <nick> [ '!' <username> ] [ '@' <host> ] 
 * <command>  ::= <letter> { <letter> } | <number> <number> <number> 
 * <SPACE>    ::= ' ' { ' ' }
 * <params>   ::= <SPACE> [ ':' <trailing> | <middle> <params> ] 
 * <middle>   ::= <Any *non-empty* sequence of octets not including SPACE or NUL or CR or LF, the first of which may not be ':'> 
 * <trailing> ::= <Any, possibly *empty*, sequence of octets not including NUL or CR or LF> 
 * <crlf>     ::= CR LF
 * @author Jared Patton
 *
 */
public class Parser {

	private static boolean init = false;
	private static boolean prefixPresent = false;
	private static String prefix = "";
	private static String command = "";
	private static String[] params;
	private static String serverName = "";
	private static String nickname = "";
	private static String user = "";
	private static String host = "";
	private static String content = "";
	final private static String DIVIDER = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

	/**
	 * Initializes the parser and set's init to true.
	 * @param init sets to true
	 */
	public Parser() 
	{
		init = true;
	}

	/**
	 * 
	 * This should be the only method that is called in this class. This is used
	 * to parse raw output from the server and will return a new message
	 * from the parsed information. Necessary information should be read
	 * from the returned message. 
	 * @param message
	 * @return
	 */
	public synchronized Message parse(StringBuffer message)
	{
		resetParser();

		if(message.substring(0,1).equals(":"))
		{
			prefixPresent = true;
			message.delete(0, 1);
		}

		if(prefixPresent)
		{
			prefix = message.substring(0, message.indexOf(" "));
			message.delete(0, message.indexOf(" ") + 1);
			parsePrefix(prefix);
		}

		command = message.substring(0, message.indexOf(" "));
		message.delete(0, message.indexOf(" "));

		if(message.indexOf(" :") >= 0)
		{
			params = parseParams(message.substring(0, message.indexOf(" :")).trim());
			message.delete(0, message.indexOf(" :") + 2);

			content = message.substring(0, message.length());
		}
		else
		{
			params = parseParams(message.substring(0, message.length()).trim());
			message.delete(0, message.length());
			content = "";
		}

		printStuff();

		return(new Message(prefix, command, params, serverName, nickname, user, host, content));

	}

	/**
	 * Parses a prefix if one is present.
	 * @param prefix
	 */
	private void parsePrefix(String prefix)
	{
		String splitPrefix[] = prefix.split("[!@ ]");
		if(splitPrefix.length == 3) {
			nickname = splitPrefix[0];
			user = splitPrefix[1];
			host = splitPrefix[2];
		}
		else if(splitPrefix.length == 2)
		{
			nickname = splitPrefix[0];
			host = splitPrefix[1];
		}
		else
			serverName = splitPrefix[0];	
	}

	/**
	 * Parses the individual parameters form the 
	 * params field.
	 * @return 
	 */
	private String[] parseParams(String possibleParams)
	{
		return(possibleParams.split(" "));
	}

	@SuppressWarnings("unused")
	private char[] modeParse(String mode)
	{

		return null;
	}

	/**
	 * Resets the parser so it can parse a new message.
	 */
	private void resetParser()
	{
		prefixPresent = false;
		prefix = "";
		command = "";
		params = null;
		serverName = "";
		nickname = "";
		user = "";
		host = "";
		content = "";
	}

	/**
	 * Used for debugging. Can print out information the parser has.
	 */
	@SuppressWarnings("unused")
	private void printStuff()
	{
		System.out.println("Prefix: \t'" + prefix + "'");
		System.out.println("Command: \t'" + command + "'");
		System.out.println("Params: \t'"+ Arrays.toString(params) + "'");
		System.out.println("Server_name: \t'" + serverName + "'");
		System.out.println("Nickname: \t'" + nickname + "'");
		System.out.println("User: \t\t'"+ user + "'");
		System.out.println("Host: \t\t'"+host + "'");
		System.out.println("Content: \t'"+content+"'");
		System.out.println(DIVIDER);
	}

	public static boolean isAway(char c){

		if(c == 'H')
			return false;
		else if (c == 'G')
			return true;
		else {
			System.err.println("Error determining user away status");
			return false;
		}
	}
	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}
}
