package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private String[][] answerMultiplication;

	public static void main(String args[]) {

	}

	public void grabsContentFromFile(String file1, String file2) {
		FileReader fr = null;
		FileWriter fw = null;
		try {
			fr = new FileReader(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\" + file1);
			fw = new FileWriter(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\+" + file2);
			int c = fr.read();
			while (c != -1) {
				fw.write(c);
				c = fr.read();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(fr);
			close(fw);
		}
	}

	public static void close(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			// ...
		}
	}

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

	public double[][] inverse(double[][] matrix) throws IOException {

		ArrayList<double[][]> list = new ArrayList<double[][]>();
		ArrayList<Double> valueList = new ArrayList<Double>();
		ArrayList<Double> secretSigns = new ArrayList<Double>();

		int count = 0;
		double[][] inverse = new double[matrix.length][matrix.length];
		boolean folder = new File(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork").mkdirs();

		BufferedReader reader = new BufferedReader(new FileReader(
				new File(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\+InverseMatrix.txt")));
		FileWriter fw = new FileWriter(
				System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\+InverseMatrix.txt", true);
		BufferedWriter writer = new BufferedWriter(fw);
		// minors and cofactors
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				double firstSide = Math.pow(-1, i + j);
				double[][] currentMinor = minor(matrix, i, j);
				list.add(count, currentMinor);

				double secondSide = determinant(currentMinor);
				valueList.add(secondSide);

				double secretValue = Math.pow(-1, i + j) * determinant(minor(matrix, i, j));
				secretSigns.add(count, secretValue);
				inverse[i][j] = secretValue;
				count++;
			}

		// adjugate and determinant
		double det = 1.0 / determinant(matrix);

		grabsContentFromFile("Determinant.txt", "InverseMatrix.txt");

		writer.write(System.getProperty("line.separator"));
		writer.write("----------------------------------------------------------------------");
		writer.newLine();

		if (Double.isInfinite(det) == false) {
			for (double[][] eachMinor : list) {

				for (int i = 0; i < eachMinor.length; i++) {
					for (int j = 0; j < eachMinor[0].length; j++) {
						writer.write(" " + eachMinor[i][j] + " ");
					}

					writer.newLine();

				}

				writer.write("------");
				writer.newLine();

				writer.newLine();
			}

			writer.newLine();

			writer.write("----------------------------------------------------------------------");

			writer.newLine();
			writer.write("Before Secret Sign is Applied");
			writer.newLine();

			int countValues = 0;
			double[][] v = new double[matrix.length][matrix[0].length];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					v[i][j] = valueList.get(countValues);
					writer.write(" " + v[i][j] + " ");
					countValues++;
				}
				writer.newLine();
			}

			writer.newLine();
			writer.write("After Secret Sign is Applied");
			writer.newLine();

			int countValues2 = 0;
			double[][] v2 = new double[matrix.length][matrix[0].length];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					v2[i][j] = secretSigns.get(countValues2);
					writer.write(" " + v2[i][j] + " ");
					countValues2++;
				}
				writer.newLine();
			}

			writer.newLine();
			writer.write("Tranpose");
			writer.newLine();
			double[][] t = transpose(v2);
			for (int i = 0; i < t.length; i++) {
				for (int j = 0; j < t[0].length; j++) {
					writer.write(" " + t[i][j] + " ");
				}
				writer.newLine();
			}

		} else {

		}
		writer.newLine();
		if (Double.isInfinite(det) == true) {
			writer.write("Determinant is: " + det);
		} else {
			writer.write("Multiplied 1/det : " + changeValueToProperForm(det) + " to Tranposed Matrix: ");
		}
		if (Double.isInfinite(det) == false) {
			for (int i = 0; i < inverse.length; i++) {
				for (int j = 0; j <= i; j++) {
					double temp = inverse[i][j];
					double Nan = inverse[j][i] * det;
					System.out.println("Double: " + Nan);
					inverse[i][j] = inverse[j][i] * det;
					inverse[j][i] = temp * det;
				}
			}
			writer.newLine();
			writer.write(" Inverse Matrix");
			writer.newLine();
			for (int i = 0; i < inverse.length; i++) {
				for (int j = 0; j < inverse[0].length; j++) {
					writer.write(" " + changeValueToProperForm(inverse[i][j]) + " ");
				}
				writer.newLine();
			}
		} else {
			writer.newLine();
			writer.write(" Inverse Matrix: ");
			writer.newLine();
			writer.write("     INVERSE DOESN'T EXIST");
			writer.close();
			return null;
		}

		writer.close();
		return inverse;
	}

	public double determinant(double[][] matrix) throws FileNotFoundException {
		String fileText = "";

		String determinantfinalShow = "";
		boolean folder = new File(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork").mkdirs();
		PrintWriter writer = new PrintWriter(
				System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\Determinant.txt");

		if (matrix.length == 0) {
			System.out.println("Determinant is Zero");
			return 0;
		}

		int signCount = 0;
		if (matrix.length != matrix[0].length) {
			throw new IllegalStateException("Dimensions are invalid...");
		}

		if (matrix.length == 2) {
			fileText += "Determinant 2 X 2 Matrix: ";
			fileText += System.getProperty("line.separator");
			fileText += System.getProperty("line.separator");
			fileText += "(" + matrix[0][0] + ")" + "*(" + matrix[1][1] + ") - (" + matrix[0][1] + ")" + "*" + "("
					+ matrix[1][0] + ")";
			fileText += System.getProperty("line.separator");
			fileText += System.getProperty("line.separator");
			fileText += "  ";
			fileText += matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
			fileText += System.getProperty("line.separator");
			writer.println(fileText);
			writer.close();
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		}
		double det = 0;
		for (int i = 0; i < matrix[0].length; i++) {
			double[][] minorCalculated = minor(matrix, 0, i);
			System.out.println("Matrix Coefficient: " + matrix[0][i]);
			System.out.println(fileText);
			writer.println("Matrix Coefficient: " + matrix[0][i]);
			fileText += System.getProperty("line.separator");
			for (int j = 0; j < minorCalculated.length; j++) {
				for (int j2 = 0; j2 < minorCalculated[0].length; j2++) {
					System.out.print(" " + minorCalculated[j][j2] + " ");
					writer.write(" " + minorCalculated[j][j2] + " ");
				}
				System.out.println(" ");
				writer.println(" ");
			}

			det += Math.pow(-1, i) * matrix[0][i] * determinant(minorCalculated);

			determinantfinalShow += "(" + matrix[0][i] + ")" + "*(" + determinant(minorCalculated) + ")";
			if (signCount % 2 == 0) {
				determinantfinalShow += "-";
			} else {
				determinantfinalShow += "+";
			}
			signCount++;

		}
		System.out.println("\nDETERMINANT PROCESS: ");
		writer.println();
		writer.println("\nDETERMINANT PROCESS: ");
		determinantfinalShow = determinantfinalShow.substring(0, determinantfinalShow.length() - 1);
		System.out.println(determinantfinalShow);
		writer.println();
		writer.println(determinantfinalShow);
		writer.println("Determinant Value: " + det);
		System.out.println("Determinant Value: " + det);
		if (det == 0) {
			writer.println();
			writer.println("DETERMINANT IS ZERO: THIS MEANS THAT THERE IS NO INVERSE.");
			writer.println(
					"HOWEVER THERE CAN BE INFINITE OR NO SOLUTIONS FOR THIS MATRIX. USE ROW OPERATIONS TO SOLVE THE SYSTEM.");

		}
		writer.close();
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

	public String doubleToFraction(double doubleVal) {
		double negligibleRatio = 0.01;
		String val = "";

		for (int i = 1;; i++) {
			double tem = doubleVal / (1D / i);
			if (Math.abs(tem - Math.round(tem)) < negligibleRatio) {

				val = Math.round(tem) + "/" + i;

				break;

			}
		}
		return val;
	}

	public String changeValueToProperForm(double number) {
		double calculate = number - Math.floor(number);

		if (calculate == 0) {
			// System.out.println(number + " is a whole number");
			return number + "";
		} else {
			// System.out.println(" Number is not a whole number");
			// System.out.println(doubleToFraction(number));
			return doubleToFraction(number);
		}
	}

	public void printArray(String dataArray) {

	}

	public void printArrayProperForm(double[][] dataArray, PrintWriter writer) {
		for (int i = 0; i < dataArray.length; i++) {
			for (int j = 0; j < dataArray[i].length; j++) {
				System.out.print("   " + changeValueToProperForm(dataArray[i][j]) + " ");
				writer.print("   " + changeValueToProperForm(dataArray[i][j]) + " ");
			}
			System.out.println(" ");
			writer.println("");
		}
	}

	public double[][] rref(double[][] matrix) throws FileNotFoundException, UnsupportedEncodingException {
		// System.out.println(System.getProperty("user.home") + "\\Desktop");
		// File desktop = new File(System.getProperty("user.home") + "/Desktop");
		boolean folder = new File(System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork").mkdirs();
		PrintWriter writer = new PrintWriter(
				System.getProperty("user.home") + "/Desktop" + "\\MatrixShowWork" + "\\ShowWork.txt");
		// writer.println("The first line");
		// writer.println("The second line");
		// writer.close();

		double[][] rrefArray = new double[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			rrefArray[i] = Arrays.copyOf(matrix[i], matrix[i].length);
		}

		System.out.println("Original Matrix: ");
		writer.println("Original Matrix: \n");
		printArrayProperForm(rrefArray, writer);

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
			System.out.print("\nRow Focus: ");
			writer.println();
			writer.print("\n\nRow Focus: ");
			for (int i = 0; i < temp.length; i++) {
				System.out.print(changeValueToProperForm(temp[i]) + " ");
				writer.print(changeValueToProperForm(temp[i]) + " ");
			}
			System.out.println(" ");
			writer.println(" ");
			if ((j + 1) != (currentRow + 1)) {
				System.out.println("Row Swap: " + (j + 1) + " with " + (currentRow + 1));
				writer.println("Row Swap: " + (j + 1) + " with " + (currentRow + 1));
			}
			rrefArray[j] = rrefArray[currentRow];
			rrefArray[currentRow] = temp;

			double s = 1.0 / rrefArray[currentRow][currentColumn];
			System.out.println("Multiply: " + changeValueToProperForm(s));
			writer.println("Multiply: " + changeValueToProperForm(s));

			for (j = 0; j < rrefArray[0].length; j++) {
				rrefArray[currentRow][j] *= s;
			}

			System.out.println("\nMatrix Update: ");
			writer.println("");
			writer.println("\nMatrix Update: ");
			printArrayProperForm(rrefArray, writer);
			double subtractionValue = 0;

			for (int i = 0; i < rrefArray.length; i++) {
				if (i != currentRow) {
					double t = rrefArray[i][currentColumn];

					System.out.println("\nMulitply " + changeValueToProperForm(t) + " Pivot: R" + (currentRow + 1));
					writer.println("");
					writer.println("\nMulitply " + changeValueToProperForm(t) + " Pivot: R" + (currentRow + 1));
					String[] pivotMultiplication = new String[50];
					double[] pivotMultiplicationAnswer = new double[50];
					String[] subtraction = new String[50];
					double[] subtractionAnswer = new double[50];

					int index = 0;

					for (j = 0; j < rrefArray[0].length; j++) {

						subtractionValue = t * rrefArray[currentRow][j];
						// System.out.println("(" + changeValueToProperForm(t) + ")" + "*("
						// + changeValueToProperForm(rrefArray[currentRow][j]) + ")");

						pivotMultiplication[index] = "(" + changeValueToProperForm(t) + ")" + "*("
								+ changeValueToProperForm(rrefArray[currentRow][j]) + ")";
						pivotMultiplicationAnswer[index] = t * rrefArray[currentRow][j];
						subtraction[index] = "  " + changeValueToProperForm(rrefArray[i][j]) + "-" + "("
								+ changeValueToProperForm(subtractionValue) + ")";

						double value = rrefArray[i][j] - subtractionValue;
						subtractionAnswer[index] = value;

						rrefArray[i][j] -= subtractionValue;
						index++;
					}

					printShowWorkForRREF(pivotMultiplication, pivotMultiplicationAnswer, subtractionAnswer, subtraction,
							writer);

					System.out.println("\n\nOperation Type:  R" + (i + 1) + " = R" + (i + 1) + " minus " + "("
							+ changeValueToProperForm(t) + ")" + ("R" + (+(currentRow + 1))));
					writer.println("");
					writer.println("\n\nOperation Type:  R" + (i + 1) + " = R" + (i + 1) + " minus " + "("
							+ changeValueToProperForm(t) + ")" + ("R" + (+(currentRow + 1))));

					for (int i1 = 0; i1 < rrefArray.length; i1++) {
						for (int k = 0; k < rrefArray[0].length; k++) {
							System.out.print("   " + changeValueToProperForm(rrefArray[i1][k]) + " ");
							writer.print("   " + changeValueToProperForm(rrefArray[i1][k]) + " ");
						}
						System.out.println();
						writer.println("");
					}

				}
			}
			System.out.println("\nEnd: Matrix Update: ");
			writer.println("");
			writer.println("\nEnd: Matrix Update: ");
			printArrayProperForm(rrefArray, writer);
			currentRow++;
		}
		System.out.println(" ");
		double[][] resultAsInteger = new double[rrefArray.length][rrefArray[0].length];
		writer.println("");
		writer.println("Matrix Row Operation Answer:");
		for (int i = 0; i < rrefArray.length; i++) {
			for (int j = 0; j < rrefArray[0].length; j++) {
				// System.out.print(Math.round(rrefArray[i][j]) + " ");
				writer.print("  " + Math.round(rrefArray[i][j]) + "  ");
				resultAsInteger[i][j] = (int) (Math.round(rrefArray[i][j]));
			}
			// System.out.println(" ");
			writer.println("");
		}
		writer.close();
		return rrefArray;
	}

	public void printShowWorkForRREF(String[] pivotMultiplication, double[] pivotMultiplicationAnswer,
			double[] subtractionAnswer, String[] subtraction, PrintWriter writer) {
		System.out.println("\n\nProcess: ");
		writer.println();
		writer.println("\nProcess: ");
		int answerCount = 0;
		for (int k = 0; k < pivotMultiplication.length; k++) {
			if (pivotMultiplication[k] == null) {
				break;
			}
			System.out.print(pivotMultiplication[k] + " ");
			writer.print(pivotMultiplication[k] + " ");
			answerCount++;
		}

		System.out.println("\n\nChanged Row: ");
		writer.println();
		writer.println("\n\nChanged Row: ");
		for (int k = 0; k < pivotMultiplicationAnswer.length; k++) {
			if (k == answerCount) {
				break;
			}
			System.out.print(changeValueToProperForm(pivotMultiplicationAnswer[k]) + " ");
			writer.print(changeValueToProperForm(pivotMultiplicationAnswer[k]) + " ");
		}

		System.out.println("\n\nSubtraction Operation: ");
		writer.println();
		writer.println("\n\nSubtraction Operation: ");

		int operationCount = 0;
		for (int k = 0; k < subtraction.length; k++) {
			if (subtraction[k] == null) {
				break;
			}
			System.out.print(subtraction[k] + " ");
			writer.print(subtraction[k] + " ");
			operationCount++;
		}

		System.out.println("\n\nSubtraction Answer: ");
		writer.println();
		writer.println("\n\nSubtraction Answer: ");
		for (int k = 0; k < subtractionAnswer.length; k++) {
			if (k == operationCount) {
				break;
			}
			System.out.print(changeValueToProperForm(subtractionAnswer[k]) + " ");
			writer.print(subtractionAnswer[k] + " ");
		}
		writer.println("");
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
			System.out.println("Dimensions are not same in subtract...");
		}
		return null;
	}

	public Matrix multipyMatrices(Matrix firstMatrix, Matrix secondMatrix) {
		int size = firstMatrix.getRow() * secondMatrix.getColumn();
		answerMultiplication = new String[firstMatrix.getRow()][secondMatrix.getColumn()];
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
					showWorkValue = showWorkValue.substring(0, showWorkValue.length() - 1);
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
		for (int i = 0; i < newResult.length; i++) {
			for (int j = 0; j < newResult[i].length; j++) {
				answerMultiplication[i][j] = "" + newResult[i][j];
			}
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
			System.out.println("First Col Count: " + firstMatrixColumnCount);
			System.out.println("Second Row CountL " + secondMatrixRowCount);
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

	public String[][] getAnswerMultiplication() {
		return answerMultiplication;
	}

}
