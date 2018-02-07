package LinearAlgebra;
import LinearAlgebra.Exceptions.*;

import java.util.Arrays;

public class LinearVector {
    private double[] vector;

    public LinearVector(double[] values) {
        vector = new double[values.length];

        for(int i = 0; i < vector.length; i ++) {
            this.vector[i] = values[i];
        }
    }

    public int length() { return vector.length; }

    public double[] getVector() { return vector; }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(double v: vector) { builder.append(v).append("\n"); }
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
