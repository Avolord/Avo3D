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
	private static Projection projection = new Projection(0d, 0d, -200d, 0d, 0d, 0d);

	protected ArrayList<V3D> edges = null;
	protected ArrayList<int[]> vertices = null;
	protected ArrayList<int[]> faces = null;
	private Color faceColor = Color.web("rgba(255,0,0,0.4)");
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
			points[i] = projection.Perspective(pts.get(i));
		}
		buffer.PolyLine(Color.BLACK, points);
	}

	public void draw() {
		Point2D[] points = new Point2D[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			points[i] = projection.Perspective(edges.get(i));
		}
		faces.forEach(face -> {
			Point2D[] faceP = new Point2D[face.length];
			boolean visible = true;
			for (int i = 0; i < face.length; i++) {
				if((faceP[i] = points[face[i]]) == null) {
					visible = false;
					break;
				}
			}
			if(visible)
			buffer.fillPoly(faceColor, faceP);
		});
		vertices.forEach(vert -> {
			if(points[vert[0]] != null && points[vert[1]] != null)
			buffer.line(points[vert[0]], points[vert[1]], vertColor);
		});
	}

	public void draw(double size_factor) {
		Point2D[] points = new Point2D[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			points[i] = projection.Perspective(edges.get(i));
			if (points[i] != null)
				points[i] = points[i].multiply(size_factor);
		}
		faces.forEach(face -> {
			Point2D[] faceP = new Point2D[face.length];
			boolean visible = true;
			for (int i = 0; i < face.length; i++) {
				if ((faceP[i] = points[face[i]]) == null) {
					visible = false;
					break;
				}
			}
			if (visible)
				buffer.fillPoly(faceColor, faceP);
		});
		vertices.forEach(vert -> {
			if (points[vert[0]] != null && points[vert[1]] != null)
				buffer.line(points[vert[0]], points[vert[1]], vertColor);
		});
	}

	public void drawEdges() {
		edges.forEach(edge -> {
			Point2D p = projection.Perspective(edge);
			if (p != null)
				buffer.circle(p.getX(), p.getY(), 1);
		});
	}

	public void drawEdges(double size_factor) {
		edges.forEach(edge -> {
			Point2D p = projection.Perspective(edge);
			if (p != null) {
				p = p.multiply(size_factor);
				buffer.circle(p.getX(), p.getY(), 1);
			}
		});
	}

	protected void cleanUpData() {
		console.log("\nCleaning the Model!\nThis may take a while...");
		long time = console.timestamp();
		console.log("\nStarting with: " + edges.size() + " edges!");
		ArrayList<V3D> ed = new ArrayList<V3D>();
		ArrayList<int[]> IndexOfDupes = new ArrayList<int[]>();
		int process_unit = (int) (edges.size() / 10);

		edges.forEach(a -> {
			boolean isUnique = true;
			int index = -1;
			if(edges.indexOf(a) % process_unit == 0) {
				System.out.print("| ");
			}
			for (int i = 0; i < ed.size(); i++) {
				if (ed.get(i).equals(a)) {
					isUnique = false;
					index = edges.indexOf(ed.get(i));
					i = ed.size();
				}
			}
			if (isUnique || ed.size() == 0)
				ed.add(a);
			else
				IndexOfDupes.add(new int[] { edges.indexOf(a), index });
		});

		console.log("\nFound " + IndexOfDupes.size() + " duplicates...");
		console.log("Edges have been reduced to: " + ed.size() + "!");
		console.log("\nStarting with: " + vertices.size() + " vertices..");
		console.log("Reconnecting vertices...");
		int process_unit2 = (int) (vertices.size() / 10);

		vertices.forEach(vert -> {
			if(vertices.indexOf(vert) % process_unit2 == 0) {
				System.out.print("| ");
			}
			for (int i = 0; i < vert.length; i++) {
				V3D searched = edges.get(vert[i]);
				for (V3D e : ed)
					if (searched.equals(e))
						vert[i] = ed.indexOf(e);
			}
		});

		console.log("\nVertices reconnected!");
		console.log("\nSearching for duplicates!");
		ArrayList<int[]> vert = new ArrayList<int[]>();

		vertices.forEach(v1 -> {
			if(vertices.indexOf(v1) % process_unit2 == 0) {
				System.out.print("| ");
			}
			boolean isUnique = true;
			for (int[] v2 : vert) {
				if ((v1[0] == v2[0] && v1[1] == v2[1]) || (v1[0] == v2[1] && v1[1] == v2[0])) {
					isUnique = false;
					break;
				}
			}
			if (isUnique || vert.size() == 0)
				vert.add(v1);
		});
		console.log("\nFound " + (vertices.size() - vert.size()) + " duplicates...");
		console.log("Vertices have been reduced to: " + vert.size() + "!");
		console.log("\nReconnecting faces...");
		int process_unit3 = (int) (faces.size() / 10);

		faces.forEach(face -> {
			if(faces.indexOf(face) % process_unit3 == 0) {
				System.out.print("| ");
			}
			for (int i = 0; i < face.length; i++) {
				V3D searched = edges.get(face[i]);
				for (V3D e : ed)
					if (searched.equals(e))
						face[i] = ed.indexOf(e);
			}
		});
		console.log("\nFaces reconnected!");
		edges.clear();
		edges.addAll(ed);
		vertices.clear();
		vertices.addAll(vert);
		
		console.log("\nFinished cleaning up the Model!");
		console.timestamp(time);
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

	public static Projection getProjection() {
		return projection;
	}

	public static void setProjection(Projection projection) {
		Obj3D.projection = projection;
	}
}
