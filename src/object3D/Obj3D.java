package object3D;

import java.util.ArrayList;
import java.util.Iterator;

import display.Display;
import display.FX;
import display.camera.computation.Vector4;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Obj3D {
	public static Color faceColor = Color.rgb(255, 0, 0, 0.5);
	public static Color edgeColor = Color.BLACK;
	private static Display display;

	public static void initDisplay(Display display) {
		Obj3D.display = display;
	}

	protected ArrayList<Face> faces = new ArrayList<Face>();
	protected ArrayList<Vector4> vertecies = new ArrayList<Vector4>();
	protected int PointsPerFace = 3;

	public void draw() {
		Iterator<Face> iterator = faces.iterator();
		while (iterator.hasNext()) {
			drawTriangles(iterator.next().clipping());
		}
	}

	public void draw(double size) {
		Iterator<Face> iterator = faces.iterator();
		while (iterator.hasNext()) {
			drawTriangles(iterator.next().clipping(), size);
		}
	}

	public void rotate(double x, double y, double z) {
		Iterator<Vector4> iterator = vertecies.iterator();
		while (iterator.hasNext()) {
			Vector4 t = iterator.next();
			t.rotateX(x);
			t.rotateY(y);
			t.rotateZ(z);
		}
	}

	public void move(double x, double y, double z) {
		Iterator<Vector4> iterator = vertecies.iterator();
		while (iterator.hasNext()) {
			Vector4 t = iterator.next();
			t.add(x, y, z, 0);
		}
	}

	public void drawTriangle(Point2D p1, Point2D p2, Point2D p3) {
		display.fillPoly(faceColor, p1, p2, p3);
		display.strokePoly(edgeColor, p1, p2, p3);
	}

	public void drawTriangles(ArrayList<Vector4> vertecies, double size) {
		Point2D A;
		try {
			A = FX.projector.Perspective(vertecies.get(0));
			if (A == null) {
				return;
			}
			A = A.multiply(size);
		} catch (IndexOutOfBoundsException e) {
			return;
		}

		for (int i = 1; i < vertecies.size() - 1; i++) {

			Point2D B = FX.projector.Perspective(vertecies.get(i));
			Point2D C = FX.projector.Perspective(vertecies.get(i + 1));

			if (B == null || C == null)
				return;

			B = B.multiply(size);
			C = C.multiply(size);

			display.fillPoly(faceColor, A, B, C);
			display.strokePoly(edgeColor, A, B, C);
		}
	}

	public void drawTriangles(ArrayList<Vector4> vertecies) {
		Point2D A;
		try {
			A = FX.projector.Perspective(vertecies.get(0));
			if (A == null) {
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			return;
		}

		for (int i = 1; i < vertecies.size() - 1; i++) {

			Point2D B = FX.projector.Perspective(vertecies.get(i));
			Point2D C = FX.projector.Perspective(vertecies.get(i + 1));
			
			if (B == null || C == null)
				return;

			display.fillPoly(faceColor, A, B, C);
			display.strokePoly(edgeColor, A, B, C);
		}
	}
	
	public void cleanUP() {
		ArrayList<Vector4> originals = new ArrayList<Vector4>();
		
		vertecies.forEach(outerVector -> {
			Iterator<Vector4> innerIterator = originals.iterator();
			boolean original = true;
			while(innerIterator.hasNext() && original) {
				Vector4 innerVector = innerIterator.next();
				if(outerVector.equals(innerVector)) {
					original = false;
				}
			}
			if(original)
				originals.add(outerVector);			
		});
		
		vertecies.clear();
		vertecies.addAll(originals);
		originals.clear();
		
		Iterator<Vector4> outerIterator = vertecies.iterator();
		while(outerIterator.hasNext()) {
			Vector4 outerVector = outerIterator.next();
			faces.forEach(face -> {
				face.getVertecies().forEach(vertex -> {
					if(vertex.equals(outerVector))
						face.replaceVertex(vertex, outerVector);
				});
			});
		}
	}
}
