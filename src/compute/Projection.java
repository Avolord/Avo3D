package compute;

import javafx.geometry.Point2D;

public class Projection extends Camera{
	
	protected Matrix SimpleOrthographic = null;
	protected Matrix Orthographic = null;
	protected Matrix Cabinet = null;

	public Projection(double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ) {
		super(PosX, PosY, PosZ, RotX, RotY, RotZ);
		initSimpleOrtho();
		initOrthographic();
		initCabinet(63.4d);
	}

	private static Matrix initSimpleOrtho() {
		return Matrix.fromArray(new double[][] { 
			{ 1, 0, 0, 0 },
			{ 0, 1, 0, 0 }
		});
	}
	
	private Matrix initOrthographic() {
		Matrix projection = Matrix.fromArray(new double[][] {
			{2/(getOPlane(0)-getOPlane(1)), 0, 0, -(getOPlane(0)+getOPlane(1))/(getOPlane(0)-getOPlane(1))},
			{0, 2/(getOPlane(2)-getOPlane(3)), 0, -(getOPlane(2)+getOPlane(3))/(getOPlane(2)-getOPlane(3))},
			{0, 0, 2/(getOPlane(5)-getOPlane(4)), -(getOPlane(5)+getOPlane(4))/(getOPlane(5)-getOPlane(4))},
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
	
	public Point2D simpleOrthographic(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		Matrix result = SimpleOrthographic.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public Point2D weakPerspective(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		Matrix result = Matrix.fromArray(new double[][] {
			{1/vec.getData(2,0), 0, 0},
			{0, 1/vec.getData(2,0), 0}
		}).mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public Point2D Cabinet(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		Matrix result = Cabinet.mult(vec);
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public Point2D Orthographic(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		Matrix result = Orthographic.mult(Matrix.sub(vec, position));
		return new Point2D(result.getData(0,0), result.getData(1,0));
	}
	
	public Point2D Perspective(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}		
		V3D d = transform(vec);
		return new Point2D((ViewerDistance.getZ() / d.getZ()) * d.getX() + ViewerDistance.getX(),
				(ViewerDistance.getZ() / d.getZ()) * d.getY() + ViewerDistance.getY());
	}
	
	public Point2D Perspective2(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		V3D d = transform(vec);
		return new Point2D (
		(d.getX() * DisplaySize.getX()) / (d.getZ() * RecordingSurface.getX()) * RecordingSurface.getZ(),
		(d.getY() * DisplaySize.getY()) / (d.getZ() * RecordingSurface.getY()) * RecordingSurface.getZ());
	}
	
	public Point2D Perspective_(V3D vec) {
		if(!inFrustum(vec)) {
			return null;
		}
		double ex = ViewerDistance.getX();
		double ey = ViewerDistance.getY();
		double ez = ViewerDistance.getZ();
		Matrix result = Matrix.fromArray(new double[][] {
			{1, 0, -ex/ez, 0},
			{0, 1, -ey/ez, 0},
			{0, 0, 1, 0},
			{0, 0, -1/ez, 1}
		});
		result = result.mult(transform_M(vec));
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

	public Matrix getSimpleOrtho() {
		return SimpleOrthographic;
	}

	public void setSimpleOrtho(Matrix simpleOrtho) {
		SimpleOrthographic = simpleOrtho;
	}

	public Matrix getOrtho() {
		return Orthographic;
	}

	public void setOrtho(Matrix ortho) {
		Orthographic = ortho;
	}

	public Matrix getCabinet() {
		return Cabinet;
	}

	public void setCabinet(Matrix cabinet) {
		Cabinet = cabinet;
	}
}
