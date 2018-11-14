package display.camera.computation;

public final class Plane {
	private Vector4 normal, base;
	private double dist;

	public Plane(double x, double y, double z, double nx, double ny, double nz) {
		base = new Vector4(x, y, z);
		normal = new Vector4(nx, ny, nz);
		calculateD(new Vector4());
	}

	public Plane(double x, double y, double z, double dist) {
		normal = new Vector4(x, y, z);
		this.dist = dist;
		calculateB();
	}

	public Plane(Vector4 normal, Vector4 relative) {
		this.normal = normal;
		calculateD(relative);
		this.base = relative;
	}

	public Plane(Vector4 base, Vector4 normal, Vector4 relative) {
		this.base = base;
		this.normal = normal;
		calculateD(relative);
	}
	
	public Plane(Vector4 normal, double dist) {
		this.normal = normal;
		this.dist = dist;
		calculateB();
	}

	private void calculateD(Vector4 relative) {
		dist = relative.skalar(normal);
	}

	private void calculateB() {
		if (normal.getX() != 0)
			base = new Vector4(dist / normal.getX(), 0, 0);
		else if (normal.getY() != 0)
			base = new Vector4(0, dist / normal.getY(), 0);
		else if (normal.getZ() != 0)
			base = new Vector4(0, 0, dist / normal.getZ());
		else
			base = new Vector4();
	}

	public Vector4 lineIntersect(Vector4 A, Vector4 B) {
		Vector4 dir = Vector4.sub(B, A);
		double t;
		try {
			t = (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return null;
		}
		if (t >= 0 && t <= 1) {
			dir.skalar_mult(t);
			return Vector4.add(A, dir);
		}
		return null;
	}

	public Vector4 lineIntersectFull(Vector4 A, Vector4 B) {
		Vector4 dir = Vector4.sub(B, A);
		double t;
		try {
			t = (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return null;
		}
		dir.skalar_mult(t);
		return Vector4.add(A, dir);
	}
	
	public double lineIntersectValue(Vector4 A, Vector4 B) {
		Vector4 dir = Vector4.sub(B, A);
		try {
			return (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return -1;
		}
	}

	public void log() {
		System.out.println(normal.getX() + "x + " + normal.getY() + "y + " + normal.getZ() + "z = " + dist);
	}

	public boolean behind(Vector4 vec) {
		return normal.skalar(Vector4.sub(vec, base)) < 0;
	}
}
