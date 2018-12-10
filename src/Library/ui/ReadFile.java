package Library.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ReadFile{
	/**
	 * Array of string contain text
	 */
	public String[] data = new String[12];
	/**
	 * 2D array of character contain characters
	 */
	public char[][] map = new char[12][18];
	/**
	 * 2D array type of file
	 */
	char[][] file;
	
	
	/**
	 *Constructor for ReadFile
	 *
	 * @param z
	 */
	public ReadFile(int z){
		try {
			this.file = FileToRead(z);
		} catch (IOException exception) {
			throw new RuntimeException("FAILURE");
		}
	}
	
	 
	/**
	 * Read the target file and store data into an 2D array
	 *
	 * @param z
	 * @return 2D array
	 * @throws IOException
	 */
	public char[][] FileToRead(int z) throws IOException{
		FileReader file = new FileReader("Level" + z +".txt");
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(file);
		String text = "";
		String line = reader.readLine();
		// Text text is now a long string with every single character contained
		while(line != null){
			text += line;
			line = reader.readLine();
		}
		// Data data is now an array combined by strings
		String[] data = text.split("/");
		System.out.println(data);
		
		// Map map is now an 2D array storing all materials from the original txt file
		char[][] map = new char[12][18];
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[i].length(); j++){
				map[i][j] = data[i].charAt(j);
			}
		}
		return map;
	}
	
	public char[][] getFile(){
		return this.file;
	}
}


