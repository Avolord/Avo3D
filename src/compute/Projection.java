package compute;

import javafx.geometry.Point2D;

public class Projection {
	protected static double right = 2d;
	protected static double left = -2d;
	protected static double top = 2d;
	protected static double bottom = -2d;
	protected static double near = 0.1d;
	protected static double far = 2d;
	
	protected static Matrix simpleOrthographic = initSimpleOrtho();
	protected static Matrix Orthographic = initOrthographic();
	protected static Matrix Cabinet = initCabinet(63.4d);
	protected static V3D CameraPos = setCameraPos(0d,0d,-100d);
	protected static V3D CameraRot = setCameraRot(0d,0d,0d);
	protected static V3D viewerDistance = setViewerDistance(0d, 0d, 50d);

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
	
	public static void moveCamera(double x, double y, double z) {
		CameraPos.data[0][0]+=x;
		CameraPos.data[1][0]+=y;
		CameraPos.data[2][0]+=z;
	}
	
	public static void rotateCamera(double x, double y, double z) {
		CameraRot.data[0][0]+=x;
		CameraRot.data[1][0]+=y;
		CameraRot.data[2][0]+=z;
	}
	
	public static void moveViewer(double x, double y, double z) {
		viewerDistance.data[0][0]+=x;
		viewerDistance.data[1][0]+=y;
		viewerDistance.data[2][0]+=z;
	}
	
	public static void Zoom(double value) {
		right = (right >= value) ? right-value : right;
		left = (left >= value) ? left+value : left;
		top = (top >= value) ? top-value : top;
		bottom = (bottom >= value) ? bottom+value : bottom;
		Orthographic = initOrthographic();
	}
	
	public static Point2D simpleOrthographic(V3D vec) {
		Matrix result = simpleOrthographic.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D weakPerspective(V3D vec) {
		Matrix result = Matrix.fromArray(new double[][] {
			{1/vec.getData(2,0), 0, 0},
			{0, 1/vec.getData(2,0), 0}
		}).mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Cabinet(V3D vec) {
		Matrix result = Cabinet.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Orthographic(V3D vec) {
		Matrix result = Orthographic.mult(Matrix.sub(vec, CameraPos));
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public static Point2D Perspective(V3D vec) {
		double ex = viewerDistance.getX();
		double ey = viewerDistance.getY();
		double ez = viewerDistance.getZ();
		Matrix result = Matrix.fromArray(new double[][] {
			{1, 0, -ex/ez, 0},
			{0, 1, -ey/ez, 0},
			{0, 0, 1, 0},
			{0, 0, -1/ez, 1}
		});
		result = result.mult(CameraTransform(vec));
		double x = result.getData(0,0) / result.getData(3,0);
		double y = result.getData(1,0) / result.getData(3,0);
		return new Point2D(x, y);
	}
	
	protected static Matrix CameraTransform(V3D vec) {
		Matrix result = Matrix.sub(vec, CameraPos);
		result = Tait_Bryan_RotationZ(CameraRot.getZ()).mult(result);
		result = Tait_Bryan_RotationY(CameraRot.getY()).mult(result);
		result = Tait_Bryan_RotationX(CameraRot.getX()).mult(result);
		return result;
	}
	
	private static Matrix Tait_Bryan_RotationX(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), Math.sin(angle), 0},
			{0, -Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
	}
	
	private static Matrix Tait_Bryan_RotationY(double angle) {
		angle = Math.toRadians(angle);
		return Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, -Math.sin(angle), 0},
			{0, 1, 0, 0},
			{Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
	}
	
	private static Matrix Tait_Bryan_RotationZ(double angle) {
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
		return simpleOrthographic;
	}

	public static void setSimpleOrtho(Matrix simpleOrtho) {
		Projection.simpleOrthographic = simpleOrtho;
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

	public static V3D getCameraPos() {
		return CameraPos;
	}

	public static V3D setCameraPos(double x, double y, double z) {
		CameraPos = new V3D(x,y,z);
		return CameraPos;
	}
	
	public static V3D getCameraRot() {
		return CameraRot;
	}
	
	public static V3D setCameraRot(double x, double y, double z) {
		CameraRot = new V3D(x,y,z);
		return CameraRot;
	}
	
	public static V3D getViewerDistance() {
		return viewerDistance;
	}
	
	public static V3D setViewerDistance(double x, double y, double z) {
		viewerDistance = new V3D(x,y,z);
		return viewerDistance;
	}
}
