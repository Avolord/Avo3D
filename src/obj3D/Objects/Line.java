package obj3D.Objects;

import compute.V3D;
import obj3D.Obj3D;

public class Line extends Obj3D {
	public Line(V3D p1, V3D p2) {
		super(2, 1, 0);
		edges.add(p1);
		edges.add(p2);
		vertices.add(new int[] { 0, 1 });
	}
	
//	public void draw() {
//		Obj3D.buffer.line(p1, p2, color);
//	}
//	
//	public void draw(double scaling) {
//		
//	}
}
