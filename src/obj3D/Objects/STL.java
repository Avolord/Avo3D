package obj3D.Objects;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import compute.STLParser;
import compute.V3D;
import obj3D.Obj3D;

public class STL extends Obj3D {
//	public STL(String path) {
//		super();
//		AvoReader data = new AvoReader(path);
//		String line = "";
//		Integer countLoops = 0;
//
//		Pattern removeEmpty = Pattern.compile("(\\s+)");
//		Pattern removeText = Pattern.compile("(\\s*)(vertex)(\\s*)");
//		Matcher matcher = null;
//
//		while (!(line = data.read()).contains("endsolid")) {
//
//			if (line.toLowerCase().contains("outer loop")) {
//				countLoops++;
//				continue;
//			}
//
//			if (line.contains("vertex")) {
//				matcher = removeText.matcher(line);
//				line = matcher.replaceAll("");
//				line = line.trim();
//				matcher = removeEmpty.matcher(line);
//				line = matcher.replaceAll(":");
//				String[] coords = line.split(":");
//				V3D vec = new V3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]),
//						Double.parseDouble(coords[2]));
//				edges.add(vec);
//			}
//		}
//
//		Integer PointsPerFace = (int) (edges.size() / countLoops);
//		console.log("Points per Face = " + PointsPerFace);
//
//		for (int i = 0; i < edges.size(); i += PointsPerFace) {
//
//			int[] faceIndexes = new int[PointsPerFace];
//
//			for (int j = 0; j < PointsPerFace; j++) {
//				faceIndexes[j] = i + j;
//				if (j < PointsPerFace - 1)
//					vertices.add(new int[] { i + j, i + j + 1 });
//				else
//					vertices.add(new int[] { i + j, i });
//			}
//
//			faces.add(faceIndexes);
//		}
//		cleanUpData();
//	}
	
	public STL(String path) {
		super();
		Path filePath = Paths.get(path);
		List<V3D[]> faces = null;
		try {
			faces = STLParser.parseSTLFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int length = faces.get(0).length;
		
		faces.forEach(face -> {
			int[] f = new int[length];
			for(int i=0; i<length;i++) {
				if(i < length-1) {
					vertices.add(new int[] {edges.size(), edges.size()+1});
				} else {
					vertices.add(new int[] {edges.size(), edges.size()-2});
				}
				f[i] = edges.size();
				edges.add(face[i]);
			}
			this.faces.add(f);
		});
		
		cleanUpData();
	}
	
}
