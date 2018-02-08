package LinearAlgebra;

import LinearAlgebra.Exceptions.*;

import java.util.Arrays;

public class Matrix {

    private LinearColumn[] columns;
    private LinearRow[] rows;

    public Matrix(LinearColumn... columns) {
        this.columns = columns;
        this.rows = new LinearRow[columns[0].length()];
        double[][] toBeRows = new double[rows.length][columns.length];
        int i = 0;
        for (LinearVector vector : columns) {
            for (int j = 0; j < vector.length(); j++) {
                toBeRows[j][i] = vector.getIndexOf(j);
            }
            i++;
        }
        for (int m = 0; m < rows.length; m++) {
            this.rows[m] = new LinearRow(toBeRows[m]);
        }
    }

    public Matrix(LinearRow... rows) {
        this.rows = rows;
        this.columns = new LinearColumn[rows[0].length()];
        double[][] toBeColumns = new double[columns.length][rows.length];
        for(int i = 0 ; i < columns.length; i ++) {
            for (int j = 0; j < rows.length; j++) {
                toBeColumns[i][j] = rows[j].getIndexOf(i);
            }
        }
        for (int n = 0; n < columns.length; n++) {
            this.columns[n] = new LinearColumn(toBeColumns[n]);
        }
    }


    public double getIndexOf(int i, int j) throws IndexOutOfBounds {
        if ((i < 0) || (j < 0) || (i >= rows.length) || (j >= columns.length)) throw new IndexOutOfBounds("Invalid index given");
        return rows[i].getIndexOf(j);
    }

    public Matrix transpose() {
        LinearRow[] newCols = new LinearRow[columns.length];
        for (int i = 0; i < columns.length; i ++) {
            newCols[i] = columns[i].toRow();
        }
        return new Matrix(newCols);
    }

    private Matrix getDetMatrixOf(int i, int j) {
        LinearRow[] newRows = new LinearRow[rows.length - 1];
        double[][] toBeRows = new double[newRows.length][rows[0].length() - 1];
        int k =0,p = 0;
        for(int m = 0; m < rows.length; m++) {
            if (m != i) {
                for (int n = 0; n < rows[0].length(); n++) {
                    if (n != j) {
                        toBeRows[k][p++] = rows[m].getIndexOf(n);
                    }
                }
                p = 0;
                k++;
            }
        }
        for(int m = 0; m < newRows.length; m ++) newRows[m] = new LinearRow(toBeRows[m]);
        return new Matrix(newRows);
    }

    public double getDet() throws IllegalOperation {
        if (columns.length != rows.length) throw new IllegalOperation("Cannot calculate determinant of a non-square matrix");
        if (rows.length == 1) return rows[0].getIndexOf(0);
        if (rows.length == 2) {
            return (rows[0].getIndexOf(0) * rows[1].getIndexOf(1)) -
                    (rows[0].getIndexOf(1) * rows[1].getIndexOf(0));
        }
        double ans = 0;
        for(int j = 0; j < rows[0].length(); j ++) {
            ans += rows[0].getIndexOf(j) * Math.pow(-1, 1 + (j+1)) * getDetMatrixOf(0, j).getDet();
        }
        return ans;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(LinearRow row: rows) { sb.append(row.toString()).append("\n"); }
        return sb.toString();
    }

    public LinearColumn[] getColumns() {
        return columns.clone();
    }

    public LinearRow[] getRows() {
        return rows.clone();
    }
}

