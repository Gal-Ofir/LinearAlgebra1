package LinearAlgebra;

import LinearAlgebra.Exceptions.*;

import java.util.Arrays;
import java.util.Objects;


public class Matrix {

    private LinearColumn[] columns;
    private LinearRow[] rows;
    private double det = Double.MIN_VALUE;
    private boolean isInvertible = false;
    private int N, M; // where M is number of rows and N is number of columns

    public Matrix(LinearColumn... columns) {
        this.columns = columns;
        N = columns.length;
        M = columns[0].length();
        this.rows = new LinearRow[M];
        double[][] toBeRows = new double[M][N];
        int i = 0;
        for (LinearVector vector : columns) {
            for (int j = 0; j < vector.length(); j++) {
                toBeRows[j][i] = vector.get(j);
            }
            i++;
        }

        for (int m = 0; m < rows.length; m++) {
            this.rows[m] = new LinearRow(toBeRows[m]);
        }
    }

    public Matrix(LinearRow... rows) {
        this.rows = rows;
        M = rows.length;
        N = rows[0].length();
        this.columns = new LinearColumn[N];
        double[][] toBeColumns = new double[N][M];
        for(int i = 0 ; i < N; i ++) {
            for (int j = 0; j < M; j++) {
                toBeColumns[i][j] = rows[j].get(i);
            }
        }
        for (int n = 0; n < N; n++) {
            this.columns[n] = new LinearColumn(toBeColumns[n]);

        }

    }

