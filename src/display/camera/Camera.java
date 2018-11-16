package display.camera;


import display.camera.computation.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Camera {
	public boolean left, right, up, down, forwards, backwards, leftRot, rightRot, upRot, downRot; // Camera Movement
	protected Vector4 position = null; // Camera position
	protected Vector4 rotation = null; // Camera rotation
	protected Vector4 RecordingSurface = null; //
	protected Vector4 ViewerDistance = null;
	protected Point2D DisplaySize = null;
	protected Plane[] frustum = initFrustum();

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
		setViewerDistance(0d, 0d, -200d);
		setDisplaySize(500d, 500d);
	}

	private static Plane[] initFrustum() {
		Plane[] plns = new Plane[6];
		Vector4 nearN = new Vector4(0, 0, 1);
		Vector4 nearD = new Vector4(0,0,10);
		
		Vector4 farN = new Vector4(0, 0, -1);
		Vector4 farD = new Vector4(0,0,1000);
		
		Vector4 leftN = new Vector4(1, 0, 0);
		Vector4 leftD = new Vector4(-500,0,0);
		
		Vector4 rightN = new Vector4(-1, 0, 0);
		Vector4 rightD = new Vector4(500,0,0);
		
		Vector4 topN = new Vector4(0, 1, 0);
		Vector4 topD = new Vector4(0,-500,0);
		
		Vector4 botN = new Vector4(0, -1, 0);
		Vector4 botD = new Vector4(0,500,0);
		
		plns[0] = new Plane(nearN, nearD);
		plns[1] = new Plane(farN, farD);
		plns[2] = new Plane(leftN, leftD);
		plns[3] = new Plane(rightN, rightD);
		plns[4] = new Plane(topN, topD);
		plns[5] = new Plane(botN, botD);
		
		return plns;
	}

	public Vector4 transform(Vector4 vec) {
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
		Vector4 ctrans = new Vector4();
		ctrans.setX(cy * (sz * y + cz * x) - sy * z);
		ctrans.setY(sx * (cy * z + sy * (sz * y + cz * x)) + cx * (cz * y - sz * y));
		ctrans.setZ(cx * (cy * z + sy * (sz * y + cz * x)) - sx * (cz * y - sz * y));
		return ctrans;
	}

	public void move(double x, double y, double z) {
		position.add(x,y,z,0);
	}

	public void moveViewer(double x, double y, double z) {
		ViewerDistance.add(x,y,z,0);
	}

	public void smoothMove(double value) {
		if (left)
			position.add(getMovementVec(1, 0, 0, value));
		if (right)
			position.add(getMovementVec(-1, 0, 0, value));
		if (up)
			position.add(getMovementVec(0, 1, 0, value));
		if (down)
			position.add(getMovementVec(0, -1, 0, value));
		if (forwards)
			position.add(getMovementVec(0, 0, 1, value));
		if (backwards)
			position.add(getMovementVec(0, 0, -1, value));
		if (upRot)
			rotation.sub(value / 2, 0, 0, 0);
		if (downRot)
			rotation.add(value / 2, 0, 0, 0);
		if (leftRot)
			rotation.add(0, value / 2, 0, 0);
		if (rightRot)
			rotation.sub(0, value / 2, 0, 0);
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
		rotation.add(x, y, z, 0);
	}

	public boolean isBehindCamera(Vector4 vec) {
		return (Vector4.sub(vec, Vector4.add(position, ViewerDistance))).skalar(getVec()) < 0;
	}

	public boolean inFrustum(Vector4 vec) {
		boolean visible = true;
		Vector4 vector = transform(vec);
		for (int i = 1; i < 6; i++) { 
			if (frustum[i].behind(vector)) {
				visible = false;
				break;
			}
		}
		return visible;
	}

	public boolean infront(Vector4 vec) {
		return !frustum[0].behind(transform(vec));
	}

	public Vector4 clipToNearPlane(Vector4 v1, Vector4 v2) {
		double intersectionValue = frustum[0].lineIntersectValue(transform(v1), transform(v2));
		return Vector4.add(v1, Vector4.skalar_mult(Vector4.sub(v2, v1), intersectionValue));
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

	public Vector4 getPos() {
		return position;
	}

	public Vector4 setPos(double x, double y, double z) {
		position = new Vector4(x, y, z);
		return position;
	}

	public Vector4 getRot() {
		return rotation;
	}

	public Vector4 setRot(double x, double y, double z) {
		rotation = new Vector4(x, y, z);
		return rotation;
	}

	public Vector4 getVec() {
		Vector4 cv = new Vector4(0, 0, 1);
		cv.rotateX(rotation.getX());
		cv.rotateY(rotation.getY());
		cv.rotateZ(rotation.getZ());
		return cv;
	}

	private Vector4 getMovementVec(double x, double y, double z, double value) {
		Vector4 vec = new Vector4(x * value, y * value, z * value);
		vec.rotateX(Math.toRadians(rotation.getX()));
		vec.rotateY(Math.toRadians(rotation.getY()));
		vec.rotateZ(Math.toRadians(rotation.getZ()));
		// vec.log();
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

	public Vector4 getViewerDistance() {
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
		ViewerDistance = new Vector4(x, y, z);
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
		RecordingSurface = new Vector4(width, height, depth);
	}

	public Vector4 getRecordingSurface() {
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
}
