package screen;

import compute.V3D;
import obj3D.*;
import obj3D.Objects.Line;

public class Render {
	public static V3D p1 = new V3D(-50, -50, -50);
	public static V3D p2 = new V3D(50, 50, 50);
	public static Line l = new Line(p1, p2);
	// public static Box box = new Box(-50, -50, -50, 100, 100, 100);
	// public static Box ground = new Box(-500, -100, -500, 1000, 10, 1000);
	// public static Sphere sphere = new Sphere(0, 0, 0, 100, 50);
	// public static STL stl = new STL(".\\data\\bottle.txt");

	public static void main() {

		l.draw();
		// box.draw(5);
		// ground.draw(10);
		// box.rotateX(0.5);
		// box.rotateY(0.5);
	}

	public static void rotate(double x, double y, double z) {
		// stl.rotateX(180);
		// sphere.rotateX(90);
	}
}
