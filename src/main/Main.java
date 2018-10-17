package main;

import compute.Camera;
import compute.Projection;
import compute.V3D;
import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class Main {

	public static void main(String[] args) {
		//new ObjectBuffer2D(400, 400, true);
		Camera a = Projection.getCamera();
		a.clipFrustum(new V3D(0,0,-100)).log();
		JFXdraw.start(args, 800, 800);
	}

}
