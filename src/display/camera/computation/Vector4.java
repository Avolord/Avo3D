package display.camera.computation;

public class Vector4 extends Matrix {
			
	public static Vector4 ZERO = new Vector4(0,0,0,0);
	
	public Vector4() {
		super(4, 1);
	}

	public Vector4(double x, double y, double z) {
		super(4, 1);
		data[0][0] = x;
		data[1][0] = y;
		data[2][0] = z;
		data[3][0] = 1;
	}
	
	public Vector4(double x, double y, double z, double w) {
		super(4, 1);
		data[0][0] = x;
		data[1][0] = y;
		data[2][0] = z;
		data[3][0] = w;
	}
	
	public static Vector4 fromMatrix(Matrix matrix) {
		if(matrix.getRows() != 4 || matrix.getCols() != 1) {
			return null;
		}
		
		return new Vector4(matrix.getData(0, 0),
				matrix.getData(1, 0),
				matrix.getData(2, 0),
				matrix.getData(3, 0)
				);
	}
	
	public static Vector4 fromArray(double[] array) {
		if(array.length != 4 && array.length != 3) {
			return null;
		}
		
		return new Vector4(array[0],
				array[0],
				array[0],
				(array.length == 4) ? array[3] : 1
				);
	}
	
	public static Vector4 random(int min, int max) {
		Vector4 result = new Vector4();
		result.randomize(min, max);
		return result;
	}
	
	public static Vector4 random(double min, double max) {
		Vector4 result = new Vector4();
		result.randomize(min, max);
		return result;
	}
	
	public void log() {
		System.out.println("V3D:( "+data[0][0]+" | "+data[1][0]+" | "+data[2][0]+" )");
	}
	
	public boolean equals(Vector4 v2) {
		return (this.getX() == v2.getX() && this.getY() == v2.getY() && this.getZ() == v2.getZ());
	}
	
	public double value() {
		return Math.sqrt(data[0][0] * data[0][0] + data[1][0] * data[1][0] + data[2][0] * data[2][0]);
	}
	
	public Vector4 dupe() {
		return new Vector4(data[0][0], data[1][0], data[2][0], data[3][0]);
	}
	
	public double distance(Vector4 vector) {
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
	
	public double skalar(Vector4 vector) {
		return this.getData(0, 0) * vector.getData(0, 0) + this.getData(1, 0) * vector.getData(1, 0)
				+ this.getData(2, 0) * vector.getData(2, 0);
	}
	
	public static Vector4 add(Vector4 vectorA, Vector4 vectorB) {
		return new Vector4(
			vectorA.getX() + vectorB.getX(),
			vectorA.getY() + vectorB.getY(),
			vectorA.getZ() + vectorB.getZ()
			);
	}
	
	public void add(Vector4 vectorB) {
		data[0][0] += vectorB.getX();
		data[1][0] += vectorB.getY();
		data[2][0] += vectorB.getZ();
	}
	
	public void add(double x, double y, double z, double w) {
		data[0][0] += x;
		data[1][0] += y;
		data[2][0] += z;
		data[2][0] += w;
	}
	
	public static Vector4 sub(Vector4 vectorA, Vector4 vectorB) {
		return new Vector4(
			vectorA.getX() - vectorB.getX(),
			vectorA.getY() - vectorB.getY(),
			vectorA.getZ() - vectorB.getZ()
			);
	}
	
	public void sub(Vector4 vectorB) {
		data[0][0] -= vectorB.getX();
		data[1][0] -= vectorB.getY();
		data[2][0] -= vectorB.getZ();
	}
	
	public void sub(double x, double y, double z, double w) {
		data[0][0] -= x;
		data[1][0] -= y;
		data[2][0] -= z;
		data[2][0] -= w;
	}
	
	public void linear_euler_rotate_ZYX(double x_rotation, double y_rotation, double z_rotation) {
		
		double new_x = data[0][0] * (Math.cos(z_rotation) * Math.cos(y_rotation))
				+ data[1][0] * (Math.cos(z_rotation) * Math.sin(y_rotation) * Math.sin(x_rotation)
						- Math.sin(z_rotation) * Math.cos(x_rotation))
				+ data[2][0] * (Math.sin(z_rotation) * Math.sin(x_rotation)
						+ Math.cos(z_rotation) * Math.sin(y_rotation) * Math.cos(x_rotation));
		
		double new_y = data[0][0] * (Math.sin(z_rotation) * Math.cos(y_rotation))
				+ data[1][0] * (Math.sin(z_rotation) * Math.sin(y_rotation) * Math.sin(x_rotation)
						+ Math.cos(z_rotation) * Math.cos(x_rotation))
				+ data[2][0] * (Math.sin(z_rotation) * Math.sin(y_rotation) * Math.cos(x_rotation)
						- Math.cos(z_rotation) * Math.sin(x_rotation));
		
		double new_z = -data[0][0] * Math.sin(y_rotation) + data[1][0] * (Math.cos(y_rotation) * Math.sin(x_rotation))
				+ data[2][0] * (Math.cos(y_rotation) * Math.cos(x_rotation));
		
		data[0][0] = new_x;
		data[1][0] = new_y;
		data[2][0] = new_z;
	}
	
	public static Vector4 skalar_mult(Vector4 vectorA, double n) {
		return new Vector4(
			vectorA.getX()*n,
			vectorA.getY()*n,
			vectorA.getZ()*n
			);
	}
	
	public static Vector4 rotateX(Vector4 vector, double rad) {
		if(rad == 0) {
			return vector.dupe();
		}
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(rad), -Math.sin(rad), 0},
			{0, Math.sin(rad), Math.cos(rad), 0},
			{0, 0, 0, 1}
		});
		return Vector4.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateX(double rad) {
		Matrix rotate = Matrix.fromArray(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(rad), -Math.sin(rad), 0},
			{0, Math.sin(rad), Math.cos(rad), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static Vector4 rotateY(Vector4 vector, double rad) {
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(rad), 0, Math.sin(rad), 0},
			{0, 1, 0, 0},
			{-Math.sin(rad), 0, Math.cos(rad), 0},
			{0, 0, 0, 1}
		});
		return Vector4.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateY(double rad) {
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(rad), 0, Math.sin(rad), 0},
			{0, 1, 0, 0},
			{-Math.sin(rad), 0, Math.cos(rad), 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
	}
	
	public static Vector4 rotateZ(Vector4 vector, double rad) {
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(rad), -Math.sin(rad), 0, 0},
			{Math.sin(rad), Math.cos(rad), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		return Vector4.fromMatrix(rotate.mult(vector));
	}
	
	public void rotateZ(double rad) {
		Matrix rotate = Matrix.fromArray(new double[][] {
			{Math.cos(rad), -Math.sin(rad), 0, 0},
			{Math.sin(rad), Math.cos(rad), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		this.data = rotate.mult(this).data;
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
