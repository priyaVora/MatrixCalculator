package control;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.management.Query;

import model.Matrix;

public class MatrixCalculator {
	private ArrayList<Matrix> listOfMatrix = new ArrayList<Matrix>();
	private List<String> keySet = new ArrayList<String>();

	public Matrix addMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		if (areSameDimensions(firstMatrix, secondMatrix)) {

			int[][] newData = new int[firstMatrix.getRow()][firstMatrix.getColumn()];
			Matrix addedMatrix = new Matrix("" + firstMatrix.getName() + "+" + secondMatrix.getName(),
					firstMatrix.getRow(), firstMatrix.getColumn());
			for (int row = 0; row < firstMatrix.getCurrentMatrix().length; row++) {
				for (int col = 0; col < firstMatrix.getCurrentMatrix()[row].length; col++) {
					int add = firstMatrix.getCurrentMatrix()[row][col] + secondMatrix.getCurrentMatrix()[row][col];
					newData[row][col] = add;
				}
			}

			addedMatrix.setCurrentMatrix(newData);
			return addedMatrix;
		} else {
			System.out.println("Dimensions are not same...");
		}
		return null;
	}

	public Matrix subtractMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		if (areSameDimensions(firstMatrix, secondMatrix)) {

			int[][] newData = new int[firstMatrix.getRow()][firstMatrix.getColumn()];
			Matrix subMatrix = new Matrix("" + firstMatrix.getName() + "-" + secondMatrix.getName(),
					firstMatrix.getRow(), firstMatrix.getColumn());
			for (int row = 0; row < firstMatrix.getCurrentMatrix().length; row++) {
				for (int col = 0; col < firstMatrix.getCurrentMatrix()[row].length; col++) {
					int sub = firstMatrix.getCurrentMatrix()[row][col] - secondMatrix.getCurrentMatrix()[row][col];
					newData[row][col] = sub;
				}
			}

			subMatrix.setCurrentMatrix(newData);
			return subMatrix;
		} else {
			System.out.println("Dimensions are not same...");
		}
		return null;
	}

	public Matrix multipyMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		int operationCount = 0;
		List<String> listOfRows = new ArrayList<String>();

		Matrix tempMatrix = getNewMatrixSize(firstMatrix, secondMatrix);
		Matrix resultMatrix = new Matrix();

		if (tempMatrix != null) {
			int row = tempMatrix.getRow();
			int column = tempMatrix.getColumn();

			resultMatrix.setRow(row);
			resultMatrix.setColumn(column);
			resultMatrix.setName(firstMatrix.getName() + "*" + secondMatrix.getName());

			int firstRow = 0;
			int firstColumn = 0;
			int secondRow = 0;
			int secondColumn = 0;

			int positionValue = 0;
			String rowValue = "";
			while (operationCount != row) {
				for (int iterateSecondCol = 1; iterateSecondCol <= secondMatrix.getColumn(); iterateSecondCol++) {
					for (int i = 0; i < secondMatrix.getRow(); i++) {
						// System.out.println("\nFIRST");
						// System.out.print(firstRow + ",");
						// System.out.print(firstColumn);
						// System.out.println(" ");
						// System.out.println(" ");
						// System.out.println("SECOND");
						// System.out.print(secondRow + ",");
						// System.out.print(secondColumn);
						// System.out.println(" ");

						int firstValue = firstMatrix.getCurrentMatrix()[firstRow][firstColumn];
						int secondValue = secondMatrix.getCurrentMatrix()[secondRow][secondColumn];

						// System.out.println("===" + firstValue);
						// System.out.println("===" + secondValue);

						int product = firstValue * secondValue;
						positionValue += product;
						firstColumn++;
						secondRow++;
					}

					firstColumn = 0;
					secondRow = 0;

					rowValue += positionValue + " ";

					// System.out.println("\tPosition: " + positionValue);

					if (iterateSecondCol == secondMatrix.getColumn()) {
						rowValue = rowValue.trim();
						listOfRows.add(rowValue);
					}
					positionValue = 0;
					// System.out.println("---------------------------------------------------");

					secondColumn = iterateSecondCol;
				}
				operationCount++;
				rowValue = "";
				firstRow = operationCount;
				secondRow = 0;
				secondColumn = 0;
			}

		}

		formatData(listOfRows);

		int[][] newResult = new int[tempMatrix.getRow()][tempMatrix.getColumn()];

		int currentRow = 0;
		int currentColumn = 0;
		List<String[]> formatedList = formatData(listOfRows);
		for (String[] dataArray : formatedList) {
			for (String string : dataArray) {
				newResult[currentRow][currentColumn] = Integer.parseInt(string);
				currentColumn++;
			}
			currentRow++;
			currentColumn = 0;
		}
		resultMatrix.setCurrentMatrix(newResult);
		resultMatrix.printMatrix();
		return resultMatrix;
	}

	private List<String[]> formatData(List<String> listOfRows) {
		if (listOfRows == null) {
			return null;
		}
		int count = 0;
		List<String[]> formatedList = new ArrayList<String[]>();

		for (String eachRow : listOfRows) {
			count = 0;
			String[] eachValue = eachRow.split(" ");
			String[] data = new String[eachValue.length];
			for (String value : eachValue) {
				// System.out.println(value);
				data[count] = value;
				count++;
			}
			formatedList.add(data);
		}

		return formatedList;
	}

	public Matrix getNewMatrixSize(Matrix firstMatrix, Matrix secondMatrix) {
		Matrix resultMatrix = null;
		int firstMatrixRowCount = firstMatrix.getRow();
		int firstMatrixColumnCount = firstMatrix.getColumn();

		int secondMatrixRowCount = secondMatrix.getRow();
		int secondMatrixColumnCount = secondMatrix.getColumn();

		if (firstMatrixColumnCount == secondMatrixRowCount) {
			resultMatrix = new Matrix();
			resultMatrix.setRow(firstMatrixRowCount);
			resultMatrix.setColumn(secondMatrixColumnCount);
			// System.out.println("Matched!");
		} else {
			System.out.println("Did not matched");
		}

		return resultMatrix;
	}

	public boolean areSameMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		if (firstMatrix == null || secondMatrix == null) {
			return false;
		}
		for (int row = 0; row < firstMatrix.getCurrentMatrix().length; row++) {
			for (int col = 0; col < firstMatrix.getCurrentMatrix()[row].length; col++) {
				if (firstMatrix.getCurrentMatrix()[row][col] == secondMatrix.getCurrentMatrix()[row][col]) {

				} else {
					return false;
				}
			}
		}
		return true;
	}

	public boolean areSameDimensions(Matrix firstMatrix, Matrix secondMatrix) {
		int row1 = firstMatrix.getRow();
		int column1 = firstMatrix.getColumn();

		int row2 = secondMatrix.getRow();
		int column2 = secondMatrix.getColumn();

		if (row1 == row2 && column1 == column2) {
			return true;
		}

		return false;
	}

	public void addMatrix(Matrix matrix) {
		if (!keySet.contains(matrix.getName())) {
			keySet.add(matrix.getName());
			listOfMatrix.add(matrix);
		}
	}

	public void setCurrentDataMatrix_List(String name, int[][] currentData, int row, int column) {

		int index = 0;
		loop: for (Matrix eachMatrix : listOfMatrix) {
			if (eachMatrix.getName().equals(name)) {
				eachMatrix.setCurrentMatrix(currentData);
				eachMatrix.setRow(row);
				eachMatrix.setColumn(column);
				break loop;
			}
			index++;
		}
	}

	public void deleteMatrix(Matrix matrix) {

		keySet.remove(matrix.getName());
		int index = 0;
		loop: for (Matrix eachMatrix : listOfMatrix) {
			if (eachMatrix.getName().equals(matrix.getName())) {
				break loop;
			}
			index++;
		}
		listOfMatrix.remove(index);

	}

	public void deleteMatrix(String matrix) {
		Matrix temp = new Matrix();
		loop: for (Matrix eachMatrix : listOfMatrix) {
			if (eachMatrix.getName().equals(matrix)) {
				temp.setName(eachMatrix.getName());
				temp.setRow(eachMatrix.getRow());
				temp.setColumn(eachMatrix.getColumn());
				temp.setCurrentMatrix(eachMatrix.getCurrentMatrix());
				break loop;
			}
		}
		if (keySet.contains(temp.getName())) {
			keySet.remove(temp.getName());
			// listOfMatrix.remove(temp);
			deleteMatrix(temp);
		}
	}

	public void showCurrentMatrices() {
		for (Matrix eachMatrix : listOfMatrix) {
			eachMatrix.printMatrix();
		}
	}

	public void changeMatrixData(String name, Matrix matrix) {
		int index = 0;
		loop: for (Matrix eachMatrix : listOfMatrix) {
			if (eachMatrix.getName().equals(matrix.getName())) {
				eachMatrix.setRow(matrix.getRow());
				eachMatrix.setColumn(matrix.getColumn());
				deleteMatrix(eachMatrix);
				break loop;
			}
			index++;
		}

		listOfMatrix.add(index, matrix);
	}

	public void changeMatrixName(String name, String newName) {
		int index = 0;
		loop: for (Matrix eachMatrix : listOfMatrix) {
			if (eachMatrix.getName().equals(name)) {
				eachMatrix.setName(newName);
				break loop;
			}
			index++;
		}
	}

	public boolean containsMatrix(Matrix matrixName) {
		if (listOfMatrix.isEmpty() || matrixName == null) {
			return false;
		}
		for (Matrix matrix : listOfMatrix) {
			if (matrix.getName().equals(matrixName.getName())) { // names match
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public ArrayList<Matrix> getListOfMatrices() {
		return listOfMatrix;
	}

	public List<String> getKeySet() {
		return keySet;
	}

}
