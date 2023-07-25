import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String[] lines = input.readLine().split(" ");
		int numRows = Integer.parseInt(lines[0]);
		int numCols = Integer.parseInt(lines[1]);
		int numTestCases = Integer.parseInt(lines[2]);
		char[][] matrix = new char[numRows][numCols];
		int holeRow = 0;
		int holeCol = 0;
		for (int i = 0; i < numRows; i++) {
			String line = input.readLine();
			for (int j = 0; j < numCols; j++) {
				matrix[i][j] = line.charAt(j);
				if (matrix[i][j] == 'H') {
					holeRow = i;
					holeCol = j;
				}
			}
		}
		for (int i = 0; i < numTestCases; i++) {
			lines = input.readLine().split(" ");
			int startRow = Integer.parseInt(lines[0]) - 1;
			int startCol = Integer.parseInt(lines[1]) - 1;
			int result = bfs(matrix, startRow, startCol, holeRow, holeCol);
			if (result == -1)
				System.out.println("Stuck");
			else
				System.out.println(result);
		}
	}

	public static int bfs(char[][] matrix, int startRow, int startColumn, int holeRow, int holeColumn) {
		int rows = matrix.length;
		int columns = matrix[0].length;
		final int[] dx = { -1, 1, 0, 0 }; // direcoes x // raciocinio do exercicio de leetcode do maze
		final int[] dy = { 0, 0, 1, -1 }; // direcoes y // raciocinio do exercicio de leetcode do maze
		Queue<int[]> queue = new LinkedList<>();
		Set<Integer> visited2 = new HashSet<>(); // O(1) complexity for adding and checking
		visited2.add(startRow * columns + startColumn);
		queue.add(new int[] { startRow, startColumn, 0 });
		while (!queue.isEmpty()) {
			int[] current = queue.remove();
			int row = current[0];
			int col = current[1];
			int moves = current[2];
			if (row == holeRow && col == holeColumn) // its all indexes
				return moves;
			for (int i = 0; i < 4; i++) {
				int newRow = row + dx[i]; // arranjar uma forma de fazer isto mais inteligente
				int newCol = col + dy[i]; // raciocinio do exercicio de leetcode do maze
				while (!checkOutOfBounds(newRow, newCol, matrix)) {
					if (matrix[newRow][newCol] == 'H')
						return moves + 1;
					if (matrix[newRow][newCol] == 'O') { 
						if (!visited2.contains(((newRow - dx[i]) * columns) + (newCol - dy[i]))) {
							queue.add(new int[] { newRow - dx[i], newCol - dy[i], moves + 1 });
							visited2.add(((newRow - dx[i]) * columns) + (newCol - dy[i]));
						}
						break;
					}
					newRow = newRow + dx[i]; // direcoes x // raciocinio do exercicio de leetcode do maze
					newCol = newCol + dy[i]; // direcoes y // raciocinio do exercicio de leetcode do maze
				}
			}
		}
		return -1;
	}
		private static boolean checkOutOfBounds(int newRow, int newCol, char[][] matrix) {
		return ((newRow < 0) || (newRow >= matrix.length)) || ((newCol < 0) || (newCol >= matrix[0].length));
	}
}
