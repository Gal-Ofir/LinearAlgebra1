package LinearAlgebra;

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
        for (int n = 0; n < columns.length; n++) { this.columns[n] = new LinearColumn(toBeColumns[n]); }
    }

    public Matrix transpose() {
        LinearRow[] newCols = new LinearRow[columns.length];
        for (int i = 0; i < columns.length; i ++) {
            newCols[i] = columns[i].toRow();
        }
        return new Matrix(newCols);
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

