package compute;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.console;

public class Matrix {
	protected double[][] data;
	protected int cols;
	protected int rows;

	public Matrix(int rows, int cols, double fill) {

		this.cols = cols;
		this.rows = rows;
		data = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = fill;
			}
		}
	}

	public Matrix(int rows, int cols) {

		this.cols = cols;
		this.rows = rows;
		data = new double[rows][cols];
	}

	private static double random(double min, double max) {
		return Math.floor(min + Math.random() * (max + 1 - min));
	}

	private static double random_float(double min, double max) {
		return min + Math.random() * (max - min);
	}

	private static double ArrayMult(double[] A, double[] B) {
		if (A.length != B.length) {
			return 0.0;
		}
		double result = 0.0;
		for (int i = 0; i < A.length; i++) {
			result += A[i] * B[i];
		}
		return result;
	}

	public void randomize(int min, int max) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Matrix.random(min, max);
			}
		}
	}

	public void randomize(double min, double max) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Matrix.random_float(min, max);
			}
		}
	}

	public static Matrix random(int rows, int cols, int min, int max) {
		Matrix result = new Matrix(rows, cols);
		result.randomize(min, max);
		return result;
	}

	public static Matrix random(int rows, int cols, double min, double max) {
		Matrix result = new Matrix(rows, cols);
		result.randomize(min, max);
		return result;
	}

	public void fill(double fill) {
		for (int i = 0; i < rows; i++)
			Arrays.fill(data[i], fill);
	}

	public static Matrix fill(int rows, int cols, double fill) {
		Matrix result = new Matrix(rows, cols);
		result.fill(fill);
		return result;
	}

	public void fillRow(int row, double fill) {
		Arrays.setAll(data[row], value -> fill);
	}

	public void fillCol(int column, double fill) {
		for (int i = 0; i < rows; i++)
			data[i][column] = fill;
	}

	public void diagnonal(double diagonal, double fill) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j)
					data[i][j] = diagonal;
				else
					data[i][j] = fill;
			}
		}
	}

	public static Matrix diagonal(int rows, int cols, double diagonal, double fill) {
		Matrix result = new Matrix(rows, cols);
		result.diagnonal(diagonal, fill);
		return result;
	}

	public void unit() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j)
					data[i][j] = 1;
				else
					data[i][j] = 0;
			}
		}
	}

	public static Matrix unit(int rows, int cols) {
		Matrix result = new Matrix(rows, cols);
		result.unit();
		return result;
	}

	public void triangle(boolean above, boolean below) {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {

				if (i > j && below) // below-check
					data[i][j] = 0;

				if (i < j && above) // above-check
					data[i][j] = 0;
			}
	}

	public boolean isTriangle() {
		boolean above = true;
		boolean below = true;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++) {
				if (i > j && data[i][j] != 0)
					below = false;
				if (i < j && data[i][j] != 0)
					above = false;
			}
		return (above || below);
	}

	public boolean emptyRow() {
		return !Arrays.stream(data).anyMatch(y -> Arrays.stream(y).anyMatch(c -> c != 0));
	}

	public boolean emptyColumn() {
		Matrix M = transpose();
		return !Arrays.stream(M.data).anyMatch(y -> Arrays.stream(y).anyMatch(c -> c != 0));
	}

	public boolean hasEmpty() {
		return (emptyRow() || emptyColumn());
	}

	public void add(Matrix M) {
		if (this.rows != M.rows || this.cols != M.cols) {
			console.log("The rows and cols didnt match!");
			return;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] + M.data[i][j];
			}
		}
	}

	public static Matrix add(Matrix M1, Matrix M2) {
		if (M1.rows != M2.rows || M1.cols != M2.cols) {
			console.log("The rows and cols didnt match!");
			return M1;
		}
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] + M2.data[i][j];
			}
		}
		return result;
	}

	public void add(double x) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] + x;
			}
		}
	}

	public static Matrix add(Matrix M1, double x) {
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] + x;
			}
		}
		return result;
	}

	public void sub(Matrix M) {
		if (this.rows != M.rows || this.cols != M.cols) {
			console.log("The rows and cols didnt match!");
			return;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] - M.data[i][j];
			}
		}
	}

	public static Matrix sub(Matrix M1, Matrix M2) {
		if (M1.rows != M2.rows || M1.cols != M2.cols) {
			console.log("The rows and cols didnt match!");
			return M1;
		}
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] - M2.data[i][j];
			}
		}
		return result;
	}

	public void sub(double x) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] - x;
			}
		}
	}

	public static Matrix sub(Matrix M1, double x) {
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] - x;
			}
		}
		return result;
	}

	public Matrix mult(Matrix M) {
		if (this.cols != M.rows) {
			console.log("The rows and cols didnt match!");
			return this;
		}
		Matrix result = new Matrix(this.rows, M.cols, 0);
		Matrix helper = M.transpose();
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < M.cols; j++) {
				result.data[i][j] = Matrix.ArrayMult(data[i], helper.data[j]);
			}
		}
		return result;
	}

	public void skalar_mult(Matrix M) {
		if (this.rows != M.rows || this.cols != M.cols) {
			console.log("The rows and cols didnt match!");
			return;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] * M.data[i][j];
			}
		}
	}

	public void skalar_mult(double x) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] * x;
			}
		}
	}

	public static Matrix skalar_mult(Matrix M1, Matrix M2) {
		if (M1.rows != M2.rows || M1.cols != M2.cols) {
			console.log("The rows and cols didnt match!");
			return M1;
		}
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] * M2.data[i][j];
			}
		}
		return result;
	}

	public static Matrix skalar_mult(Matrix M1, double x) {
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] * x;
			}
		}
		return result;
	}

	public void div(Matrix M) {
		if (this.rows != M.rows || this.cols != M.cols) {
			console.log("The rows and cols didnt match!");
			return;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = data[i][j] / M.data[i][j];
			}
		}
	}

	public static Matrix div(Matrix M1, double x) {
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] /= x;
			}
		}
		return result;
	}

	public void div(double x) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] /= x;
			}
		}
	}

	public static Matrix div(Matrix M1, Matrix M2) {
		if (M1.rows != M2.rows || M1.cols != M2.cols) {
			console.log("The rows and cols didnt match!");
			return M1;
		}
		Matrix result = new Matrix(M1.rows, M1.cols, 0);
		for (int i = 0; i < M1.rows; i++) {
			for (int j = 0; j < M1.cols; j++) {
				result.data[i][j] = M1.data[i][j] / M2.data[i][j];
			}
		}
		return result;
	}

	public static Matrix fromArray(double[] arr) {
		Matrix result = new Matrix(arr.length, 1, 0);
		for (int i = 0; i < arr.length; i++) {
			result.data[i][0] = arr[i];
		}
		return result;
	}

	public static Matrix fromArray(double[][] arr) {
		for (double[] col : arr) {
			if (col.length != arr[0].length) {
				console.log("All collums have to be of equal length!");
				return null;
			}
		}
		Matrix result = new Matrix(arr.length, arr[0].length, 0);
		for (int i = 0; i < arr.length; i++) {
			result.data[i] = arr[i].clone();
		}
		return result;
	}

	public double[][] toArray() {
		double[][] result = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			result[i] = data[i].clone();
		}
		return result;
	}

	public double[] toArray_flat() {
		double[] result = new double[rows * cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result[i * cols + j] = data[i][j];
			}
		}
		return result;
	}

	public void invert() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = -data[i][j];
			}
		}
	}
	
	public void invertRow(int row) {
		Arrays.setAll(data[row], x -> -x);
	}
	
	public void invertColumn(int column) {
		for(int i=0; i<rows; i++)
			data[i][column] *= -1;
	}

	public static Matrix invert(Matrix M) {
		Matrix result = new Matrix(M.rows, M.cols, 0);
		for (int i = 0; i < M.rows; i++) {
			for (int j = 0; j < M.cols; j++) {
				result.data[i][j] = -M.data[i][j];
			}
		}
		return result;
	}

	public double reduce() {
		double result = 0;
		for (double[] row : data)
			for (double col : row)
				result += col;
		return result;
	}

	public String stringify_auto() {
		return new String(rows + ";" + cols + ";").concat(Arrays.deepToString(data));
	}

	public String stringify() {
		String result = "rows:" + rows + "; cols:" + cols + "; data:";
		return result.concat(Arrays.deepToString(data));
	}

	public static Matrix fromString_regex(String matrix) {
		Pattern data_p = Pattern.compile("(\\w:)?(\\[+)(.*?)(\\]])"); // w -> w+ or remove w because its "useless"
		Pattern dim_p = Pattern.compile("(\\w+:)?(\\d+)(\\;)");

		Matcher data_m = data_p.matcher(matrix);
		Matcher dim_m = dim_p.matcher(matrix);

		data_m.find();
		String[] data = data_m.group(3).split("\\], \\[");

		dim_m.find();
		int rows = Integer.parseInt(dim_m.group(2));
		dim_m.find();
		int cols = Integer.parseInt(dim_m.group(2));

		Matrix result = new Matrix(rows, cols, 0);

		for (int i = 0; i < result.rows; i++) {

			String[] res = data[i].split(", ");
			Arrays.setAll(result.data[i], k -> Double.parseDouble(res[k]));
		}

		return result;
	}

	public static Matrix fromString(String matrix) {
		String[] mat = matrix.split(";");
		String[] rws = mat[2].split("-");

		Matrix result = new Matrix(Integer.parseInt(mat[0]), Integer.parseInt(mat[1]), 0);
		for (int i = 0; i < result.rows; i++) {

			String[] res = rws[i].split(",");
			Arrays.setAll(result.data[i], k -> Double.parseDouble(res[k]));
		}
		return result;
	}

	public Matrix transpose() {
		Matrix result = new Matrix(cols, rows, 0);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.data[j][i] = data[i][j];
			}
		}
		return result;
	}
	
	public void log() {
		console.log(data);
	}

	public Matrix copy() {
		Matrix result = new Matrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.data[i][j] = data[i][j];
			}
		}
		return result;
	}
	
	public Matrix clone_copy() {
		Matrix res = new Matrix(rows,cols);
		res.data = data.clone();
		return res;
	}

	public int[] getDim() {
		return new int[] { rows, cols };
	}

	public double getData(int row, int column) {
		return data[row][column];
	}

	public double[][] getData() {
		return data;
	}

	public void setData(int row, int column, double value) {
		data[row][column] = value;
	}

	public void setData(double value) {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				data[i][j] = value;
	}
	
	public double determinant() {
		return determinant(0);
	}
	
	private double determinant(int iterations) { 
		//iterations are an Error-catch so that the function doesn't have too much recursion!
	    if (iterations > 100) {
	      console.log("Uhh..well..something went wrong...I guess?");
	      return -1;
	    }

	    if (isTriangle()) { //Check if it is a triangle-matrix
	      int result = 1;
	      for (int i = 0; i < rows; i++) {
	        result *= data[i][i];
	      }
	      return Math.round(result * 1000) / 1000;
	    }

	    if (hasEmpty()) { //Check if a row or column consists of zeroes.
	      return 0.0;
	    }

	    if (cols == 2 && rows == 2) { //check if it's a 2x2 Matrix
	      return data[0][0] * data[1][1] - data[1][0] * data[0][1];
	    }

	    if (cols == 3 && rows == 3) { //check if it's a 3x3 Matrix [Sarrus-rule]
	      return data[0][0] * data[1][1] * data[2][2] +
	        data[0][1] * data[1][2] * data[2][0] +
	        data[0][2] * data[1][0] * data[2][1] -
	        data[0][2] * data[1][1] * data[2][0] -
	        data[0][0] * data[1][2] * data[2][1] -
	        data[0][1] * data[1][0] * data[2][2];
	    }

	    //AvoLords aproach to a gauß elimination -> creating a trinagle Matrix [below]
	    Matrix M = copy();

	    int start = 1;

	    for (int j = 0; j < M.cols - 1; j++) {

	      //-----Error Catch--------
	      if (M.data[j][j] == 0) {
	        int switchindex = j + 1;
	        Matrix M2 = M.copy();

	        while (M.data[j][j] == 0) {
	          if (switchindex > M.rows - 1)
	            if (M.hasEmpty())
	              return 0.0;
	            else
	              return -1;
	          if (M.data[switchindex][j] != 0) {
	            //M.data[j] = M2.data[switchindex].map(x => -1 * x);
	        	  M.data[j] = M2.data[switchindex].clone();
	        	  M.invertRow(j);
	            M.data[switchindex] = M2.data[j].clone();
	          }
	          switchindex++;
	        }
	      }
	      //------------------------

	      for (int i = start; i < M.rows; i++) {
	        double[] temp = M.data[j].clone();
	        //temp = temp.map(x => x * M.data[i][j] / M.data[j][j]);
	        
	        for(int u=0; u<M.rows; u++) {
	        	temp[u] = temp[u] * M.data[i][j] / M.data[j][j];
	        }
	        
	        for(int u=0; u<M.rows; u++) {
	        	M.data[i][u] = Math.round((M.data[i][u] - temp[u]) * 1000000) / 1000000;
	        }
	        //M.data[i] = M.data[i].map((x, z) => Math.round((x - temp[z]) * 1000000) / 1000000); //avoiding rounding errors (1.2e-103 != 0)

	      }
	      start++;
	    }
	    return M.determinant(iterations + 1);

	  }

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
