package compute;

import javafx.geometry.Point2D;
import main.console;

public class Projection {
	protected static double right = 1d;
	protected static double left = -1d;
	protected static double top = 1d;
	protected static double bottom = -1d;
	protected static double near = 0.1d;
	protected static double far = 1d;
	protected static V3D[] frustum_normals = initFrustumNormals();
	protected static V3D[] frustum = initFrustum();
	
	protected static Matrix SimpleOrthographic = initSimpleOrtho();
	protected static Matrix Orthographic = initOrthographic();
	protected static Matrix Cabinet = initCabinet(63.4d);
	protected static V3D CameraPos = setCameraPos(0d,0d,-200d);
	protected static V3D CameraRot = setCameraRot(0d,0d,0d);
	protected static V3D RecordingSurface = setRecordingSurface(800d, 800d, -100d);
	protected static V3D ViewerDistance = setViewerDistance(0d, 0d, -100d);
	protected static Point2D DisplaySize = setDisplaySize(800d, 800d);

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
	
	private static V3D[] initFrustumNormals() {
		V3D[] fn = new V3D[6];
		fn[0] = new V3D(-1,0,0);
		fn[1] = new V3D(1,0,0);
		fn[2] = new V3D(0,-1,0);
		fn[3] = new V3D(0,1,0);
		fn[4] = new V3D(0,0,1);
		fn[5] = new V3D(0,0,-1);
		return fn;
	}
	
	private static V3D[] initFrustum() {
		V3D[] fn = new V3D[6];
		fn[0] = new V3D(300,0,0);
		fn[1] = new V3D(-300,0,0);
		fn[2] = new V3D(0,300,0);
		fn[3] = new V3D(0,-300,0);
		fn[4] = new V3D(0,0,20);
		fn[5] = new V3D(0,0,400);
		return fn;
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
		ViewerDistance.data[0][0]+=x;
		ViewerDistance.data[1][0]+=y;
		ViewerDistance.data[2][0]+=z;
	}
	
	public static void Zoom(double value) {
		right = (right >= value) ? right-value : right;
		left = (left >= value) ? left+value : left;
		top = (top >= value) ? top-value : top;
		bottom = (bottom >= value) ? bottom+value : bottom;
		Orthographic = initOrthographic();
	}
	
	public static Point2D simpleOrthographic(V3D vec) {
		Matrix result = SimpleOrthographic.mult(vec);
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
		if(!inFrustum(vec)) {
			return null;
		}
		V3D d = CameraTransform(vec);
		return new Point2D((ViewerDistance.getZ() / d.getZ()) * d.getX() + ViewerDistance.getX(),
				(ViewerDistance.getZ() / d.getZ()) * d.getY() + ViewerDistance.getY());
	}
	
	public static Point2D Perspective2(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		V3D d = CameraTransform(vec);
		return new Point2D (
		(d.getX() * DisplaySize.getX()) / (d.getZ() * RecordingSurface.getX()) * RecordingSurface.getZ(),
		(d.getY() * DisplaySize.getY()) / (d.getZ() * RecordingSurface.getY()) * RecordingSurface.getZ());
	}
	
	private static V3D CameraTransform(V3D vec) {
		double cx,cy,cz;
		cx = Math.cos(Math.toRadians(CameraRot.getX()));
		cy = Math.cos(Math.toRadians(CameraRot.getY()));
		cz = Math.cos(Math.toRadians(CameraRot.getZ())); 
		double sx,sy,sz;
		sx = Math.sin(Math.toRadians(CameraRot.getX()));
		sy = Math.sin(Math.toRadians(CameraRot.getY()));
		sz = Math.sin(Math.toRadians(CameraRot.getZ())); 
		double x,y,z;
		x = vec.getX() - CameraPos.getX();
		y = vec.getY() - CameraPos.getY();
		z = vec.getZ() - CameraPos.getZ();
		V3D ctrans = new V3D();
		ctrans.setX(cy * (sz * y + cz * x) - sy * z);
		ctrans.setY(sx * (cy * z + sy * (sz * y + cz * x)) + cx * (cz * y - sz * y));
		ctrans.setZ(cx * (cy * z + sy * (sz * y + cz * x)) - sx * (cz * y - sz * y));	
		return ctrans;
	}
	
	public static Point2D Perspective_(V3D vec) {
		double ex = ViewerDistance.getX();
		double ey = ViewerDistance.getY();
		double ez = ViewerDistance.getZ();
		Matrix result = Matrix.fromArray(new double[][] {
			{1, 0, -ex/ez, 0},
			{0, 1, -ey/ez, 0},
			{0, 0, 1, 0},
			{0, 0, -1/ez, 1}
		});
		result = result.mult(CameraTransform_M(vec));
		double x = result.getData(0,0) / result.getData(3,0);
		double y = result.getData(1,0) / result.getData(3,0);
		return new Point2D(x, y);
	}
	
	protected static Matrix CameraTransform_M(V3D vec) {
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
	
	public static boolean isBehindCamera(V3D vec) {
		return (V3D.sub(vec, V3D.add(CameraPos, ViewerDistance))).skalar(getCameraVec()) < 0;
	}
	
	public static boolean isBehindCamera2(V3D vec) {
		return (V3D.sub(vec, V3D.add(CameraPos, ViewerDistance))).skalar(getCameraVec()) < 0;
	}
	
	public static boolean inFrustum(V3D vec) {
		boolean visible = true;
		for(int i=0; i<6; i++) {
			V3D normal = frustum_normals[i].dupe();
			normal.rotateX(CameraRot.getX());
			normal.rotateY(CameraRot.getY());
			normal.rotateZ(CameraRot.getZ());
			if((V3D.sub(vec, V3D.add(CameraPos, frustum[i]))).skalar(normal) < 0) {
				visible = false;
				break;
			}
		}
		return visible;
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
	
	public static V3D getCameraVec() {
		V3D cv = new V3D(0,0,1);
		cv.rotateX(CameraRot.getX());
		cv.rotateY(CameraRot.getY());
		cv.rotateZ(CameraRot.getZ());
		return cv;
	}
	
	public static V3D getViewerDistance() {
		return ViewerDistance;
	}
	
	public static V3D setViewerDistance(double x, double y, double z) {
		ViewerDistance = new V3D(x,y,z);
		return ViewerDistance;
	}
	
	public static Point2D setDisplaySize(double width, double height) {
		DisplaySize = new Point2D(width, height);
		return DisplaySize;
	}
	
	public static Point2D getDisplaySize() {
		return DisplaySize;
	}
	
	public static V3D setRecordingSurface(double width, double height, double depth) {
		RecordingSurface = new V3D(width, height, depth);
		return RecordingSurface;
	}
	
	public static V3D getRecordingSurface() {
		return RecordingSurface;
	}
}
