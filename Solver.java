import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Brute Force Sudoku Solver
 * 
 * Author: Rex Cummings
 * 
 * Date 9/25/2016
 */

public class Solver extends ParseCmdLine {
	private static String filename;
	private String comment;
	private int w;
	private int h;
	private int rowColSize;
	private int grid[][];

	/*
	 * Default constructor
	 */
	public Solver() {

	}

	/*
	 * InputFile Constructor
	 */
	public Solver(String fn, String cm, int width, int height, int[][] g) {
		filename = fn;
		comment = cm;
		w = width;
		h = height;
		grid = g;
	}

	public static void main(String[] args) {
		Solver s = new Solver();
		s.filename = "";
		ParseCmdLine parse = new ParseCmdLine();

		// Parse command line arguments
		s.setFilename(parse.getArgs(args));

		// Read the file to obtain contents
		readFile(filename, s);

		// int[][] testPuzzle1 = new int[][]{
		// {5,8,7,4,3,6,1,2},
		// {6,1,2,3,4,7,5,8},
		// {8,3,0,0,0,0,2,0},
		// {0,0,0,0,0,8,0,3},
		// {2,0,6,0,0,0,0,0},
		// {0,5,0,8,0,0,7,1},
		// {0,0,0,7,0,5,6,0},
		// {0,4,8,6,1,2,3,7}
		// };
		//
		// System.out.println(checkAllRows(s, testPuzzle1));
		// System.out.println(checkAllCols(s, testPuzzle1));
		
	}

