package obj3D.Objects;

import compute.V3D;
import obj3D.Obj3D;

public class Box extends Obj3D {
	public Box(double x, double y, double z, double w, double h, double d) {
		super(8, 12, 6);
		initCube(x, y, z, w, h, d);
	}

	private void initCube(double x, double y, double z, double w, double h, double d) {
		edges.add(new V3D(x, y, z));
		edges.add(new V3D(x + w, y, z));
		edges.add(new V3D(x + w, y + h, z));
		edges.add(new V3D(x, y + h, z));
		edges.add(new V3D(x, y, z + d));
		edges.add(new V3D(x + w, y, z + d));
		edges.add(new V3D(x + w, y + h, z + d));
		edges.add(new V3D(x, y + h, z + d));

		vertices.add(new int[] { 0, 1 });
		vertices.add(new int[] { 1, 2 });
		vertices.add(new int[] { 2, 3 });
		vertices.add(new int[] { 3, 0 });
		vertices.add(new int[] { 4, 5 });
		vertices.add(new int[] { 5, 6 });
		vertices.add(new int[] { 6, 7 });
		vertices.add(new int[] { 7, 4 });
		vertices.add(new int[] { 0, 4 });
		vertices.add(new int[] { 1, 5 });
		vertices.add(new int[] { 2, 6 });
		vertices.add(new int[] { 3, 7 });

		faces.add(new int[] { 0, 1, 2, 3 });
		faces.add(new int[] { 4, 5, 6, 7 });
		faces.add(new int[] { 0, 1, 5, 4 });
		faces.add(new int[] { 1, 2, 6, 5 });
		faces.add(new int[] { 2, 3, 7, 6 });
		faces.add(new int[] { 3, 0, 4, 7 });
	}
}
