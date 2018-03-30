package LinearAlgebra;
public class Main {

    public static void main(String [] args) {
        double[] vec =  {1,2,0,0};
        double[] vec1 = {0,1,3,0};
        double[] vec2 = {0,5,1,0};
        double[] vec3 = {0,0,0,1};

        double[][] matrix = {{1,2,3,4},
                             {5,6,7,8},
                             {9,10,11,12}};

        Matrix k = new Matrix(matrix);

        System.out.println(k.getRREF());
        try {
            LinearRow row1 = new LinearRow(vec);
            LinearRow row2 = new LinearRow(vec1);
            LinearRow row3 = new LinearRow(vec2);
            LinearRow row4 = new LinearRow(vec3);
            double[] vec4 = {0,0,1,0};
            LinearRow col1 = new LinearRow(vec4);

            Matrix m = new Matrix(row1, row2);
            Matrix n = new Matrix(row1, row2, row3, row4);
           // System.out.print(m.multiplyWithMatrix(n));
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

            m.selfMultiplyWithMatrix(m); // multiplies m by itself

            System.out.println(m.multiplyWithVector(col1)); // multiplies m with the vector col1 (e3) which will return the 3rd column of m

            */


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
