package obj3D;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import compute.V3D;
import main.console;
import transfer.AvoReader;

public class STL extends Obj3D {
	public STL(String path) {
		super();
		AvoReader data = new AvoReader(path);
		String line = "";
		Integer countLoops = 0;

		Pattern removeEmpty = Pattern.compile("(\\s+)");
		Pattern removeText = Pattern.compile("(\\s*)(vertex)(\\s*)");
		Matcher matcher = null;

		while (!(line = data.read()).contains("endsolid")) {

			if (line.toLowerCase().contains("outer loop")) {
				countLoops++;
				continue;
			}

			if (line.contains("vertex")) {
				matcher = removeText.matcher(line);
				line = matcher.replaceAll("");
				line = line.trim();
				matcher = removeEmpty.matcher(line);
				line = matcher.replaceAll(":");
				String[] coords = line.split(":");
				V3D vec = new V3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]),
						Double.parseDouble(coords[2]));
				edges.add(vec);
			}
		}

		Integer PointsPerFace = (int) (edges.size() / countLoops);
		console.log("Points per Face = " + PointsPerFace);

		for (int i = 0; i < edges.size(); i += PointsPerFace) {

			int[] faceIndexes = new int[PointsPerFace];

			for (int j = 0; j < PointsPerFace; j++) {
				faceIndexes[j] = i + j;
				if (j < PointsPerFace - 1)
					vertices.add(new int[] { i + j, i + j + 1 });
				else
					vertices.add(new int[] { i + j, i });
			}

			faces.add(faceIndexes);
		}
		cleanUpData();
	}
	
}
