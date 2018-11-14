package display;

import display.camera.computation.Vector4;
import object3D.Box;
import object3D.Face;
import object3D.STL;
import object3D.Sphere;

public class Renderer {
	//public static Box b1 = new Box(-50,-50,200,100,100,100);
	public static STL stl = new STL(".\\data\\gengar.STL");
	//public static Sphere s1 = new Sphere(0,0,0,200,20);
	
	public static void start() {
		//s1.draw();
		stl.draw(1);
		//stl.rotate(0.02, 0, 0);
		//b1.move(0, 0, -0.1);
	}
}
