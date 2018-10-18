package compute;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Camera {
	public boolean left, right, up, down, forwards, backwards, leftRot, rightRot, upRot, downRot;
	protected V3D position = null;
	protected V3D rotation = null;
	protected V3D RecordingSurface = null;
	protected V3D ViewerDistance = null;
	protected Point2D DisplaySize = null;
	protected V3D[] frustum_normals = initFrustumNormals();
	protected V3D[] frustum = initFrustum();
	protected double[] OrthographicPlanes = null;

	public Camera(double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ) {
		left = false;
		right = false;
		up = false;
		down = false;
		forwards = false;
		backwards = false;
		setPos(PosX, PosY, PosZ);
		setRot(RotX, RotY, RotZ);
		setRecordingSurface(800d, 800d, -100d);
		setViewerDistance(0d, 0d, -100d);
		setDisplaySize(800d, 800d);
		OrthographicPlanes = new double[] { 1d, -1d, 1d, -1d, 0.1d, 1d };
	}

	private static V3D[] initFrustumNormals() {
		V3D[] fn = new V3D[6];
		fn[0] = new V3D(-1, 0, 0);
		fn[1] = new V3D(1, 0, 0);
		fn[2] = new V3D(0, -1, 0);
		fn[3] = new V3D(0, 1, 0);
		fn[4] = new V3D(0, 0, 1);
		fn[5] = new V3D(0, 0, -1);
		return fn;
	}

	private static V3D[] initFrustum() {
		V3D[] fn = new V3D[6];
		fn[0] = new V3D(800, 0, 0);
		fn[1] = new V3D(-800, 0, 0);
		fn[2] = new V3D(0, 800, 0);
		fn[3] = new V3D(0, -800, 0);
		fn[4] = new V3D(0, 0, 20);
		fn[5] = new V3D(0, 0, 1000);
		return fn;
	}

	public V3D transform(V3D vec) {
		double cx, cy, cz;
		cx = Math.cos(Math.toRadians(getXRot()));
		cy = Math.cos(Math.toRadians(getYRot()));
		cz = Math.cos(Math.toRadians(getZRot()));
		double sx, sy, sz;
		sx = Math.sin(Math.toRadians(getXRot()));
		sy = Math.sin(Math.toRadians(getYRot()));
		sz = Math.sin(Math.toRadians(getZRot()));
		double x, y, z;
		x = vec.getX() - getX();
		y = vec.getY() - getY();
		z = vec.getZ() - getZ();
		V3D ctrans = new V3D();
		ctrans.setX(cy * (sz * y + cz * x) - sy * z);
		ctrans.setY(sx * (cy * z + sy * (sz * y + cz * x)) + cx * (cz * y - sz * y));
		ctrans.setZ(cx * (cy * z + sy * (sz * y + cz * x)) - sx * (cz * y - sz * y));
		return ctrans;
	}

	protected Matrix transform_M(V3D vec) {
		Matrix result = Matrix.sub(vec, position);
		result = Projection.Tait_Bryan_RotationZ(getZRot()).mult(result);
		result = Projection.Tait_Bryan_RotationY(getYRot()).mult(result);
		result = Projection.Tait_Bryan_RotationX(getXRot()).mult(result);
		return result;
	}

	public void move(double x, double y, double z) {
		position.data[0][0] += x;
		position.data[1][0] += y;
		position.data[2][0] += z;
	}

	public void moveViewer(double x, double y, double z) {
		ViewerDistance.data[0][0] += x;
		ViewerDistance.data[1][0] += y;
		ViewerDistance.data[2][0] += z;
	}

	public void smoothMove(double value) {
		if (left)
			position.add(getMovementVec(1,0,0,value));
		if (right)
			position.add(getMovementVec(-1,0,0,value));
		if (up)
			position.add(getMovementVec(0,1,0,value));
		if (down)
			position.add(getMovementVec(0,-1,0,value));
		if (forwards)
			position.add(getMovementVec(0,0,1,value));
		if (backwards)
			position.add(getMovementVec(0,0,-1,value));
		if (upRot)
			rotation.data[0][0] -= value/2;
		if (downRot)
			rotation.data[0][0] += value/2;
		if (leftRot)
			rotation.data[1][0] += value/2;
		if (rightRot)
			rotation.data[1][0] -= value/2;
	}

	public void keyInput(KeyCode code, boolean activate) {
		switch (code) {
		case UP:
			upRot(activate);
			break;
		case DOWN:
			downRot(activate);
			break;
		case LEFT:
			leftRot(activate);
			break;
		case RIGHT:
			rightRot(activate);
			break;
		case W:
			forwards(activate);
			break;
		case A:
			left(activate);
			break;
		case S:
			backwards(activate);
			break;
		case D:
			right(activate);
			break;
		case SPACE:
			up(activate);
			break;
		case SHIFT:
			down(activate);
			break;
		default:
			break;
		}
	}

	public void rotate(double x, double y, double z) {
		rotation.data[0][0] += x;
		rotation.data[1][0] += y;
		rotation.data[2][0] += z;
	}

	public boolean isBehindCamera(V3D vec) {
		return (V3D.sub(vec, V3D.add(position, ViewerDistance))).skalar(getVec()) < 0;
	}

	public boolean inFrustum(V3D vec) {
		boolean visible = true;
		V3D vector = transform(vec);
		for (int i = 0; i < 6; i++) {
			if (V3D.sub(vector, frustum[i]).skalar(frustum_normals[i]) < 0) {
				visible = false;
				break;
			}
		}
		return visible;
	}

	public V3D clipFrustum(V3D vec) {
		int index = -1;
		V3D vector = transform(vec);
		for (int i = 0; i < 6; i++) {
			if (V3D.sub(vector, frustum[i]).skalar(frustum_normals[i]) < 0) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			return vec;
		} else {
			V3D p = V3D.fromMatrix(transform(vec));
			double d = frustum[index].skalar(frustum_normals[index]);
			double t = (d - frustum[index].getX() * p.getX() - frustum[index].getY() * p.getY()
					- frustum[index].getZ() * p.getZ())
					/ (-frustum[index].getX() * p.getX() - frustum[index].getY() * p.getY()
							- frustum[index].getZ() * p.getZ());
			return new V3D(vec.getX() - t * vec.getX(), vec.getY() - t * vec.getY(), vec.getZ() - t * vec.getZ());
		}
	}

	public void left(boolean type) {
		left = type;
	}

	public void right(boolean type) {
		right = type;
	}

	public void forwards(boolean type) {
		forwards = type;
	}

	public void backwards(boolean type) {
		backwards = type;
	}

	public void up(boolean type) {
		up = type;
	}

	public void down(boolean type) {
		down = type;
	}

	public void downRot(boolean type) {
		downRot = type;
	}

	public void upRot(boolean type) {
		upRot = type;
	}

	public void leftRot(boolean type) {
		leftRot = type;
	}

	public void rightRot(boolean type) {
		rightRot = type;
	}

	public V3D getPos() {
		return position;
	}

	public V3D setPos(double x, double y, double z) {
		position = new V3D(x, y, z);
		return position;
	}

	public V3D getRot() {
		return rotation;
	}

	public V3D setRot(double x, double y, double z) {
		rotation = new V3D(x, y, z);
		return rotation;
	}

	public V3D getVec() {
		V3D cv = new V3D(0, 0, 1);
		cv.rotateX(rotation.getX());
		cv.rotateY(rotation.getY());
		cv.rotateZ(rotation.getZ());
		return cv;
	}
	
	private V3D getMovementVec(double x, double y, double z, double value) {
		V3D vec = new V3D(x * value, y * value, z * value);
		vec.rotateX(rotation.getX());
		vec.rotateY(rotation.getY());
		vec.rotateZ(rotation.getZ());
		//vec.log();
		return vec;
	}

	public double getXRot() {
		return rotation.getX();
	}

	public double getYRot() {
		return rotation.getY();
	}

	public double getZRot() {
		return rotation.getZ();
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public double getZ() {
		return position.getZ();
	}

	public V3D getViewerDistance() {
		return ViewerDistance;
	}

	public double getViewerDistX() {
		return ViewerDistance.getX();
	}

	public double getViewerDistY() {
		return ViewerDistance.getY();
	}

	public double getViewerDistZ() {
		return ViewerDistance.getZ();
	}

	public void setViewerDistance(double x, double y, double z) {
		ViewerDistance = new V3D(x, y, z);
	}

	public void setDisplaySize(double width, double height) {
		DisplaySize = new Point2D(width, height);
	}

	public Point2D getDisplaySize() {
		return DisplaySize;
	}

	public double dispX() {
		return DisplaySize.getX();
	}

	public double dispY() {
		return DisplaySize.getY();
	}

	public void setRecordingSurface(double width, double height, double depth) {
		RecordingSurface = new V3D(width, height, depth);
	}

	public V3D getRecordingSurface() {
		return RecordingSurface;
	}

	public double getRecordingSurfaceX() {
		return RecordingSurface.getX();
	}

	public double getRecordingSurfaceY() {
		return RecordingSurface.getY();
	}

	public double getRecordingSurfaceZ() {
		return RecordingSurface.getZ();
	}
	
	public double getOPlane(int index) {
		return OrthographicPlanes[index];
	}
}
