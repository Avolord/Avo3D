package compute;

import screen.JFXdraw;
import screen.ObjectBuffer2D;

public class V3D extends Matrix {
			
	private static ObjectBuffer2D buffer = JFXdraw.getBuffer2D();
	
	public V3D() {
		super(4, 1);
	}

	public V3D(double x, double y, double z) {
		super(4, 1);
		data[0][0] = x;
		data[1][0] = y;
		data[2][0] = z;
		data[3][0] = 1;
	}
	
	public V3D(double x, double y, double z, double w) {
		super(4, 1);
		data[0][0] = x;
		data[1][0] = y;
		data[2][0] = z;
		data[3][0] = w;
	}
	
	public static V3D fromMatrix(Matrix matrix) {
		if(matrix.getRows() != 4 || matrix.getCols() != 1) {
			return null;
		}
		
		return new V3D(matrix.getData(0, 0),
				matrix.getData(1, 0),
				matrix.getData(2, 0),
				matrix.getData(3, 0)
				);
	}
	
	public static V3D fromArray(double[] array) {
		if(array.length != 4 && array.length != 3) {
			return null;
		}
		
		return new V3D(array[0],
				array[0],
				array[0],
				(array.length == 4) ? array[3] : 1
				);
	}
	
	public static V3D random(int min, int max) {
		V3D result = new V3D();
		result.randomize(min, max);
		return result;
	}
	
	public static V3D random(double min, double max) {
		V3D result = new V3D();
		result.randomize(min, max);
		return result;
	}

	public static void initBuffer(ObjectBuffer2D buffer) {
		V3D.buffer = buffer;
	}
	
	public void log() {
		System.out.println("V3D:( "+data[0][0]+" | "+data[1][0]+" | "+data[2][0]+" )");
	}
	
	public boolean equals(V3D v2) {
		return (this.getX() == v2.getX() && this.getY() == v2.getY() && this.getZ() == v2.getZ());
	}
	
	public V3D dupe() {
		return new V3D(data[0][0], data[1][0], data[2][0], data[3][0]);
	}
	
	public double distance(V3D vector) {
		double dx = this.getX() - vector.getX();
		double dy = this.getY() - vector.getY();
		double dz = this.getZ() - vector.getZ();
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public double distance(double x, double y, double z) {
		double dx = this.getX() - x;
		double dy = this.getY() - y;
		double dz = this.getZ() - z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public double skalar(V3D vector) {
		return this.getData(0, 0) * vector.getData(0, 0) + this.getData(1, 0) * vector.getData(1, 0)
				+ this.getData(2, 0) * vector.getData(2, 0);
	}
	
	public static V3D add(V3D vectorA, V3D vectorB) {
		return new V3D(
			vectorA.getX() + vectorB.getX(),
			vectorA.getY() + vectorB.getY(),
			vectorA.getZ() + vectorB.getZ()
			);
	}
	
	public static V3D sub(V3D vectorA, V3D vectorB) {
		return new V3D(
			vectorA.getX() - vectorB.getX(),
			vectorA.getY() - vectorB.getY(),
			vectorA.getZ() - vectorB.getZ()
			);
	}
	
	public static V3D rotateX(V3D vector, double degree) {
		if(degree == 0) {
			return vector.dupe();
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		return V3D.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateX(double degree) {
		if(degree == 0) {
			return;
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public void rotateX(double angle, boolean rad) {
		if(angle == 0) {
			return;
		}
		angle = (rad) ? angle : Math.toRadians(angle);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle), 0},
			{0, Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static V3D rotateY(V3D vector, double degree) {
		if(degree == 0) {
			return vector.dupe();
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle), 0},
			{0, 1, 0, 0},
			{-Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		return V3D.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateY(double degree) {
		if(degree == 0) {
			return;
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle), 0},
			{0, 1, 0, 0},
			{-Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public void rotateY(double angle, boolean rad) {
		if(angle == 0) {
			return;
		}
		angle = (rad) ? angle : Math.toRadians(angle);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle), 0},
			{0, 1, 0, 0},
			{-Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static V3D rotateZ(V3D vector, double degree) {
		if(degree == 0) {
			return vector.dupe();
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0, 0},
			{Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		return V3D.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateZ(double degree) {
		if(degree == 0) {
			return;
		}
		double angle = Math.toRadians(degree);
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0, 0},
			{Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public void rotateZ(double angle, boolean rad) {
		if(angle == 0) {
			return;
		}
		angle = (rad) ? angle : Math.toRadians(angle);
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
	
	public void setX(double value) {
		data[0][0] = value;
	}
	
	public double getY() {
		return data[1][0];
	}
	
	public void setY(double value) {
		data[1][0] = value;
	}

	public double getZ() {
		return data[2][0];
	}
	
	public void setZ(double value) {
		data[2][0] = value;
	}
	
	public double getW() {
		return data[3][0];
	}
	
	public void setW(double value) {
		data[3][0] = value;
	}
	
}
