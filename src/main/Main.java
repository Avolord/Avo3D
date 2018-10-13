package main;

import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class Main {

	public static void main(String[] args) {
		new ObjectBuffer2D(400, 400, true);
		JFXdraw.start(args);
	}

}
