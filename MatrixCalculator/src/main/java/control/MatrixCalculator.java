package control;

import java.lang.reflect.Array;
import java.security.KeyStore.SecretKeyEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.management.Query;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;

import model.Matrix;

public class MatrixCalculator {
	private ArrayList<Matrix> listOfMatrix = new ArrayList<Matrix>();
	private List<String> keySet = new ArrayList<String>();

	private String[][] showWorkAddition;
	private String[][] showWorkSubtraction;

	private String[][] answerAddition;
	private String[][] answerSubtraction;

	private String[][] showWorkMultiplication;

	public void printData(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				System.out.print(data[i][j] = " ");
			}
			System.out.println(" ");
		}
	}

	public void printData(double[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				System.out.println(data[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public Matrix addMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		if (areSameDimensions(firstMatrix, secondMatrix)) {

			double[][] newData = new double[firstMatrix.getRow()][firstMatrix.getColumn()];
			String[][] showWork = new String[firstMatrix.getRow()][firstMatrix.getColumn()];
			String[][] answer = new String[firstMatrix.getRow()][firstMatrix.getColumn()];

			Matrix addedMatrix = new Matrix("" + firstMatrix.getName() + "+" + secondMatrix.getName(),
					firstMatrix.getRow(), firstMatrix.getColumn());
			for (int row = 0; row < firstMatrix.getCurrentMatrix().length; row++) {
				for (int col = 0; col < firstMatrix.getCurrentMatrix()[row].length; col++) {
					showWork[row][col] = "(" + firstMatrix.getCurrentMatrix()[row][col] + ")" + " + " + "("
							+ secondMatrix.getCurrentMatrix()[row][col] + ")";
					double add = firstMatrix.getCurrentMatrix()[row][col] + secondMatrix.getCurrentMatrix()[row][col];
					answer[row][col] = "(" + add + ")";
					newData[row][col] = add;
				}
			}

			showWorkAddition = showWork;
			answerAddition = answer;
			addedMatrix.setCurrentMatrix(newData);
			return addedMatrix;
		} else {
			System.out.println("Dimensions are not same...");
		}
		return null;
	}

	public static double[][] transpose(double[][] matrix) {
		double[][] transpose = new double[matrix[0].length][matrix.length];

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				transpose[j][i] = matrix[i][j];
		return transpose;
	}

	public static double[][] inverse(double[][] matrix) {
		double[][] inverse = new double[matrix.length][matrix.length];

		// minors and cofactors
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				inverse[i][j] = Math.pow(-1, i + j) * determinant(minor(matrix, i, j));
			}

		// adjugate and determinant
		double det = 1.0 / determinant(matrix);
		for (int i = 0; i < inverse.length; i++) {
			for (int j = 0; j <= i; j++) {
				double temp = inverse[i][j];
				inverse[i][j] = inverse[j][i] * det;
				inverse[j][i] = temp * det;
			}
		}

		return inverse;
	}

	public static double determinant(double[][] matrix) {
		if (matrix.length != matrix[0].length) {
			throw new IllegalStateException("Dimensions are invalid...");
		}

		if (matrix.length == 2) {
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		}
		double det = 0;
		for (int i = 0; i < matrix[0].length; i++) {
			det += Math.pow(-1, i) * matrix[0][i] * determinant(minor(matrix, 0, i));
		}
		return det;
	}

	private static double[][] minor(double[][] matrix, int row, int column) {
		double[][] minor = new double[matrix.length - 1][matrix.length - 1];

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; i != row && j < matrix[i].length; j++)
				if (j != column)
					minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
		return minor;
	}

	public static double[][] rref(double[][] matrix) {
		double[][] rrefArray = new double[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			rrefArray[i] = Arrays.copyOf(matrix[i], matrix[i].length);
		}

		int currentRow = 0;
		for (int currentColumn = 0; currentColumn < rrefArray[0].length
				&& currentRow < rrefArray.length; currentColumn++) {
			int j = currentRow;
			for (int i = currentRow + 1; i < rrefArray.length; i++)
				if (Math.abs(rrefArray[i][currentColumn]) > Math.abs(rrefArray[j][currentColumn]))
					j = i;
			if (Math.abs(rrefArray[j][currentColumn]) < 0.00001)
				continue;

			double[] temp = rrefArray[j];
			rrefArray[j] = rrefArray[currentRow];
			rrefArray[currentRow] = temp;

			double s = 1.0 / rrefArray[currentRow][currentColumn];
			for (j = 0; j < rrefArray[0].length; j++)
				rrefArray[currentRow][j] *= s;
			for (int i = 0; i < rrefArray.length; i++) {
				if (i != currentRow) {
					double t = rrefArray[i][currentColumn];
					for (j = 0; j < rrefArray[0].length; j++)
						rrefArray[i][j] -= t * rrefArray[currentRow][j];
				}
			}
			currentRow++;
		}

		return rrefArray;
	}

	public Matrix subtractMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		if (areSameDimensions(firstMatrix, secondMatrix)) {

			double[][] newData = new double[firstMatrix.getRow()][firstMatrix.getColumn()];
			String[][] showWork = new String[firstMatrix.getRow()][firstMatrix.getColumn()];
			String[][] answer = new String[firstMatrix.getRow()][firstMatrix.getColumn()];

			Matrix subMatrix = new Matrix("" + firstMatrix.getName() + "-" + secondMatrix.getName(),
					firstMatrix.getRow(), firstMatrix.getColumn());
			for (int row = 0; row < firstMatrix.getCurrentMatrix().length; row++) {
				for (int col = 0; col < firstMatrix.getCurrentMatrix()[row].length; col++) {
					showWork[row][col] = "(" + firstMatrix.getCurrentMatrix()[row][col] + ")" + " - " + "("
							+ secondMatrix.getCurrentMatrix()[row][col] + ")";
					double sub = firstMatrix.getCurrentMatrix()[row][col] - secondMatrix.getCurrentMatrix()[row][col];
					answer[row][col] = "(" + sub + ")";
					newData[row][col] = sub;

				}
			}

			showWorkSubtraction = showWork;
			answerSubtraction = answer;
			subMatrix.setCurrentMatrix(newData);
			return subMatrix;
		} else {
			System.out.println("Dimensions are not same...");
		}
		return null;
	}

	public Matrix multipyMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		int size = firstMatrix.getRow() * secondMatrix.getColumn();
		String[] eachValue = new String[size];
		String[][] showWork = new String[firstMatrix.getRow()][secondMatrix.getColumn()];

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
			int indexPosition = 0;
			String showWorkValue = "";
			while (operationCount != row) {
				for (int iterateSecondCol = 1; iterateSecondCol <= secondMatrix.getColumn(); iterateSecondCol++) {
					for (int i = 0; i < secondMatrix.getRow(); i++) {

						double firstValue = firstMatrix.getCurrentMatrix()[firstRow][firstColumn];
						double secondValue = secondMatrix.getCurrentMatrix()[secondRow][secondColumn];

						double product = firstValue * secondValue;

						showWorkValue += "(" + firstValue + ")(" + secondValue + ")" + "+";
						positionValue += product;
						firstColumn++;
						secondRow++;
					}
					showWorkValue = showWorkValue.trim();
					showWorkValue = showWorkValue.substring(0, showWorkValue.length()-1);
					eachValue[indexPosition] = showWorkValue;
					System.out.println(" ");
					showWorkValue = "";
					indexPosition++;
					firstColumn = 0;
					secondRow = 0;

					rowValue += positionValue + " ";

					if (iterateSecondCol == secondMatrix.getColumn()) {
						rowValue = rowValue.trim();
						listOfRows.add(rowValue);
					}
					positionValue = 0;

					secondColumn = iterateSecondCol;

				}

				operationCount++;
				rowValue = "";
				firstRow = operationCount;
				secondRow = 0;
				secondColumn = 0;
			}

			int count = 0;
			for (int i = 0; i < firstMatrix.getRow(); i++) {
				for (int j = 0; j < secondMatrix.getColumn(); j++) {
					showWork[i][j] = eachValue[count];
					System.out.print(showWork[i][j] + " ");
					count++;
				}
				System.out.println(" ");
			}

		}

		formatData(listOfRows);

		double[][] newResult = new double[tempMatrix.getRow()][tempMatrix.getColumn()];

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
		showWorkMultiplication = showWork;
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

	public void setCurrentDataMatrix_List(String name, double[][] currentData, int row, int column) {

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

	public String[][] getShowWorkAddition() {
		return showWorkAddition;
	}

	public String[][] getShowWorkSubtraction() {
		return showWorkSubtraction;
	}

	public String[][] getAnswerAddition() {
		return answerAddition;
	}

	public String[][] getAnswerSubtraction() {
		return answerSubtraction;
	}

	public String[][] getShowWorkMultiplication() {
		return showWorkMultiplication;
	}

}
