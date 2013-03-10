package com.test9.irc.engine;


import java.util.Scanner;

public class InputReader {
	Scanner in = new Scanner(System.in);
	String input = "";
	
	public InputReader() {
		while(true){
			input = in.nextLine();

			done();
		}
	}
	
	private void done() {
		in.reset();
		input = "";
	}
}