    public Matrix(double[]... rows) {
        M = rows.length;
        N = rows[0].length;
        this.rows = new LinearRow[M];
        this.columns = new LinearColumn[N];

        for(int i = 0 ; i < M; i++) {
            this.rows[i] = new LinearRow(rows[i]);
        }

        //initiate LinearColumns array to avoid NullPointerException
        for(int i = 0; i < N; i++) {
            this.columns[i] = new LinearColumn(rows.length);
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < this.columns[0].length(); j++) {
                this.columns[i].set(j, this.rows[j].get(i));
            }
        }
    }

    public double[][] getMatrixAsArray() {

        double[][] matrix = new double[M][N];

        int i = 0;
        for(LinearRow row: this.rows) {
            for(int j = 0; j < row.length(); j++) {
                matrix[i][j] = row.get(j);
            }
            i++;
        }
        return matrix;
    }

    public void set(int i, int j, double val) throws IndexOutOfBounds {
        if ((i < 0) || (j < 0) || (i >= M) || (j >= N)) throw new IndexOutOfBounds("Invalid index given");
        // changing a value in the matrix can change det value, so need to recalculate it
        this.det = Double.MIN_VALUE;
        this.rows[i].set(j, val);
        this.columns[j].set(i, val);
    }

    public double get(int i, int j) throws IndexOutOfBounds {
        if ((i < 0) || (j < 0) || (i >= M) || (j >= N)) throw new IndexOutOfBounds("Invalid index given");
        return rows[i].get(j);
    }

    public Matrix transpose() {
        LinearRow[] newCols = new LinearRow[N];
        for (int i = 0; i < N; i ++) {
            newCols[i] = columns[i].toRow();
        }
        return new Matrix(newCols);
    }

    public void multiplyByScalar(double c) {
        for(LinearRow row: this.rows) {
            row.multiply(c);
        }
        for(LinearColumn column: this.columns) {
            column.multiply(c);
        }
    }

    public void RowScaling(int m, double c) throws IndexOutOfBounds {
        if (m >= M || m < 0) throw new IndexOutOfBounds("Invalid index given");
        this.rows[m].multiply(c);
        for(LinearColumn column: this.columns) {
            column.set(m, column.get(m) * c);
        }
    }

    public void RowReplacement(int t, int d, double times, String operator) throws IndexOutOfBounds, IllegalOperation {
        if (t >= M || d >= M || d < 0 || t < 0) throw new IndexOutOfBounds("Invalid index given");
        if (!operator.equals(LinearRow.MINUS) && !operator.equals(LinearRow.PLUS)) throw new IllegalOperation("Illegal row operation");
        LinearRow temp = new LinearRow(this.rows[d].getVector());
        times *= (operator.equals("+")) ? 1 : -1;
        temp.multiply(times);
        this.rows[t].add(temp);
        int i = 0;
        for(LinearColumn column: this.columns) {
            column.set(t, this.rows[t].get(i++));
        }
    }

    public void RowInterchange(int t, int d) throws IndexOutOfBounds {
        if (t >= M || d >= M || d < 0 || t < 0) throw new IndexOutOfBounds("Invalid index given");
        this.rows[t].RowInterchange(this.rows[d]);
        int i = 0;
        for(LinearColumn column: this.columns) {
            column.set(t, this.rows[t].get(i));
            column.set(d, this.rows[d].get(i++));
        }
    }

    public void selfTranspose() {
        LinearRow[] newRows = new LinearRow[N];
        LinearColumn[] newColumns = new LinearColumn[M];

        for(int i = 0; i < N; i++) {
            newRows[i] = columns[i].toRow();
        }

        for(int i = 0; i < M; i++) {
            newColumns[i] = rows[i].toColumn();
        }

        this.rows = newRows;
        this.columns = newColumns;
    }

    private Matrix getDetMatrixOf(int i, int j) {
        LinearRow[] newRows = new LinearRow[M - 1];
        double[][] toBeRows = new double[newRows.length][N - 1];
        int k =0,p = 0;
        for(int m = 0; m < M; m++) {
            if (m != i) {
                for (int n = 0; n < N; n++) {
                    if (n != j) {
                        toBeRows[k][p++] = rows[m].get(n);
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
        if (N != M) throw new IllegalOperation("Cannot calculate determinant of a non-square matrix");
        if (this.det != Double.MIN_VALUE) return det;
        if (N == 1) return rows[0].get(0);
        if (N == 2) {
            return ((rows[0].get(0) * rows[1].get(1)) -
                    (rows[0].get(1) * rows[1].get(0)));
        }
        double ans = 0;
        for(int j = 0; j < rows[0].length(); j ++) {
            ans += rows[0].get(j) * Math.pow(-1, 1 + (j+1)) * getDetMatrixOf(0, j).getDet();
        }
        this.det = ans;
        if (det != 0) {
            this.isInvertible = true;
        }
        return det;
    }

    public Matrix getAdjMatrix() throws IllegalOperation {
        if (M != N) throw new IllegalOperation("Cannot calculate determinant of a non-square matrix");
        LinearRow[] cofactorRows = new LinearRow[M];
        for(int i = 0; i < cofactorRows.length; i++) { cofactorRows[i] = new LinearRow(N); }
        double cof;
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < N; j++) {
                cof = (Math.pow(-1, i + j) * getDetMatrixOf(i, j).getDet());
                cofactorRows[i].set(j,cof);
            }
        }
        return new Matrix(cofactorRows).transpose();
    }

    public Matrix getInverse() throws IllegalOperation {
        if (columns.length != rows.length) throw new IllegalOperation("Cannot inverse a non-square matrix");
        if (this.getDet() == 0) throw new IllegalOperation("Cannot inverse Matrix (determinant is equal to 0");
        double scalar = 1 / det;
        Matrix adj = getAdjMatrix();
        adj.multiplyByScalar(scalar);
        return adj;
    }

    public LinearColumn[] getColumns() {
        return columns.clone();
    }

    public LinearRow[] getRows() {
        return rows.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(LinearRow row: rows) { sb.append(row.toString()).append("\n"); }
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        return Double.compare(matrix.det, det) == 0 &&
                isInvertible == matrix.isInvertible &&
                Arrays.equals(columns, matrix.columns) &&
                Arrays.equals(rows, matrix.rows);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(det, isInvertible);
        result = 31 * result + Arrays.hashCode(columns);
        result = 31 * result + Arrays.hashCode(rows);
        return result;
    }
}

