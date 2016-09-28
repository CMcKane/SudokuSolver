
/**
 * Brute Force Sudoku Solver
 * 
 * Author: Rex Cummings
 * 
 * Date 9/25/2016
 */

public class ParseCmdLine
{
	public String getArgs(String[] args)
	{
		String fn = "";

		if (args.length > 0)
		{
			if (args.length > 1)
			{
				System.out.println("Too many arguments.");
				System.out.println("Usage: java program filename");
				System.exit(0);
			}
			else if (args[0].endsWith(".txt"))
			{
				fn = args[0];
				System.out.println(fn);
			}
			else
				System.out.println("Argument not a proper file.");
		}
		return fn;
	}
}
