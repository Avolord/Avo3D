package obj3D;

import java.util.ArrayList;

import compute.Projection;
import compute.V3D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import main.console;
import screen.ObjectBuffer2D;

public class Obj3D {
	private static ObjectBuffer2D buffer = null;

	protected ArrayList<V3D> edges = null;
	protected ArrayList<int[]> vertices = null;
	protected ArrayList<int[]> faces = null;
	private Color faceColor = Color.web("rgba(255,0,0,0.2)");
	private Color vertColor = Color.BLACK;

	protected Obj3D(int edges, int vertices, int faces) {
		this.edges = new ArrayList<V3D>(edges);
		this.vertices = new ArrayList<int[]>(vertices);
		this.faces = new ArrayList<int[]>(faces);
	}

	protected Obj3D() {
		this.edges = new ArrayList<V3D>();
		this.vertices = new ArrayList<int[]>();
		this.faces = new ArrayList<int[]>();
	}

	public static void initBuffer(ObjectBuffer2D buffer) {
		Obj3D.buffer = buffer;
	}

	public static void PolyLine3D(ArrayList<V3D> pts) {
		Point2D[] points = new Point2D[pts.size()];
		for (int i = 0; i < pts.size(); i++) {
			points[i] = Projection.Perspective(pts.get(i));
		}
		buffer.PolyLine(Color.BLACK, points);
	}

	public void draw() {
		Point2D[] points = new Point2D[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			points[i] = Projection.Perspective(edges.get(i));
		}
		faces.forEach(face -> {
			Point2D[] faceP = new Point2D[face.length];
			for (int i = 0; i < face.length; i++) {
				faceP[i] = points[face[i]];
			}
			buffer.fillPoly(faceColor, faceP);
		});
		vertices.forEach(vert -> {
			buffer.line(points[vert[0]], points[vert[1]], vertColor);
		});
	}

	public void draw(double size_factor) {
		Point2D[] points = new Point2D[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			points[i] = Projection.Perspective(edges.get(i)).multiply(size_factor);
		}
		faces.forEach(face -> {
			Point2D[] faceP = new Point2D[face.length];
			for (int i = 0; i < face.length; i++) {
				faceP[i] = points[face[i]];
			}
			buffer.fillPoly(faceColor, faceP);
		});
		vertices.forEach(vert -> {
			buffer.line(points[vert[0]], points[vert[1]], vertColor);
		});
	}

	public void drawEdges() {
		edges.forEach(edge -> {
			Point2D p = Projection.Perspective(edge);
			buffer.circle(p.getX(), p.getY(), 1);
		});
	}

	public void drawEdges(double size_factor) {
		edges.forEach(edge -> {
			Point2D p = Projection.Perspective(edge).multiply(size_factor);
			buffer.circle(p.getX(), p.getY(), 1);
		});
	}

	protected void cleanUpData() {
		console.log("starting with: " + edges.size() + " edges!");
		ArrayList<V3D> ed = new ArrayList<V3D>();
		ArrayList<Integer> IndexOfDupes = new ArrayList<Integer>();

		edges.forEach(a -> {
			boolean isUnique = true;
			for (int i = 0; i < ed.size(); i++) {
				if (ed.get(i).equals(a))
					isUnique = false;
			}
			if (isUnique || ed.size() == 0)
				ed.add(a);
			else
				IndexOfDupes.add(edges.indexOf(a));
		});

		edges.clear();
		edges.addAll(ed);
		console.log("Found " + IndexOfDupes.size()+" duplicates!");
		console.log("Edges have been reduced to: " + edges.size() + " !");
		
	}

	public void rotateX(double angle) {
		edges.forEach(edge -> edge.rotateX(angle));
	}

	public void rotateY(double angle) {
		edges.forEach(edge -> edge.rotateY(angle));
	}

	public void rotateZ(double angle) {
		edges.forEach(edge -> edge.rotateZ(angle));
	}

	public ArrayList<V3D> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<V3D> edges) {
		this.edges = edges;
	}

	public ArrayList<int[]> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<int[]> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<int[]> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<int[]> faces) {
		this.faces = faces;
	}

	public Color getFaceColor() {
		return faceColor;
	}

	public void setFaceColor(Color faceColor) {
		this.faceColor = faceColor;
	}

	public void setFaceColor(String faceColor) {
		this.faceColor = Color.web(faceColor);
	}

	public Color getVertColor() {
		return vertColor;
	}

	public void setVertColor(Color vertColor) {
		this.vertColor = vertColor;
	}

	public void setVertColor(String vertColor) {
		this.vertColor = Color.web(vertColor);
	}
}
