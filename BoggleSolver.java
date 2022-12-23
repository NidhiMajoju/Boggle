import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.*;
public class BoggleSolver {
	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	HashSet<String> dictionary;
	HashSet<String> words;

	public BoggleSolver(String dictionaryName) {
		//TODO
		try
		{
			Scanner scan = new Scanner(new File(dictionaryName));
			words = new HashSet<>();
			dictionary = new HashSet<>();
			while (scan.hasNextLine())
				dictionary.add(scan.nextLine());
		}
		catch (FileNotFoundException e)
		{
			System.out.println ("File not Found");
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		//TODO
		boolean[][] status = new boolean[board.rows()][board.cols()];

		for (int r = 0; r<board.rows(); r++)
		{
			for (int c = 0; c< board.cols(); c++)
			{
				status [r][c] = true;
				getNeighbors(board, "" + board.getLetter(r,c), status, r, c);
				status [r][c] = false;
			}
		}
		return words;

	}

	private void getNeighbors(BoggleBoard board, String word, boolean[][] status, int r, int c)
	{
		if (board.getLetter(r,c) == 'Q')
		{
			word += "U";
		}
		// conditional to add to words
		if (dictionary.contains(word))
		{
			words.add(word);
			dictionary.remove(word);
		}
		//
		//recursive backtracking here
		if (r+1<board.rows() && !status[r+1][c])
		{
			String str = word +  "" + board.getLetter(r+1, c) ;
			boolean [][] newStatus =  setStatus(status, r+1, c);
			getNeighbors(board, str, newStatus,r+1, c);
		}
		if (r-1>=0 && !status[r-1][c])
		{
			String str = word + "" + board.getLetter(r-1, c);
			boolean [][] newStatus = setStatus(status, r-1, c);
			getNeighbors(board, str, newStatus ,r-1, c);
		}
		if (c+1<board.cols() && !status[r][c+1])
		{
			String str = word + "" + board.getLetter(r, c+1);
			boolean [][] newStatus = setStatus(status, r, c+1);
			getNeighbors(board, str, newStatus,r, c+1);
		}
		if (c-1>=0 && !status[r][c-1])
		{
			String str = word + "" + board.getLetter(r, c-1);
			boolean [][] newStatus = setStatus(status, r, c-1);
			getNeighbors(board, str, newStatus,r, c-1);
		}
		if (r+1 < board.rows() && c+1 < board.cols() && !status[r+1][c+1])
		{
			String str = word +  "" + board.getLetter(r+1, c+1);
			boolean [][] newStatus = setStatus(status, r+1, c+1);
			getNeighbors(board, str, newStatus,r+1, c+1);
		}
		if (r-1 >= 0  && c-1 >= 0 && !status[r-1][c-1])
		{
			String str = word + board.getLetter(r-1, c-1);
			boolean [][] newStatus = setStatus(status, r-1, c-1);
			getNeighbors(board, str, newStatus ,r-1, c-1);
		}
		if (r+1 < board.rows()  && c-1 >= 0 && !status[r+1][c-1])
		{
			String str = word + "" + board.getLetter(r+1, c-1);
			boolean [][] newStatus = setStatus(status, r+1, c-1);
			getNeighbors(board, str, newStatus,r+1, c-1);
		}
		if (r-1 >= 0 && c+1 < board.cols() && !status[r-1][c+1])
		{
			String str = word + "" + board.getLetter(r-1, c+1);
			boolean [][] newStatus = setStatus(status, r-1, c+1);
			getNeighbors(board, str, newStatus ,r-1, c+1);
		}
	}

	private boolean[][] setStatus(boolean[][] status, int row, int col)
	{
		boolean[][] newStatus = new boolean [status.length][status[0].length];
		for (int r = 0; r< status.length; r++)
			for (int c = 0; c<status[0].length; c++)
			{
				if (row == r && col == c)
				{
					newStatus[r][c] = true;
					continue;
				}
				newStatus[r][c] = status[r][c];
			}
		return newStatus;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		//TODO
		int length = word.length();
		if (length<=2)
			return 0;
		if (length<=4)
			return 1;
		if (length == 5)
			return 2;
		if (length == 6)
			return 3;
		if (length == 7)
			return 5;
		return 11;
	}

	public static void main(String[] args) {
		System.out.println("WORKING");

		final String PATH   = "./data/";
		BoggleBoard  board  = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		//solver.printDictionary();
		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}
		//System.out.println(solver.getAllValidWords(board));
		System.out.println("Score = " + totalPoints); //should print 84

		//new BoggleGame(4, 4);
	}

}
