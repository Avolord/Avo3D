package compute;

import javafx.geometry.Point2D;
import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class V3D extends Matrix {
			
	private static ObjectBuffer2D buffer = JFXdraw.getBuffer2D();

	public V3D(double x, double y, double z) {
		super(4, 1);
		data[0][0] = x;
		data[1][0] = y;
		data[2][0] = z;
		data[3][0] = 1;
	}

	public static void initBuffer(ObjectBuffer2D buffer) {
		V3D.buffer = buffer;
	}
	
	public void log() {
		System.out.println("V3D:( "+data[0][0]+" | "+data[1][0]+" | "+data[2][0]+" )");
	}
	
	public boolean equals(V3D v2) {
		return (data[0][0] == v2.getData(0,0) && data[1][0] == v2.getData(1,0) && data[2][0] == v2.getData(2,0));
	}

	public void drawSimpleOrtho() {
		Point2D p = Projection.simpleOrthographic(this);
		buffer.circle(p.getX(), p.getY(), 2);
	}

	public void simpleOrthoLine(V3D point) {
		Point2D p1 = Projection.simpleOrthographic(this);
		Point2D p2 = Projection.simpleOrthographic(point);
		V3D.buffer.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public void simpleOrthoLine(double x, double y, double z) {
		Point2D p1 = Projection.simpleOrthographic(this);
		Point2D p2 = Projection.simpleOrthographic(new V3D(x, y, z));
		V3D.buffer.line(p1.getX(), p1.getY()+100, p2.getX(), p2.getY()+100);
	}
	
	public void weakPerspectiveLine(V3D point) {
		Point2D p1 = Projection.weakPerspective(this);
		Point2D p2 = Projection.weakPerspective(point);
		V3D.buffer.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public static Matrix rotateX(V3D vector, double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		return rotate.mult(vector);
	}
	
	public void rotateX(double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static Matrix rotateY(V3D vector, double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle), 0},
			{0, 1, 0, 0},
			{-Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		return rotate.mult(vector);
	}
	
	public void rotateY(double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle), 0},
			{0, 1, 0, 0},
			{-Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static Matrix rotateZ(V3D vector, double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0, 0},
			{Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		return rotate.mult(vector);
	}
	
	public void rotateZ(double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0, 0},
			{Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}

	public static ObjectBuffer2D getBuffer() {
		return buffer;
	}

	public static void setBuffer(ObjectBuffer2D buffer) {
		V3D.buffer = buffer;
	}
	
	public double getX() {
		return data[0][0];
	}
	
	public double getY() {
		return data[1][0];
	}

	public double getZ() {
		return data[2][0];
	}
	
}