package display.camera;

import display.camera.computation.Vector4;
import javafx.geometry.Point2D;

public class Projection extends Camera {

	public Projection(double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ) {
		super(PosX, PosY, PosZ, RotX, RotY, RotZ);
	}

	public Projection() {
		super(0d, 0d, 0d, 0d, 0d, 0d);
	}

	public Point2D Perspective(Vector4 vec) {
		Vector4 d = transform(vec);
		return new Point2D((ViewerDistance.getZ() / d.getZ()) * d.getX() + ViewerDistance.getX(),
				(ViewerDistance.getZ() / d.getZ()) * d.getY() + ViewerDistance.getY());
	}

	public Point2D Perspective2(Vector4 vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		Vector4 d = transform(vec);
		return new Point2D(
				(d.getX() * DisplaySize.getX()) / (d.getZ() * RecordingSurface.getX()) * RecordingSurface.getZ(),
				(d.getY() * DisplaySize.getY()) / (d.getZ() * RecordingSurface.getY()) * RecordingSurface.getZ());
	}
}
