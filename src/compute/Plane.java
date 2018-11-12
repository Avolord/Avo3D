package compute;

public final class Plane {
	private V3D normal, base;
	private double dist;

	public Plane(double x, double y, double z, double nx, double ny, double nz) {
		base = new V3D(x, y, z);
		normal = new V3D(nx, ny, nz);
		calculateD(new V3D());
	}

	public Plane(double x, double y, double z, double dist) {
		normal = new V3D(x, y, z);
		this.dist = dist;
		calculateB();
	}

	public Plane(V3D normal, V3D relative) {
		this.normal = normal;
		calculateD(relative);
		this.base = relative;
	}

	public Plane(V3D base, V3D normal, V3D relative) {
		this.base = base;
		this.normal = normal;
		calculateD(relative);
	}
	
	public Plane(V3D normal, double dist) {
		this.normal = normal;
		this.dist = dist;
		calculateB();
	}

	private void calculateD(V3D relative) {
		dist = relative.skalar(normal);
	}

	private void calculateB() {
		if (normal.getX() != 0)
			base = new V3D(dist / normal.getX(), 0, 0);
		else if (normal.getY() != 0)
			base = new V3D(0, dist / normal.getY(), 0);
		else if (normal.getZ() != 0)
			base = new V3D(0, 0, dist / normal.getZ());
		else
			base = new V3D();
	}

	public V3D lineIntersect(V3D A, V3D B) {
		V3D dir = V3D.sub(B, A);
		double t;
		try {
			t = (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return null;
		}
		if (t >= 0 && t <= 1) {
			dir.skalar_mult(t);
			return V3D.add(A, dir);
		}
		return null;
	}

	public V3D lineIntersectFull(V3D A, V3D B) {
		V3D dir = V3D.sub(B, A);
		double t;
		try {
			t = (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return null;
		}
		dir.skalar_mult(t);
		return V3D.add(A, dir);
	}
	
	public double lineIntersectValue(V3D A, V3D B) {
		V3D dir = V3D.sub(B, A);
		try {
			return (dist - normal.skalar(A)) / (normal.skalar(dir));
		} catch (ArithmeticException e) {
			return -1;
		}
	}

	public void log() {
		System.out.println(normal.getX() + "x + " + normal.getY() + "y + " + normal.getZ() + "z = " + dist);
	}

	public boolean behind(V3D vec) {
		return normal.skalar(V3D.sub(vec, base)) < 0;
	}
}
