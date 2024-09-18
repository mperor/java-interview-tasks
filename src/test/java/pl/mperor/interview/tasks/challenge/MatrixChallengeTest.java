package pl.mperor.interview.tasks.challenge;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h2>Matrix Challenge</h2>
 * <p>
 * The function <code>MatrixChallenge(strArr: String[]): number</code> takes the <code>strArr</code> parameter being passed which will be a 2D matrix of 0's and 1's, and determines the area of the largest rectangular submatrix that contains all 1's.
 * </p>
 * <h3>Example:</h3>
 * <p>
 * If strArr is ["01000", "10111", "11111", "10101"] then this looks like the following matrix:
 *
 * <pre>
 * 0 1 0 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 1 0 1
 * </pre>
 * <p>
 * For the input above, you can see the bolded 'ones' create the largest rectangular submatrix of size (2x3), so your program should return the area which is (6).
 * You can assume the input will not be empty.
 *
 * <p><b>More Examples:</b>
 * <p>
 * <strong>Input:</strong>
 * <pre>
 * new String[] {"011", "011", "011", "111"}
 * Output:
 * 8
 * </pre>
 * <p>
 * <strong>Input:</strong>
 * <pre>
 * new String[] {"010", "111", "010"}
 * Output:
 * 3
 * </pre>
 */
public class MatrixChallengeTest {

    private static class MatrixCalculator {

        private final int[][] matrix;
        private int cols;
        private int rows;

        private MatrixCalculator(String[] input) {
            this.matrix = Arrays.stream(input)
                    .map(line -> line.chars().map(c -> Character.getNumericValue(c)).toArray())
                    .toArray(int[][]::new);

            rows = matrix.length;
            cols = rows == 0 ? 0 : matrix[0].length;
        }

        public int calculateArea() {
            int maxArea = 0;
            for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
                for (int colIdx = 0; colIdx < cols; colIdx++) {
                    if (matrix[rowIdx][colIdx] == 1) {
                        maxArea = Math.max(maxArea, findMaxAreaFromCell(MatrixCell.of(rowIdx, colIdx)));
                    }
                }
            }
            return maxArea;
        }

        private int findMaxAreaFromCell(MatrixCell cell) {
            int width = calculateRowWidth(cell);

            MatrixCell nextCell = cell.nextRowCell();
            while (nextCell.rowIdx < rows && calculateRowWidthUntil(nextCell, cell.colIdx + width) == width) {
                nextCell = nextCell.nextRowCell();
            }

            int length = nextCell.rowIdx - cell.rowIdx;
            return width * length;
        }

        private int calculateRowWidth(MatrixCell cell) {
            return calculateRowWidthUntil(cell, cols);
        }

        private int calculateRowWidthUntil(MatrixCell cell, int until) {
            int width = 0;
            while (cell.colIdx + width < until && matrix[cell.rowIdx][cell.colIdx + width] == 1) {
                width++;
            }
            return width;
        }

        private record MatrixCell(int rowIdx, int colIdx) {
            public MatrixCell nextRowCell() {
                return new MatrixCell(rowIdx + 1, colIdx);
            }

            public static MatrixCell of(int rowIdx, int colIdx) {
                return new MatrixCell(rowIdx, colIdx);
            }
        }
    }

    @Test
    public void testEmptyTableBehaviour() {
        assertEquals(0, new MatrixCalculator(new String[]{}).calculateArea());
    }

    @Test
    public void testFindAreaOfLargestRectangularMatrixNaiveWay() {
        assertEquals(6, new MatrixCalculator(new String[]{
                "01000",
                "10111",
                "11111",
                "10101",
        }).calculateArea());

        assertEquals(8, new MatrixCalculator(new String[]{
                "011",
                "011",
                "011",
                "111"
        }).calculateArea());

        assertEquals(3, new MatrixCalculator(new String[]{
                "010",
                "111",
                "010",
                "000"
        }).calculateArea());

        assertEquals(4, new MatrixCalculator(new String[]{
                "100",
                "111",
                "110",
                "100"
        }).calculateArea());
    }
}
