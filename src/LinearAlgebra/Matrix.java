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

    /** Constructs a new Matrix out of an array of LinearColumns
     *
     * @param columns - Linear vectors represented as columns that will be the matrix' columns
     */
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

    /** Constructs a new Matrix out of an array of LinearRows
     *
     * @param rows - Linear vectors represented as rows that will be the matrix' rows
     */
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

    /** Constructs a new Matrix out of an array of doubles
     *
     * @param rows - Arrays of doubles representing rows that will be matrix' rows
     */
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

    /** Creates an identity nxn Matrix of the form |1 0 . . 0|
     *                                         |0 1 . . 0|
     *                                         |0 . . . 0|
     *                                         |0 . . 0 1|
     * @param n dimension of the identity matrix to return
     * @return identity matrix of nxn dimensions
     */
    public static Matrix getSingularMatrix(int n) {
        double [][] matrix = new double[n][n];
        for (int i = 0 ; i < n ; i ++) { matrix[i][i] = 1; }
        return new Matrix(matrix);
    }

    /** Returns the matrix as a 2D double array
     *
     * @return the matrix as a 2D double array
     */
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

    /** Update a value in M[i][j]
     *
     * @param i row
     * @param j column
     * @param val new value
     * @throws IndexOutOfBounds - throws an exception if either i or j is out of range for the matrix
     */
    public void set(int i, int j, double val) throws IndexOutOfBounds {
        if ((i < 0) || (j < 0) || (i >= M) || (j >= N)) throw new IndexOutOfBounds("Invalid index given");
        // changing a value in the matrix can change det value, so need to recalculate it
        this.det = Double.MIN_VALUE;
        this.rows[i].set(j, val);
        this.columns[j].set(i, val);
    }

    /** Returns value at M[i][j]
     *
     * @param i row
     * @param j column
     * @return value at M[i][j]
     * @throws IndexOutOfBounds throws exception if i or j out of range
     */
    public double get(int i, int j) throws IndexOutOfBounds {
        if ((i < 0) || (j < 0) || (i >= M) || (j >= N)) throw new IndexOutOfBounds("Invalid index given");
        return rows[i].get(j);
    }

    /** Returns a transposed matrix
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        LinearRow[] newCols = new LinearRow[N];
        for (int i = 0; i < N; i ++) {
            newCols[i] = columns[i].toRow();
        }
        return new Matrix(newCols);
    }

    /** Multiplies the whole matrix by scalar c
     *
     * @param c scalar
     */
    public void multiplyByScalar(double c) {
        for(LinearRow row: this.rows) {
            row.multiply(c);
        }
        for(LinearColumn column: this.columns) {
            column.multiply(c);
        }
    }

    /** Preforms the row scaling operation
     *
     * @param m row to scale
     * @param c scalar to scale by
     * @throws IndexOutOfBounds if m is out of range, throws exception
     */
    public void RowScaling(int m, double c) throws IndexOutOfBounds {
        if (m >= M || m < 0) throw new IndexOutOfBounds("Invalid index given");
        if (det != Double.MIN_VALUE) det *= c; // Row scaling multiplies det by c
        this.rows[m].RowScaling(c);
        for(LinearColumn column: this.columns) {
            column.set(m, column.get(m) * c);
        }
    }

    /** Preforms the row replacement operation
     *
     * @param t target row (the row to be manipulated)
     * @param d source row (the row to use on target)
     * @param times scalar to multiply d with
     * @param operator either addition or subtraction (to be removed, unnecessary)
     * @throws IndexOutOfBounds throws if t or d is out of range
     * @throws IllegalOperation throws exception if operator is not a known action
     */
    public void RowReplacement(int t, int d, double times, String operator) throws IndexOutOfBounds, IllegalOperation {
        if (t >= M || d >= M || d < 0 || t < 0) throw new IndexOutOfBounds("Invalid index given");
        if (!operator.equals(LinearRow.MINUS) && !operator.equals(LinearRow.PLUS)) throw new IllegalOperation("Illegal row operation");
       /* LinearRow temp = new LinearRow(this.rows[d].getVector());
        times *= (operator.equals("+")) ? 1 : -1;
        temp.multiply(times);
        this.rows[t].add(temp); */
        this.rows[t].RowReplacement(this.rows[d], operator, times);
        int i = 0;
        for(LinearColumn column: this.columns) {
            column.set(t, this.rows[t].get(i++));
        }
    }

    /** Preforms the row interchange operation
     *
     * @param t target row
     * @param d destination row
     * @throws IndexOutOfBounds throws exception if t or d is out of range
     */
    public void RowInterchange(int t, int d) throws IndexOutOfBounds {
        if (t >= M || d >= M || d < 0 || t < 0) throw new IndexOutOfBounds("Invalid index given");
        if (det != Double.MIN_VALUE) det *= -1; // Row interchange changes det by multiplying -1
        this.rows[t].RowInterchange(this.rows[d]);
        int i = 0;
        for(LinearColumn column: this.columns) {
            column.set(t, this.rows[t].get(i));
            column.set(d, this.rows[d].get(i++));
        }
    }

    /** Preforms a transpose on this matrix
     *
     */
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

    /** Returns the det Matrix of M[i][j] (i.e, for getDetMatrixOf(0, 0) will return a new matrix without row 0 and column 0)
     *  Used privately for calculating det
     * @param i row
     * @param j column
     * @return det matrix
     */
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

    /** Returns the determinant of matrix
     *
     * @return det
     * @throws IllegalOperation if matrix is not a square matrix, throw an exception
     */
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

    /** Returns the adjoint/adjugate matrix of this matrix
     *
     * @return adj matrix
     * @throws IllegalOperation if matrix is not a square matrix, throws exception
     */
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

    /** Returns the inverse of this matrix
     *
     * @return inverse matrix
     * @throws IllegalOperation if matrix is not square, throws exception
     */
    public Matrix getInverse() throws IllegalOperation {
        if (columns.length != rows.length) throw new IllegalOperation("Cannot inverse a non-square matrix");
        if (this.getDet() == 0) throw new IllegalOperation("Cannot inverse Matrix (determinant is equal to 0");
        double scalar = 1 / det;
        Matrix adj = getAdjMatrix();
        adj.multiplyByScalar(scalar);
        return adj;
    }

    /** Preforms matrix addition on the 2 matrix, returns a new matrix with the updated values
     *
     * @param other matrix to add to this one
     * @return new matrix with updated values
     * @throws IllegalOperation if matrices this and other are of different dimensions, throws exception
     */
    public Matrix add(Matrix other) throws IllegalOperation {
        if (M != other.M || N != other.N) throw new IllegalOperation("Matrices must have same dimensions when preforming matrix addition");
        LinearColumn[] newColumns = getColumns();
        for(int i = 0; i < newColumns.length; i++) {
            newColumns[i].add(other.columns[i]);
        }
        return new Matrix(newColumns);
    }

    /**
     *
     * @return LinearColumn[] columns of this matrix
     */
    public LinearColumn[] getColumns() {
        return columns.clone();
    }

    /**
     *
     * @return LinearRow[] rows of this matrix
     */
    public LinearRow[] getRows() {
        return rows.clone();
    }

    /** Performs a multiplication operation with a vector
     *
     * @param vector - vector to multiply by
     * @return the result of the multiplication
     * @throws IllegalOperation if vector's height (N) isn't equal to rows (M), throw an exception
     */
    public LinearVector multiplyWithVector(LinearVector vector) throws IllegalOperation {
        if (vector.length() != N) { throw new IllegalOperation("Cannot multiply vector with length " +vector.length() + " by Matrix with " +N +" columns"); }
        LinearVector result = new LinearVector(M);
        for(int i = 0; i < vector.length(); i++) {
            result.add(this.columns[i].multiplyAndCreate(vector.get(i)));
        }
        return result;
    }

    /** Performs a multiplication operation with another matrix
     *
     * @param other - matrix to multiply with
     * @return new matrix
     * @throws IllegalOperation if dimensions are illegal, throw exception
     */
    public Matrix multiplyWithMatrix(Matrix other) throws IllegalOperation {
        if (this.N != other.M) throw new IllegalOperation("Cannot multiply matrix with " + N + " columns by matrix with " + other.M + " rows");
        LinearColumn[] newColumns = new LinearColumn[other.N];
        for (int i = 0; i < other.columns.length; i++) {
            newColumns[i] = multiplyWithVector(other.columns[i]).toColumn();
        }
        return new Matrix(newColumns);
    }

    /** Returns a REF of this matrix
     *
     * @return REF matrix
     */
    public Matrix getREF() {
        Matrix reduced = new Matrix(this.columns);
        int i = 0, j = 0;
        while(i < M && j < N) {
            // this will ensure zero rows are all at the bottom of the matrix
            for(int k = i;k < M && reduced.rows[i].isZeroVector();k++) {
                reduced.RowInterchange(i, k);
            }
            // if there is a nonzero number in this column
            if (!reduced.columns[j].isZeroVector()) {
                // using row operations we will use the current column to zerofy all other coefficients
                for(int k = i + 1; k < M; k++) {
                    double ratio = reduced.get(k,j) / reduced.get(i, j);
                    try {
                        reduced.RowReplacement(k, i, ratio, LinearRow.MINUS);
                    }
                    catch (IllegalOperation I) { I.printStackTrace(); }
                }
            }
            i++;
            j++;
        }
        return reduced;
    }

    /** Returns the unique RREF of this matrix
     *
     * @return RREF matrix
     */
    public Matrix getRREF() {
        Matrix reduced = getREF();
        for (int i = 0; i < M; i++) {
            if (reduced.get(i, i) != 0) {
                reduced.RowScaling(i, 1 / reduced.get(i, i));

                // too much repeated code
                for(int m = i - 1; m >= 0; m--) {
                    double ratio = reduced.get(m, i) / reduced.get(i, i);
                    try {
                        reduced.RowReplacement(m, i, ratio, LinearRow.MINUS);
                    }
                    catch (IllegalOperation I) { I.printStackTrace(); }
                }
                for(int m = i+1; m < M; m++) {
                    double ratio = reduced.get(m, i) / reduced.get(i, i);
                    try {
                        reduced.RowReplacement(m, i, ratio, LinearRow.MINUS);
                    }
                    catch (IllegalOperation I) { I.printStackTrace(); }
                }
            }
        }
        for(int i = 0 ; i < M; i++) {
            if (reduced.get(i, i) != 0) reduced.RowScaling(i, 1 / reduced.get(i, i));
        }
        return reduced;
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

