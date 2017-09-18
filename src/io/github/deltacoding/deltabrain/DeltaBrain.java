package io.github.deltacoding.deltabrain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.github.deltacoding.deltabrain.interpreter.BrainfuckInterpreter;

public class DeltaBrain {

	public static void main(String[] args) {
		if(args[0].equals("genHW")) {
			try {
				File tmp = new File("HelloWorld.bf");
				tmp.createNewFile();
				String helloWorld = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
				Files.write(Paths.get("HelloWorld.bf"), helloWorld.getBytes());
			} catch (IOException e) {
				System.err.println("[ERROR] Could not create/write to the file 'HelloWorld.bf'");
			}
		} else {
			BrainfuckInterpreter bfi = new BrainfuckInterpreter(args[0]);
			bfi.execute();
		}
	}
	
}
