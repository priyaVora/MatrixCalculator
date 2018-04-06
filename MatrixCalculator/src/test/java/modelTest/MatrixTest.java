package modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import control.MatrixCalculator;
import model.Matrix;

public class MatrixTest {

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
