
package model;

public class Matrix {
	private String name = null;
	private Integer row = 0;
	private Integer column = 0;

	int[][] currentMatrix = new int[0][0];

	public Matrix(String name, Integer row, Integer column) {
		this.name = name;
		this.row = row;
		this.column = column;
		createDefaultMatrix();
	}

	private void createDefaultMatrix() {
		if (!(row == 0 || column == 0)) {
			currentMatrix = new int[row][column];
			for (int row = 0; row < currentMatrix.length; row++) {
				for (int col = 0; col < currentMatrix[row].length; col++) {
					currentMatrix[row][col] = 0;
				}
			}
		}
	}

	public Matrix() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public int[][] getCurrentMatrix() {
		return currentMatrix;
	}

	public void setCurrentMatrix(int[][] currentMatrix) {
		this.currentMatrix = currentMatrix;
		// printMatrix();
	}

	@Override
	public String toString() {
		return "Matrix: [" + name + "]: " + row + "X" + column + " " + currentMatrix;
	}

	public void printMatrix() {
		System.out.println("\nPrint: [" + name + "] " + row + "X" + column + "\n");
		for (int row = 0; row < this.currentMatrix.length; row++) {
			for (int col = 0; col < this.currentMatrix[row].length; col++) {
				System.out.print(currentMatrix[row][col]);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}

}
