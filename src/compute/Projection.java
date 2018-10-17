package compute;

import java.util.Arrays;

import javafx.geometry.Point2D;
import main.console;

public class Projection{

	protected static double right = 1d;
	protected static double left = -1d;
	protected static double top = 1d;
	protected static double bottom = -1d;
	protected static double near = 0.1d;
	protected static double far = 1d;
	
	protected static Matrix SimpleOrthographic = initSimpleOrtho();
	protected static Matrix Orthographic = initOrthographic();
	protected static Matrix Cabinet = initCabinet(63.4d);
	protected static Camera camera = new Camera(0d, 0d, -200d, 0d, 0d, 0d);

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
	
	public static void Zoom(double value) {
		right = (right >= value) ? right-value : right;
		left = (left >= value) ? left+value : left;
		top = (top >= value) ? top-value : top;
		bottom = (bottom >= value) ? bottom+value : bottom;
		Orthographic = initOrthographic();
	}
	
	public static void moveCamera(double x, double y, double z) {
		camera.move(x, y, z);
	}
	
	public static void moveViewer(double x, double y, double z) {
		camera.moveViewer(x, y, z);
	}
	
	public static void smoothMove(double value) {
		camera.smoothMove(value);
	}
	
	public static void rotateCamera(double x, double y, double z) {
		camera.rotate(x, y, z);
	}
	
	public static Point2D simpleOrthographic(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		Matrix result = SimpleOrthographic.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D weakPerspective(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		Matrix result = Matrix.fromArray(new double[][] {
			{1/vec.getData(2,0), 0, 0},
			{0, 1/vec.getData(2,0), 0}
		}).mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Cabinet(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		Matrix result = Cabinet.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Orthographic(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		Matrix result = Orthographic.mult(Matrix.sub(vec, camera.getPos()));
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Perspective(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}		
		V3D d = camera.transform(vec);
		return new Point2D((camera.getViewerDistZ() / d.getZ()) * d.getX() + camera.getViewerDistX(),
				(camera.getViewerDistZ() / d.getZ()) * d.getY() + camera.getViewerDistY());
	}
	
	public static Point2D Perspective2(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		V3D d = camera.transform(vec);
		return new Point2D (
		(d.getX() * camera.dispX()) / (d.getZ() * camera.getRecordingSurfaceX()) * camera.getRecordingSurfaceZ(),
		(d.getY() * camera.dispY()) / (d.getZ() * camera.getRecordingSurfaceY()) * camera.getRecordingSurfaceZ());
	}
	
	public static Point2D Perspective_(V3D vec) {
		if(!camera.inFrustum(vec)) {
			return null;
		}
		double ex = camera.getViewerDistX();
		double ey = camera.getViewerDistY();
		double ez = camera.getViewerDistZ();
		Matrix result = Matrix.fromArray(new double[][] {
			{1, 0, -ex/ez, 0},
			{0, 1, -ey/ez, 0},
			{0, 0, 1, 0},
			{0, 0, -1/ez, 1}
		});
		result = result.mult(camera.transform_M(vec));
		double x = result.getData(0,0) / result.getData(3,0);
		double y = result.getData(1,0) / result.getData(3,0);
		return new Point2D(x, y);
	}
	
	static Matrix Tait_Bryan_RotationX(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), Math.sin(angle), 0},
			{0, -Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
	}
	
	static Matrix Tait_Bryan_RotationY(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, -Math.sin(angle), 0},
			{0, 1, 0, 0},
			{Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
	}
	
	static Matrix Tait_Bryan_RotationZ(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{Math.cos(angle), Math.sin(angle), 0, 0},
			{-Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
	}

	public static double getRight() {
		return right;
	}

	public static void setRight(double right) {
		Projection.right = right;
	}

	public static double getLeft() {
		return left;
	}

	public static void setLeft(double left) {
		Projection.left = left;
	}

	public static double getTop() {
		return top;
	}

	public static void setTop(double top) {
		Projection.top = top;
	}

	public static double getBottom() {
		return bottom;
	}

	public static void setBottom(double bottom) {
		Projection.bottom = bottom;
	}

	public static double getNear() {
		return near;
	}

	public static void setNear(double near) {
		Projection.near = near;
	}

	public static double getFar() {
		return far;
	}

	public static void setFar(double far) {
		Projection.far = far;
	}

	public static Matrix getSimpleOrtho() {
		return SimpleOrthographic;
	}

	public static void setSimpleOrtho(Matrix simpleOrtho) {
		Projection.SimpleOrthographic = simpleOrtho;
	}

	public static Matrix getOrtho() {
		return Orthographic;
	}

	public static void setOrtho(Matrix ortho) {
		Orthographic = ortho;
	}

	public static Matrix getCabinet() {
		return Cabinet;
	}

	public static void setCabinet(Matrix cabinet) {
		Cabinet = cabinet;
	}

	public static Camera getCamera() {
		return camera;
	}

	public static void setCamera(Camera camera) {
		Projection.camera = camera;
	}
}
