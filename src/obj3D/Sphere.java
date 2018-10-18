package obj3D;

import compute.V3D;

public class Sphere extends Obj3D {
	private int amount = 25;
	
	public Sphere(double x, double y, double z, double radius) {
		super();
		initSphere(x, y, z, radius, amount);
	}

	public Sphere(double x, double y, double z, double radius, int amount) {
		super();
		initSphere(x, y, z, radius, amount);
	}

	private static V3D sphereCoordinates(double x, double y, double z, double radius, double angle1, double angle2) {
		x = x + radius * Math.cos(angle1) * Math.cos(angle2);
		y = y + radius * Math.cos(angle1) * Math.sin(angle2);
		z = z + radius * Math.sin(angle1);
		return new V3D(x, y, z);
	}

	private void initSphere(double x, double y, double z, double radius, int amount) {
		double step_size = 2 * Math.PI / (amount - 1);
		for (int i = 0; i < amount * 2; i++) {
			for (int j = 0; j < amount; j++) {
				edges.add(sphereCoordinates(x, y, z, radius, i * step_size / 2, j * step_size));
			}
		}
		for (int i = 0; i < amount - 1; i++) {
			for (int j = 1; j < amount * 2; j++) {
				vertices.add(new int[] { i + j * amount, i + 1 + j * amount });
				vertices.add(new int[] { i + (j - 1) * amount, i + j * amount });
				faces.add(new int[] { i + 1 + j * amount, i + j * amount, i + (j - 1) * amount,
						i + 1 + (j - 1) * amount });
			}
		}
		cleanFaces();
		// cleanUpData();
	}

	private void cleanFaces() {
		//faces = new ArrayList<int[]>(faces.subList(0, 48));
		for (int i = faces.size() - 1; i >= 49; i--) {
			if(i % 49 == 0)
				faces.remove(i);
		}
	}
}
