package io.github.deltacoding.deltabrain.interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class BrainfuckInterpreter {

	private byte[] memory;
	private int dp;
	private char[] code;
	private int ip;
	
	private Stack<Integer> brackets;
	
	public BrainfuckInterpreter(String file) {
		this.memory = new byte[30000];
		this.dp = 0;
		try {
			this.code = new String(Files.readAllBytes(Paths.get(file))).toCharArray();
		} catch (IOException e) {
			System.err.println("[ERROR] Could not find the file '" + file + "'");
		}
		this.ip = 0;
		this.brackets = new Stack<Integer>();
	}
	
	public void execute() {
		while(this.ip != this.code.length - 1) {
			switch(code[ip]) {
			case '>':
				this.dp++;
				break;
				
			case '<':
				this.dp--;
				break;
				
			case '+':
				this.memory[this.dp]++;
				break;
				
			case '-':
				this.memory[this.dp]--;
				break;
				
			case '.':
				System.out.print((char) this.memory[this.dp]);
				break;
				
			case ',':
				try {
					this.memory[this.dp] = (byte) System.in.read();
				} catch (IOException e) {
					System.err.println("[ERROR] Could not read character");
				}
				break;
				
			case '[':
				if(this.memory[this.dp] != 0) {
					this.brackets.push(this.ip);
				}
				break;
				
			case ']':
				if(this.memory[this.dp] != 0) {
					this.ip = this.brackets.peek();
				} else {
					this.brackets.pop();
				}
				break;
			}
			this.ip++;
		}
	}
	
	public void checkBracketCount() {
		int opening = 0;
		int closing = 0;
		
		for(char c : this.code) {
			if(c == '[')
				opening++;
			else if(c == ']')
				closing++;
		}
		
		if(opening != closing) {
			System.err.println("[ERROR] The amount of opening and closing brackets do not match. Interpretation will not be executed.");
			System.exit(-1);
		}
	}
}
