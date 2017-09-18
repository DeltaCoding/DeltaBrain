package io.github.deltacoding.deltabrain.interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BrainfuckInterpreter {

	private byte[] memory;
	private int dp;
	private char[] code;
	private int ip;
	
	public BrainfuckInterpreter(String file) {
		this.memory = new byte[30000];
		this.dp = 0;
		try {
			this.code = new String(Files.readAllBytes(Paths.get(file))).toCharArray();
		} catch (IOException e) {
			System.err.println("[ERROR] Could not find the file '" + file + "'");
		}
		this.ip = 0;
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
				if(this.memory[this.dp] == 0) {
					int tmp = this.searchForClosingBracket();
					if(tmp == -1) {
						System.err.println("[ERROR] Could not find matching closing bracket, interpretation aborted");
						System.exit(-1);
					}
					this.ip = tmp;
				}
				break;
				
			case ']':
				if(this.memory[this.dp] != 0) {
					int tmp = this.searchForOpeningBracket();
					if(tmp == -1) {
						System.err.println("[ERROR] Could not find matching opening bracket, interpretation aborted");
						System.exit(-1);
					}
					this.ip = tmp;
				}
				break;
			}
			this.ip++;
		}
	}
	
	private int searchForClosingBracket() {
		int nestedBrackets = 0;
		for(int i = this.ip + 1; i < this.code.length - 1; i++) {
			if(this.code[i] == '[') {
				nestedBrackets++;
			} else if(this.code[i] == ']') {
				if(nestedBrackets == 0) {
					return i;
				} else {
					nestedBrackets--;
				}
			}
		}
		
		return -1;
	}
	
	private int searchForOpeningBracket() {
		int nestedBrackets = 0;
		for(int i = this.ip - 1; i > 0; i--) {
			if(this.code[i] == ']') {
				nestedBrackets++;
			} else if(this.code[i] == '[') {
				if(nestedBrackets == 0) {
					return i;
				} else {
					nestedBrackets--;
				}
			}
		}
		
		return -1;
	}	
}
