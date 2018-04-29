package modelTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;

import control.MatrixCalculator;
import model.Matrix;

public class MatrixTest {

	@Test
	public void testMultiplication3by3_3by1() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 3);
		Matrix b = new Matrix("B", 3, 1);

		double[][] a_data = new double[3][3];
		a_data[0][0] = 3;
		a_data[0][1] = 3;
		a_data[0][2] = 3;

		a_data[1][0] = 43;
		a_data[1][1] = 234;
		a_data[1][2] = 234;

		a_data[2][0] = 234;
		a_data[2][1] = 234;
		a_data[2][2] = 234;

		a.setCurrentMatrix(a_data);

		double[][] b_data = new double[3][1];
		b_data[0][0] = 2;
		b_data[1][0] = 1;
		b_data[2][0] = 1;

		b.setCurrentMatrix(b_data);
		Matrix resultMatrix = new Matrix("A B", 3, 1);
		resultMatrix = cal.multipyMatrices(a, b);
		System.out.println("Print Multiplications: ");
		resultMatrix.printMatrix();

	}

	@Test
	public void testShowingWork_addition() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix b = new Matrix("B", 2, 2);

		Matrix expected = new Matrix("A+B", 2, 2);

		double[][] a_data = new double[2][2];
		double[][] b_data = new double[2][2];
		double[][] expectedData = new double[2][2];

		a_data[0][0] = 2;
		a_data[0][1] = 2;
		a_data[1][0] = 2;
		a_data[1][1] = 2;

		b_data[0][0] = 2;
		b_data[0][1] = 2;
		b_data[1][0] = 2;
		b_data[1][1] = 2;

		expectedData[0][0] = 4;
		expectedData[0][1] = 4;
		expectedData[1][0] = 4;
		expectedData[1][1] = 4;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.addMatrices(a, b);
		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testShowingWork_Inverse() throws IOException {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix b = new Matrix("B", 2, 2);

		Matrix expected = new Matrix("A B", 2, 2);

		double[][] a_data = new double[2][2];
		double[][] expectedData = new double[2][2];

		a_data[0][0] = 23;
		a_data[0][1] = 23;
		a_data[1][0] = 23;
		a_data[1][1] = 23;

		expectedData[0][0] = Double.NaN;
		expectedData[0][1] = Float.NaN;
		expectedData[1][0] = Float.NaN;
		expectedData[1][1] = Float.NaN;

		a.setCurrentMatrix(a_data);

		// expected.setCurrentMatrix(expectedData);

		System.out.println("/////////////");
		double[][] result = cal.inverse(a_data);
		System.out.println("/////////////");
		System.out.println("Is result null? - " + result.length);
		Matrix resultMatrix = new Matrix();
		resultMatrix.setName("A B");
		resultMatrix.setCurrentMatrix(result);
		resultMatrix.printMatrix();

		System.out.println("EXPECTED: ");
		for (int i = 0; i < expectedData.length; i++) {
			for (int j = 0; j < expectedData[0].length; j++) {
				System.out.print(expectedData[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println("Result: ");
		resultMatrix.printMatrix();

		assertArrayEquals(expectedData, result);
	}

	@Test
	public void inverse3by3_Second() throws IOException {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 3);
		Matrix b = new Matrix("B", 3, 3);

		Matrix expected = new Matrix("A+B", 3, 3);

		double[][] a_data = new double[3][3];
		double[][] expectedData = new double[3][3];

		a_data[0][0] = 3;
		a_data[0][1] = 3;
		a_data[0][2] = 3;

		a_data[1][0] = 43;
		a_data[1][1] = 234;
		a_data[1][2] = 234;

		a_data[2][0] = 234;
		a_data[2][1] = 234;
		a_data[2][2] = 234;

		expectedData[0][0] = -0.3333333333333333;
		expectedData[0][1] = 0.3333333333333333;
		expectedData[0][2] = 0.3333333333333333;
		expectedData[1][0] = -2.333333333333333;
		expectedData[1][1] = 0.3333333333333333;
		expectedData[1][2] = 1.3333333333333333;
		expectedData[2][0] = -3.6666666666666665;
		expectedData[2][1] = 0.6666666666666666;
		expectedData[2][2] = 1.6666666666666665;

		a.setCurrentMatrix(a_data);

		double[][] result = cal.inverse(a_data);

		Matrix resultMatrix = new Matrix();
		resultMatrix.setCurrentMatrix(result);
		resultMatrix.printMatrix();
	}

	@Test
	public void testShowingWork_Inverse3by3() throws IOException {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 3);
		Matrix b = new Matrix("B", 3, 3);

		Matrix expected = new Matrix("A+B", 3, 3);

		double[][] a_data = new double[3][3];
		double[][] expectedData = new double[3][3];

		a_data[0][0] = 1;
		a_data[0][1] = 1;
		a_data[0][2] = -1;

		a_data[1][0] = 3;
		a_data[1][1] = -2;
		a_data[1][2] = 1;

		a_data[2][0] = 1;
		a_data[2][1] = 3;
		a_data[2][2] = -2;

		expectedData[0][0] = -0.3333333333333333;
		expectedData[0][1] = 0.3333333333333333;
		expectedData[0][2] = 0.3333333333333333;
		expectedData[1][0] = -2.333333333333333;
		expectedData[1][1] = 0.3333333333333333;
		expectedData[1][2] = 1.3333333333333333;
		expectedData[2][0] = -3.6666666666666665;
		expectedData[2][1] = 0.6666666666666666;
		expectedData[2][2] = 1.6666666666666665;

		a.setCurrentMatrix(a_data);

		expected.setCurrentMatrix(expectedData);

		System.out.println("/////////////");
		double[][] result = cal.inverse(a_data);
		System.out.println("/////////////");
		System.out.println("Is result null? - " + result.length);
		Matrix resultMatrix = new Matrix();
		resultMatrix.setCurrentMatrix(result);
		resultMatrix.printMatrix();
		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}

	}

	@Test
	public void testDeterminant3by3() throws FileNotFoundException {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 3);
		Matrix expectedMatrix = new Matrix("A", 3, 3);
		double[][] a_data = new double[3][3];
		a_data[0][0] = 1;
		a_data[0][1] = 4;
		a_data[0][2] = -3;

		a_data[1][0] = 3;
		a_data[1][1] = -1;
		a_data[1][2] = 3;

		a_data[2][0] = 1;
		a_data[2][1] = 1;
		a_data[2][2] = 6;

		a.setCurrentMatrix(a_data);
		double determinant = cal.determinant(a.getCurrentMatrix());
		System.out.println("Determinant: " + determinant);

		// cal.determinant(a.getCurrentMatrix());
	}

	@Test
	public void testDeterminant() throws FileNotFoundException {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix expectedMatrix = new Matrix("A", 2, 2);
		double[][] a_data = new double[2][2];
		a_data[0][0] = 2;
		a_data[0][1] = 7;

		a_data[1][0] = 7;
		a_data[1][1] = 2;

		a.setCurrentMatrix(a_data);
		double determinant = cal.determinant(a.getCurrentMatrix());
		System.out.println("Determinant: " + determinant);

		// cal.determinant(a.getCurrentMatrix());
	}

	@Test
	public void testRREF() throws IOException {
		MatrixCalculator cal = new MatrixCalculator();

		Matrix a = new Matrix("A", 3, 4);

		Matrix expected = new Matrix("A", 3, 4);
		double[][] expectedData = new double[3][4];

		double[][] a_data1 = new double[3][4];

		a_data1[0][0] = 1;
		a_data1[0][1] = -2;
		a_data1[0][2] = 3;
		a_data1[0][3] = 7;

		a_data1[1][0] = 2;
		a_data1[1][1] = 1;
		a_data1[1][2] = 1;
		a_data1[1][3] = 4;

		a_data1[2][0] = -3;
		a_data1[2][1] = 2;
		a_data1[2][2] = -2;
		a_data1[2][3] = -10;

		expectedData[0][0] = 1;
		expectedData[0][1] = 0;
		expectedData[0][2] = 0;
		expectedData[0][3] = 2;

		expectedData[1][0] = 0;
		expectedData[1][1] = 1;
		expectedData[1][2] = 0;
		expectedData[1][3] = -1;

		expectedData[2][0] = 0;
		expectedData[2][1] = 0;
		expectedData[2][2] = 1;
		expectedData[2][3] = 1;

		a.setCurrentMatrix(a_data1);
		expected.setCurrentMatrix(expectedData);
		double[][] theResult = cal.rref(a_data1);

		int count = 0;
		double[][] resultAsInteger = new double[theResult.length][theResult[0].length];
		for (int i = 0; i < theResult.length; i++) {
			for (int j = 0; j < theResult[0].length; j++) {
				System.out.print(Math.round(theResult[i][j]) + " ");
				resultAsInteger[i][j] = (int) (Math.round(theResult[i][j]));
			}
			System.out.println(" ");
		}

		Matrix resultMatrix = new Matrix("A", 3, 4);
		resultMatrix.setCurrentMatrix(resultAsInteger);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixAddition() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix b = new Matrix("B", 2, 2);

		Matrix expected = new Matrix("A+B", 2, 2);

		double[][] a_data = new double[2][2];
		double[][] b_data = new double[2][2];
		double[][] expectedData = new double[2][2];

		a_data[0][0] = 2;
		a_data[0][1] = 2;
		a_data[1][0] = 2;
		a_data[1][1] = 2;

		b_data[0][0] = 2;
		b_data[0][1] = 2;
		b_data[1][0] = 2;
		b_data[1][1] = 2;

		expectedData[0][0] = 4;
		expectedData[0][1] = 4;
		expectedData[1][0] = 4;
		expectedData[1][1] = 4;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.addMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixAddition2() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 4);
		Matrix b = new Matrix("B", 2, 4);

		Matrix expected = new Matrix("A+B", 2, 4);

		double[][] a_data = new double[2][4];
		double[][] b_data = new double[2][4];
		double[][] expectedData = new double[2][4];

		a_data[0][0] = 2;
		a_data[0][1] = 4;
		a_data[0][2] = 8;
		a_data[0][3] = -3;

		a_data[1][0] = 0;
		a_data[1][1] = 1;
		a_data[1][2] = 2;
		a_data[1][3] = 3;

		b_data[0][0] = -3;
		b_data[0][1] = 4;
		b_data[0][2] = 0;
		b_data[0][3] = 1;

		b_data[1][0] = 6;
		b_data[1][1] = 8;
		b_data[1][2] = 2;
		b_data[1][3] = 0;

		expectedData[0][0] = -1;
		expectedData[0][1] = 8;
		expectedData[0][2] = 8;
		expectedData[0][3] = -2;

		expectedData[1][0] = 6;
		expectedData[1][1] = 9;
		expectedData[1][2] = 4;
		expectedData[1][3] = 3;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.addMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixSubtraction() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 4);
		Matrix b = new Matrix("B", 2, 4);

		Matrix expected = new Matrix("A-B", 2, 4);

		double[][] a_data = new double[2][4];
		double[][] b_data = new double[2][4];
		double[][] expectedData = new double[2][4];

		a_data[0][0] = 2;
		a_data[0][1] = 4;
		a_data[0][2] = 8;
		a_data[0][3] = -3;

		a_data[1][0] = 0;
		a_data[1][1] = 1;
		a_data[1][2] = 2;
		a_data[1][3] = 3;

		b_data[0][0] = -3;
		b_data[0][1] = 4;
		b_data[0][2] = 0;
		b_data[0][3] = 1;

		b_data[1][0] = 6;
		b_data[1][1] = 8;
		b_data[1][2] = 2;
		b_data[1][3] = 0;

		expectedData[0][0] = 5;
		expectedData[0][1] = 0;
		expectedData[0][2] = 8;
		expectedData[0][3] = -4;

		expectedData[1][0] = -6;
		expectedData[1][1] = -7;
		expectedData[1][2] = 0;
		expectedData[1][3] = 3;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.subtractMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixSubtraction2() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 4);
		Matrix b = new Matrix("B", 2, 4);

		Matrix expected = new Matrix("B-A", 2, 4);

		double[][] a_data = new double[2][4];
		double[][] b_data = new double[2][4];
		double[][] expectedData = new double[2][4];

		a_data[0][0] = 2;
		a_data[0][1] = 4;
		a_data[0][2] = 8;
		a_data[0][3] = -3;

		a_data[1][0] = 0;
		a_data[1][1] = 1;
		a_data[1][2] = 2;
		a_data[1][3] = 3;

		b_data[0][0] = -3;
		b_data[0][1] = 4;
		b_data[0][2] = 0;
		b_data[0][3] = 1;

		b_data[1][0] = 6;
		b_data[1][1] = 8;
		b_data[1][2] = 2;
		b_data[1][3] = 0;

		expectedData[0][0] = -5;
		expectedData[0][1] = 0;
		expectedData[0][2] = -8;
		expectedData[0][3] = 4;

		expectedData[1][0] = 6;
		expectedData[1][1] = 7;
		expectedData[1][2] = 0;
		expectedData[1][3] = -3;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.subtractMatrices(b, a);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixMultiplication() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 3);
		Matrix b = new Matrix("B", 3, 4);

		Matrix expected = new Matrix("A*B", 2, 4);

		double[][] a_data = new double[2][3];
		double[][] b_data = new double[3][4];
		double[][] expectedData = new double[2][4];

		a_data[0][0] = 2;
		a_data[0][1] = 4;
		a_data[0][2] = -1;

		a_data[1][0] = 5;
		a_data[1][1] = 8;
		a_data[1][2] = 0;

		b_data[0][0] = 2;
		b_data[0][1] = 5;
		b_data[0][2] = 1;
		b_data[0][3] = 4;

		b_data[1][0] = 4;
		b_data[1][1] = 8;
		b_data[1][2] = 0;
		b_data[1][3] = 6;

		b_data[2][0] = -3;
		b_data[2][1] = 1;
		b_data[2][2] = -2;
		b_data[2][3] = -1;

		expectedData[0][0] = 23;
		expectedData[0][1] = 41;
		expectedData[0][2] = 4;
		expectedData[0][3] = 33;

		expectedData[1][0] = 42;
		expectedData[1][1] = 89;
		expectedData[1][2] = 5;
		expectedData[1][3] = 68;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.multipyMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixMultiplication2() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix b = new Matrix("B", 2, 2);

		Matrix expected = new Matrix("A*B", 2, 2);

		double[][] a_data = new double[2][2];
		double[][] b_data = new double[2][2];
		double[][] expectedData = new double[2][2];

		a_data[0][0] = -4;
		a_data[0][1] = 6;

		a_data[1][0] = 12;
		a_data[1][1] = 999;

		b_data[0][0] = 1;
		b_data[0][1] = 2;

		b_data[1][0] = 3;
		b_data[1][1] = -4;

		expectedData[0][0] = 14;
		expectedData[0][1] = -32;

		expectedData[1][0] = 3009;
		expectedData[1][1] = -3972;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.multipyMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testMatrixMultiplication3() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 3);
		Matrix b = new Matrix("B", 3, 2);

		Matrix expected = new Matrix("A*B", 2, 2);

		double[][] a_data = new double[2][3];
		double[][] b_data = new double[3][2];
		double[][] expectedData = new double[2][2];

		a_data[0][0] = 2;
		a_data[0][1] = 1;
		a_data[0][2] = 3;

		a_data[1][0] = 1;
		a_data[1][1] = -1;
		a_data[1][2] = 0;

		b_data[0][0] = 1;
		b_data[0][1] = 0;

		b_data[1][0] = 2;
		b_data[1][1] = 1;

		b_data[2][0] = 3;
		b_data[2][1] = 2;

		expectedData[0][0] = 13;
		expectedData[0][1] = 7;

		expectedData[1][0] = -1;
		expectedData[1][1] = -1;

		a.setCurrentMatrix(a_data);
		b.setCurrentMatrix(b_data);
		expected.setCurrentMatrix(expectedData);

		Matrix resultMatrix = cal.multipyMatrices(a, b);

		if (cal.areSameMatrices(expected, resultMatrix)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testSameDimensions_true() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 5);
		Matrix b = new Matrix("B", 3, 5);

		assertTrue(cal.areSameDimensions(a, b));
	}

	@Test
	public void testSameDimensions_false() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 3, 3);
		Matrix b = new Matrix("B", 23, 5);

		assertFalse(cal.areSameDimensions(a, b));
	}

	@Test
	public void addMatrix() {
		int expectedCount = 1;
		MatrixCalculator cal = createTestMatrix();
		cal.deleteMatrix("A");
		cal.deleteMatrix("B");

		Matrix additionalMatrix = new Matrix();
		cal.addMatrix(additionalMatrix);

		if (cal.getListOfMatrices().size() != expectedCount) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	@Test
	public void deleteMatrix() {
		int expectedCount = 0;
		MatrixCalculator cal = createTestMatrix();
		cal.deleteMatrix("A");
		cal.deleteMatrix("B");

		if (cal.getListOfMatrices().size() != expectedCount) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	@Test
	public void changeMatrix() {
		MatrixCalculator cal = createTestMatrix();
		Matrix updated = new Matrix("A", 5, 5);
		Matrix new2 = new Matrix("A", 3, 3);
		cal.changeMatrixData("A", updated);
		// cal.showCurrentMatrices();
		if (cal.getListOfMatrices().contains(updated)) {
			if (!(cal.getListOfMatrices().contains(new2))) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}
	}

	@Test
	public void setMatrix() {
		MatrixCalculator cal = createTestMatrix();

		double[][] expectedData = makeCurrentData(1997);
		int row = expectedData.length;
		int column = expectedData[0].length;
		cal.setCurrentDataMatrix_List("A", expectedData, row, column);
		int index = 0;
		if (cal.getKeySet().contains("A")) {
			ArrayList<Matrix> list = cal.getListOfMatrices();
			loop: for (Matrix eachMatrix : list) {
				if (eachMatrix.getName().equals("A")) {
					if (eachMatrix.getCurrentMatrix().equals(expectedData)) {
						assertTrue(true);
						break loop;
					} else {
						assertFalse(false);
					}
				}
			}
		}
	}

	public double[][] makeCurrentData(int number) {
		double[][] currentData1 = new double[5][15];

		for (int row = 0; row < currentData1.length; row++) {
			for (int col = 0; col < currentData1[row].length; col++) {
				currentData1[row][col] = number;
			}
		}
		return currentData1;
	}

	public MatrixCalculator createTestMatrix() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix new2 = new Matrix("A", 3, 3);
		Matrix new3 = new Matrix("B", 3, 13);
		cal.addMatrix(new2);
		cal.addMatrix(new3);
		return cal;
	}
}
