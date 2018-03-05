package LinearAlgebra;
import java.util.Arrays;

public class Main {

    public static void main(String [] args) {
        double[] vec = {3,5,0,4};
        double[] vec1 = {0,1,0,4};
        double[] vec2 = {0,0,3,4};
        double[] vec3 = {1,2,3,4};


        try {
            LinearRow row1 = new LinearRow(vec);
            LinearRow row2 = new LinearRow(vec1);
            LinearRow row3 = new LinearRow(vec2);
            LinearRow row4 = new LinearRow(vec3);

            Matrix m = new Matrix(row1, row2, row3, row4);
         /*  example usage:
            System.out.println(m); // String representation of matrix

            System.out.println(m.get(2, 2)); // 3

            System.out.println(m.transpose()); // transposed matrix

            System.out.println(m.getDet()); // calcs det of matrix

            m.selfTranspose(); // transpose the matrix without returning a new Matrix object)

            System.out.println(m.getDet()); // should be the same value as before transpose

            System.out.println(m.getInverse()); // prints the inverse of m

            for(double[] row : m.getMatrixAsArray()) { // returns the matrix as a 2D array object
                System.out.println(Arrays.toString(row));
            }

            m.RowInterchange(0, 1); // switches the first and second row in the matrix
            m.RowReplacement(0, 1, 2, LinearRow.MINUS); // subtracts the value of (row1 * 2) from row0
            m.RowScaling(0, 3); // scales row 0 by 3

            */


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
