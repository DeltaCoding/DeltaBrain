package io.github.deltacoding.deltabrain;

import io.github.deltacoding.deltabrain.interpreter.BrainfuckInterpreter;

public class DeltaBrain {

	public static void main(String[] args) {
		BrainfuckInterpreter bfi = new BrainfuckInterpreter(args[0]);
		bfi.execute();
	}
	
}
