package LinearAlgebra;
import LinearAlgebra.Exceptions.IllegalOperation;

public class Main {

    public static void main(String [] args) {
        double[] vec = {3,5,0,4};
        double[] vec1 = {0,1,0,4};
        double[] vec2 = {0,0,3,4};
        double[] vec3 = {1,2,3,4};
        try {
            LinearColumn[] vectors = {new LinearColumn(vec), new LinearColumn(vec1), new LinearColumn(vec2), new LinearColumn(vec3)};
            LinearRow row1 = new LinearRow(vec);
            LinearRow row2 = new LinearRow(vec1);
            LinearRow row3 = new LinearRow(vec2);
            LinearRow row4 = new LinearRow(vec3);
            Matrix m = new Matrix(row1, row2, row3, row4);

            //example usage:
            System.out.println(m); // String representation of matrix
            System.out.println(m.getIndexOf(2, 2)); // 3
            System.out.println(m.transpose()); // transposed matrix
            System.out.println(m.getDet()); // calcs det of matrix
            m.selfTranspose(); // transpose the matrix without returning a new Matrix object)
            System.out.println(m);
            System.out.println(m.getDet()); // should be the same value as before transpose
        }
        catch (Exception i) { System.out.println(i.getMessage());
        }
    }
}
