package object3D;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import display.camera.computation.Vector4;

public class STL extends Obj3D {
	public STL(String path) {
		super();
		Path filePath = Paths.get(path);
		List<Vector4[]> faces = null;
		try {
			faces = STLParser.parseSTLFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Vector4[]> iterator = faces.iterator();

		while (iterator.hasNext()) {
			Vector4[] face = iterator.next();
			Collections.addAll(vertecies, face);
			this.faces.add(new Face(face));
		}

		System.out.println("Before Cleanup:");
		System.out.println("Vertecies: " + vertecies.size());
		System.out.println("Faces: " + faces.size());
		
		cleanUP();
		
		System.out.println("\nAfter Cleanup:");
		System.out.println("Vertecies: " + vertecies.size());
		System.out.println("Faces: " + faces.size());
		
		rotate(Math.PI/2, 0, Math.PI);
	}

}
