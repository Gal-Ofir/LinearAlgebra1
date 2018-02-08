package LinearAlgebra;
import LinearAlgebra.Exceptions.IllegalOperation;

public class Main {

    public static void main(String [] args) {
        double[] vec = {1,1,1};
        double[] vec1 = {2,4,5};
        double[] vec2 = {2,7,9};
        try {
            LinearColumn[] vectors = {new LinearColumn(vec), new LinearColumn(vec1), new LinearColumn(vec2)};
            LinearRow row1 = new LinearRow(vec);
            LinearRow row2 = new LinearRow(vec1);
            LinearRow row3 = new LinearRow(vec2);
            System.out.println(row1);
            row1.RowScaling(2);
            System.out.println(row1);
            row1.RowScaling(0.5);
            row1.RowReplacement(row2, LinearRow.MINUS);
            System.out.println(row1);
            //Matrix m = new Matrix(vectors);
            //System.out.println(m + "\n\n");


        }
        catch (Exception i) { System.out.println(i.getMessage());
        }
    }
}
