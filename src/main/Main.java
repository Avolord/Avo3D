package main;

import compute.Plane;
import compute.V3D;
import screen.JFXdraw;

public class Main {

	public static void main(String[] args) {
		//new ObjectBuffer2D(400, 400, true);
		JFXdraw.start(args, 800, 800);
		Plane p = new Plane(4,3,-7,14);
		p.log();
		V3D A = new V3D(1,1,1);
		V3D B = new V3D(2,1,1);
		p.lineIntersectFull(A, B).log();
	}

}
