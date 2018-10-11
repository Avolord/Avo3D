package compute;

import javafx.geometry.Point2D;
import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class V3D extends Matrix {
	
	private static double right = 2d;
	private static double left = -2d;
	private static double top = 2d;
	private static double bottom = -2d;
	private static double near = 0.1d;
	private static double far = 2d;
	
	private static Matrix simpleOrtho = initSimpleOrtho();
	private static Matrix Ortho = initOrthographic();
	private static Matrix Cabinet = initCabinet(63.4d);
	private static Matrix Camera = initCamera(0d,0d,0d);
	
	
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
	
	public static Matrix initCamera(double x, double y, double z) {
		return Matrix.fromArray(new double[] {x,y,z,0});
	}

	private static Matrix initSimpleOrtho() {
		return Matrix.fromArray(new double[][] { 
			{ 1, 0, 0, 0 },
			{ 0, 1, 0, 0 }
		});
	}
	
	private static Matrix initOrthographic() {
		Matrix projection = Matrix.fromArray(new double[][] {
			{2/(right-left), 0, 0, -(right+left)/(right-left)},
			{0, 2/(top-bottom), 0, -(top+bottom)/(top-bottom)},
			{0, 0, 2/(far-near), -(far+near)/(far-near)},
			{0, 0, 0, 1}
		});
		return projection;
	}
	
	private static Matrix initCabinet(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{1, 0, 0.5*Math.cos(angle), 0},
			{0, 1, 0.5*Math.sin(angle), 0},
		});
	}
	
	public void log() {
		System.out.println("V3D:( "+data[0][0]+" | "+data[1][0]+" | "+data[2][0]+" )");
	}
	
	public static void moveCamera(double x, double y, double z) {
		Camera.data[0][0]+=x;
		Camera.data[1][0]+=y;
		Camera.data[2][0]+=z;
	}
	
	public static void Zoom(double value) {
		right = (right >= value) ? right-value : right;
		left = (left >= value) ? left+value : left;
		top = (top >= value) ? top-value : top;
		bottom = (bottom >= value) ? bottom+value : bottom;
		Ortho = initOrthographic();
	}


	public Point2D simpleOrtho() {
		Matrix result = simpleOrtho.mult(this);
		return new Point2D(result.data[0][0], result.data[1][0]);
	}

	public void drawSimpleOrtho() {
		Point2D p = simpleOrtho();
		buffer.circle(p.getX(), p.getY(), 2);
	}

	public void simpleOrthoLine(V3D point) {
		Point2D p1 = this.simpleOrtho();
		Point2D p2 = point.simpleOrtho();
		V3D.buffer.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public void simpleOrthoLine(double x, double y, double z) {
		Point2D p1 = this.simpleOrtho();
		Point2D p2 = new V3D(x, y, z).simpleOrtho();
		V3D.buffer.line(p1.getX(), p1.getY()+100, p2.getX(), p2.getY()+100);
	}
	
	public Point2D weakPerspective() {
		Matrix projection = Matrix.fromArray(new double[][] {
			{1/data[2][0], 0, 0},
			{0, 1/data[2][0], 0}
		});
		Matrix result = projection.mult(this);
		return new Point2D(result.data[0][0], result.data[1][0]);
	}
	
	public void weakPerspectiveLine(V3D point) {
		Point2D p1 = this.weakPerspective();
		Point2D p2 = point.weakPerspective();
		V3D.buffer.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public Point2D Cabinet() {
		Matrix result = Cabinet.mult(this);
		return new Point2D(result.data[0][0], result.data[1][0]);
	}
	
	public static Matrix rotateX(V3D vector, double degree) {
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0}
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
	
	public Point2D Orthographic() {
		Matrix result = Ortho.mult(Matrix.add(this, Camera));
		return new Point2D(result.data[0][0], result.data[1][0]);
	}

	public static Matrix getSimpleOrtho() {
		return simpleOrtho;
	}

	public static void setSimpleOrtho(Matrix simpleOrtho) {
		V3D.simpleOrtho = simpleOrtho;
	}

	public static Matrix getOrtho() {
		return Ortho;
	}

	public static void setOrtho(Matrix ortho) {
		Ortho = ortho;
	}

	public static Matrix getCabinet() {
		return Cabinet;
	}

	public static void setCabinet(Matrix cabinet) {
		Cabinet = cabinet;
	}

	public static ObjectBuffer2D getBuffer() {
		return buffer;
	}

	public static void setBuffer(ObjectBuffer2D buffer) {
		V3D.buffer = buffer;
	}
	
}
