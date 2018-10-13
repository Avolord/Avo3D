package main;

import compute.Projection;
import compute.V3D;
import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class Main {

	public static void main(String[] args) {
		Projection.Perspective(new V3D(1,2,3));
		new ObjectBuffer2D(400, 400, true);
		JFXdraw.start(args);
	}

}
