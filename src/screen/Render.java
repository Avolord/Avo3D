package screen;

import compute.V3D;
import obj3D.*;

public class Render {
	public static V3D p1 = new V3D(-50, -50, -50);
	public static V3D p2 = new V3D(50, 50, 50);
	//public static Box box = new Box(-50, -50, -50, 100, 100, 100);
	//public static Sphere sphere = new Sphere(0, 0, 0, 100);
	public static STL stl = new STL("G:\\EclipsePrograms\\Java\\Avo3D\\data\\piramide_palenque.stl");

	public static void main() {

//		JFXdraw.getBuffer2D().line(p1.Orthographic(), p2.Orthographic());
//		p1.rotateX(1);
//		p2.rotateX(1);
//		p1.rotateY(2);
//		p2.rotateY(2);
//		p1.rotateZ(3);
//		p2.rotateZ(3);
		stl.draw();
		//stl.rotateX(0.5);
		//stl.rotateY(0.5);
		//stl.rotateZ(0.5);
		//sphere.draw();
		//sphere.rotateX(0.5);
		//sphere.rotateY(0.5);
		//sphere.rotateZ(0.5);
//		cube.rotateY(2);
//		cube.rotateZ(3);
//		cube.draw();
//		cube.rotateX(1);
//		cube.rotateY(2);
//		cube.rotateZ(3);
	}
	
	public static void rotate(double x, double y, double z) {
//		sphere.rotateX(x);
//		sphere.rotateY(y);
//		sphere.rotateZ(z);
	}
}