	public static void readFile(String fn, Solver s) {
		File file = new File(fn);
		String line = "";
		String[] numbers;

		try {
			Scanner in = new Scanner(new FileReader(file));
			line = in.nextLine();
			if (line.startsWith("c")) {
				line = in.nextLine();
		
			s.setW(Integer.parseInt(line));
			System.out.println(s.getW());
			line = in.nextLine();
			s.setH(Integer.parseInt(line));
			System.out.println(s.getH());
			line = in.nextLine();
			s.rowColSize = s.getW()*s.getH();
			int grid1[][] = new int[s.rowColSize][s.rowColSize];

			while (in.hasNextLine()) {
				numbers = line.split(" ");

				for (int i = 0; i < s.getH(); i++)
					for (int j = 0; j < s.getW(); j++)
						grid1[i][j] = Integer.parseInt(numbers[j]);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file + "'");
			// ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error reading file '" + file + "'");
			// ex.printStackTrace();
		}
	}

	/*
	 * Parse a string and return an integer.
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Tests to see if there are any duplicate numbers in the current row/column
	 * 
	 * @param testArray
	 *            an array that contains the numbers for the current row/column
	 *            being tested
	 * @return True if the row/column contains duplicates, False otherwise
	 */
	private static boolean hasDuplicates(int[] testArray) {
		for (int i = 0; i < testArray.length; i++) {
			for (int j = i + 1; j < testArray.length; j++) {
				if (testArray[i] == testArray[j] && testArray[i] != 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks the current row for duplicates
	 * 
	 * @param grid
	 *            the initial sudoku puzzle
	 * @param currentRow
	 *            the row being tested
	 * @return True for duplicates, False otherwise
	 */
	private static boolean checkRow(int[][] grid, int currentRow) {
		int[] testArray = new int[grid.length];
		for (int colPosition = 0; colPosition < testArray.length; colPosition++) {
			testArray[colPosition] = grid[currentRow][colPosition];
		}
		if (hasDuplicates(testArray)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks the current column for duplicates
	 * 
	 * @param grid
	 *            the initial sudoku puzzle
	 * @param currentCol
	 *            the column being tested
	 * @return True for duplicates, False otherwise
	 */
	private static boolean checkCol(int[][] grid, int currentCol) {
		int[] testArray = new int[grid.length];
		for (int rowPosition = 0; rowPosition < testArray.length; rowPosition++) {
			testArray[rowPosition] = grid[rowPosition][currentCol];
		}
		if (hasDuplicates(testArray)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks current rectangle for duplicates
	 * 
	 * @return
	 */
	private static boolean checkRectangle(Solver s, int[][] grid, int currentRow, int currentCol) {
		int[] testArray = new int[grid.length];
		int rowCount, colCount;
		int cRow, dRow, cCol, dCol, n, t; // use more descriptive vars, will
											// update later
		n = currentRow / s.getW(); // w is global var for width
		t = currentCol / s.getH(); // h is global var for height, these are both
									// supposed to be integer division,
									// potentially losing data. that is on
									// purpose
		cRow = n * s.getW() + 1;
		int i = 0;
		if (cRow > currentRow) {
			dRow = currentRow;
			cRow = currentRow - s.getW() + 1;
		} else
			dRow = (n * s.getW()) + s.getW();
		cCol = t * s.getH() + 1;
		if (dRow > currentCol) {
			dCol = currentCol;
			cCol = currentCol - s.getH() + 1;
		} else
			dCol = (t * s.getH()) + s.getH();
		// After this, cRow gives us the low end of the row of the box to be
		// checking, dRow is high
		// Same for columns. cCol is low, dCol is high. Now just need for loop
		// to check every cell
		// in this "rectangle" or "box" for a duplicate number
		// Do this by creating a test array like in previous checks, but nested
		// for loops to run through
		// rows and columns
		for (int rowPosition = cRow; rowPosition <= dRow; rowPosition++) {
			for (int colPosition = cCol; colPosition <= dCol; colPosition++) {
				testArray[i] = grid[rowPosition][colPosition];
				++i;
			}
		}
		if (hasDuplicates(testArray)) {
			return true;
		}

		return false;
	}

	/**
	 * Checks all of the rows at once to see if there are any containing
	 * duplicate numbers.
	 * 
	 * @param s
	 *            the Solver with puzzle parameters
	 * @param grid
	 *            the puzzle grid containing the numbers
	 * @return A string that informs the user whether or not there are
	 *         duplicates in any of the rows.
	 */
	private static String checkAllRows(Solver s, int[][] grid) {
		String rowsWithDupes = "The following row(s) has/have duplicates: ";
		int numOfDupes = 0;

		for (int currentRow = 0; currentRow < grid.length; currentRow++) {
			if (checkRow(grid, currentRow)) {
				rowsWithDupes += "Row " + (currentRow + 1) + " ";
				numOfDupes++;
			}
			if (currentRow == grid.length - 1 && numOfDupes == 0) {
				rowsWithDupes = "There are no duplicates within the rows.\n";
			}
		}

		return rowsWithDupes;
	}

	/**
	 * Checks all of the columns at once to see if there are any containing
	 * duplicate numbers.
	 * 
	 * @param s
	 *            the Solver with puzzle parameters
	 * @param grid
	 *            the puzzle grid containing the numbers
	 * @return A string the informs the user whether or not there are duplicates
	 *         in any of the columns.
	 */
	private static String checkAllCols(Solver s, int[][] grid) {
		String colsWithDupes = "The following column(s) has/have duplicates: ";
		int numOfDupes = 0;

		for (int currentCol = 0; currentCol < grid.length; currentCol++) {
			if (checkCol(grid, currentCol)) {
				colsWithDupes += "Column " + (currentCol + 1) + " ";
				numOfDupes++;
			}
			if (currentCol == grid.length - 1 && numOfDupes == 0) {
				colsWithDupes = "There are no duplicates within the columns.\n";
			}
		}

		return colsWithDupes;
	}

	/*
	 * Getters
	 */

	public String getFilename() {
		return filename;
	}

	public String getComment() {
		return comment;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int[][] getGrid() {
		return grid;
	}

	/*
	 * Setters
	 */
	public void setFilename(String newFilename) {
		filename = newFilename;
	}

	public void setComment(String newComment) {
		comment = newComment;
	}

	public void setW(int newW) {
		w = newW;
	}

	public void setH(int newH) {
		h = newH;
	}

	public void setGrid(int[][] newGrid) {
		grid = newGrid;
	}
}
