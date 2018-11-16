package object3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import display.FX;
import display.camera.computation.Vector4;

public class Face {
	private ArrayList<Vector4> vertecies = new ArrayList<Vector4>();

	public Face(Vector4... vertecies) { // We want shared memory
		Collections.addAll(this.vertecies, vertecies);
	}

	public Face(boolean sharedMemory, Vector4... vertecies) {
		if (sharedMemory)
			Collections.addAll(this.vertecies, vertecies);
		else {
			for (int i = 0; i < vertecies.length; i++)
				this.vertecies.add(vertecies[i].dupe());
		}

	}

	public ArrayList<Vector4> clipping() {
		ArrayList<Vector4> result = new ArrayList<Vector4>();
		Iterator<Vector4> v_iterator = vertecies.iterator();

		Vector4 previousVertex = v_iterator.next();
		boolean previousInside = FX.projector.infront(previousVertex);

		if (previousInside) {
			result.add(previousVertex);
		}

		while (v_iterator.hasNext()) {
			Vector4 currentVertex = v_iterator.next();
			boolean currentInside = FX.projector.infront(currentVertex);
			
			if (currentInside) {
				result.add(currentVertex);
			}
			
			if (currentInside ^ previousInside) {
				result.add(FX.projector.clipToNearPlane(currentVertex, previousVertex));
			}
			previousVertex = currentVertex;
			previousInside = currentInside;
		}
		
		if(FX.projector.infront(vertecies.get(0)) ^ previousInside) {
			result.add(FX.projector.clipToNearPlane(vertecies.get(0), previousVertex));
		}
		return result;
	}

	public ArrayList<Vector4> getVertecies() {
		return vertecies;
	}

	public void setVertecies(ArrayList<Vector4> vertecies) {
		this.vertecies = vertecies;
	}
	
	public void replaceVertex(Vector4 current, Vector4 replacement) {
		vertecies.set(vertecies.indexOf(current), replacement);
	}

}
