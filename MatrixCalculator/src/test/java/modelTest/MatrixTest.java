package modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import control.MatrixCalculator;
import model.Matrix;

public class MatrixTest {

	@Test
	public void testMatrixAddition() {
		MatrixCalculator cal = new MatrixCalculator();
		Matrix a = new Matrix("A", 2, 2);
		Matrix b = new Matrix("B", 2, 2);

		Matrix expected = new Matrix("A+B", 2, 2);

		int[][] a_data = new int[2][2];
		int[][] b_data = new int[2][2];
		int[][] expectedData = new int[2][2];

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

		int[][] a_data = new int[2][4];
		int[][] b_data = new int[2][4];
		int[][] expectedData = new int[2][4];

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

		int[][] a_data = new int[2][4];
		int[][] b_data = new int[2][4];
		int[][] expectedData = new int[2][4];

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

		int[][] a_data = new int[2][4];
		int[][] b_data = new int[2][4];
		int[][] expectedData = new int[2][4];

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
		Matrix a = new Matrix("A", 2, 4);
		Matrix b = new Matrix("B", 2, 4);

		Matrix expected = new Matrix("A*B", 2, 4);

		int[][] a_data = new int[2][4];
		int[][] b_data = new int[2][4];
		int[][] expectedData = new int[2][4];

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

		int[][] expectedData = makeCurrentData(1997);
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

	public int[][] makeCurrentData(int number) {
		int[][] currentData1 = new int[5][15];

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
