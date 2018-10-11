package transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AvoReader {
	private File file = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	private String path = "undefined";
	
	public AvoReader(File file) {
		initReader(file);
		setPath(file.getPath());
	}
	
	public AvoReader(String path) {
		initReader(new File(path));
		this.setPath(path);
	}
	
	private void initReader(File file) {
		try {
			this.file = file; 
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			System.out.println("Sucessfully loaded data!");
		} catch (FileNotFoundException e) {
			System.out.println("Error! Data couldn't be loaded!");
			e.printStackTrace();
		}
	}
	
	public String read() {
		String zeile = "";
		try {
			if((zeile = br.readLine()) == null) {
				return "done!";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zeile;
	}
	
	public void reopen() {
		initReader(file);
	}
	
	public void close() {
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
