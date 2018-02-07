package LinearAlgebra;

import java.util.Arrays;

public class Matrix {

    private LinearVector[] columns;
    private LinearVector[] rows;

    public Matrix(LinearVector[] columns) {
        this.columns = columns;
        this.rows = new LinearVector[columns[0].length()];
        double[][] toBeRows = new double[rows.length][columns.length];
        int i = 0;
        for (LinearVector vector : columns) {
            System.out.println(vector);
            for (int j = 0; j < vector.length(); j++) {
                System.out.println(vector.getIndexOf(j) + " "  + vector.length());
                toBeRows[i][j] = vector.getIndexOf(j);
            }
            i++;
        }
        for (double[] s:
             toBeRows) {
         System.out.println(Arrays.toString(s));
        }
        }
    }

