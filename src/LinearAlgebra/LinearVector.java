package LinearAlgebra;
import LinearAlgebra.Exceptions.*;

import java.util.Arrays;

public class LinearVector {
    protected double[] vector;

    public LinearVector(double[] values) {
        vector = new double[values.length];
        System.arraycopy(values, 0, vector, 0, vector.length);
        }

    public int length() { return vector.length; }

    public double[] getVector() { return vector.clone(); }

    public boolean isZeroVector() {
        for(double val: vector) {
            if (val != 0) return false;
        }
        return true;
    }

    public LinearVector addAndCreate(LinearVector other) throws IllegalOperation {
        if (other.length() != this.length()) throw new IllegalOperation("Cant preform operations on vectors with different dimensions");
        double[] otherVector = other.getVector();
        double [] thisVector = this.vector;
        for(int i = 0; i < vector.length; i++) thisVector[i] += otherVector[i];
        return new LinearVector(thisVector);
    }

    public void add(LinearVector other) throws IllegalOperation {
        if (other.length() != this.length()) throw new IllegalOperation("Cant preform operations on vectors with different dimensions");
        double[] otherVector = other.getVector();
        for(int i = 0; i < vector.length; i++) vector[i] += otherVector[i];
    }

    public double getIndexOf(int i) throws IndexOutOfBounds {
        if ((i >= vector.length) || (i < 0)) throw new IndexOutOfBounds("Invalid index");
        return vector[i];
    }

    public void multiply(double c) {
        for(int i = 0; i < vector.length; i ++) {
            vector[i] = vector[i] * c;
        }
    }

    public LinearVector multiplyAndCreate(double c) {
        double[] thisVector = this.vector;
        for(int i = 0; i < vector.length; i ++) {
            thisVector[i] = vector[i] * c;
        }
        return new LinearVector(thisVector);
    }

    public LinearColumn toColumn() { return new LinearColumn(this.vector); }

    public LinearRow toRow() { return new LinearRow(this.vector);}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(double v: vector) { builder.append("|").append(v).append("|").append("\n"); }
        return builder.toString().substring(0, builder.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinearVector)) return false;
        LinearVector vector1 = (LinearVector) o;
        return Arrays.equals(vector, vector1.getVector());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }
}
