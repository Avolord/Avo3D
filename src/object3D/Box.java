package object3D;

import java.util.Collections;

import display.camera.computation.Vector4;

public class Box extends Obj3D{
	public Box(double x, double y, double z, double w, double h, double d) {
		Vector4 A = new Vector4(x, y, z);
		Vector4 B = new Vector4(x + w, y, z);
		Vector4 C = new Vector4(x + w, y + h, z);
		Vector4 D = new Vector4(x, y + h, z);
		Vector4 E = new Vector4(x, y, z + d);
		Vector4 F = new Vector4(x + w, y, z + d);
		Vector4 G = new Vector4(x + w, y + h, z + d);
		Vector4 H = new Vector4(x, y + h, z + d);
		
		Collections.addAll(vertecies, A, B, C, D, E, F, G, H);
		Collections.addAll(faces,
		new Face(A,C,B),
		new Face(A,C,D),
		new Face(A,F,B),
		new Face(A,F,E),
		new Face(A,H,E),
		new Face(A,H,D),
		
		new Face(G,E,H),
		new Face(G,E,F),
		new Face(G,B,C),
		new Face(G,B,F),
		new Face(G,D,C),
		new Face(G,D,H) );
		//ABCD, EFGH, ABEF, 
		cleanUP();
	}
}
