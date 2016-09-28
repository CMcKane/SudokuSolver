import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class fileReader {

	private void createPuzzle(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			br.readLine();
			int w = Integer.parseInt(br.readLine());
			int h = Integer.parseInt(br.readLine());
			
			int grid[][] = new int[w*h][w*h];
			
			for(int i = 0; i < w*h; i++) {
				String currentLine = br.readLine();
				String[] rowNums = currentLine.split(" ");   // ("\\s+") if " " doesn't work
				for(int j = 0; j <w*h; j++) {
					grid[i][j] = Integer.parseInt(rowNums[j]);
				}
			}
			
			} catch(FileNotFoundException e) {
				System.err.println("Can not open "+ filename);
			} catch(IOException e) {
				System.err.println("Error reading file: " + filename);
			}
		}
	
	private int numOfZeros() {
		return 0;
	}
}
