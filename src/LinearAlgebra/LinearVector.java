package LinearAlgebra;
import LinearAlgebra.Exceptions.*;

import java.util.Arrays;

public class LinearVector {

    protected double[] vector;

    public LinearVector(double[] values) {
        vector = new double[values.length];
        System.arraycopy(values, 0, vector, 0, vector.length);
        }

    public LinearVector(int index) {
        vector = new double[index];
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

    public LinearVector add(LinearVector other) throws IllegalOperation {
        if (other.length() != this.length()) throw new IllegalOperation("Cant preform operations on vectors with different dimensions");
        double[] otherVector = other.getVector();
        for(int i = 0; i < vector.length; i++) vector[i] += otherVector[i];
        return this;
    }

    public double get(int i) throws IndexOutOfBounds {
        if ((i >= vector.length) || (i < 0)) throw new IndexOutOfBounds("Invalid index");
        return vector[i];
    }

    public void set(int i, double val) throws IndexOutOfBounds {
        if ((i >= vector.length) || (i < 0)) throw new IndexOutOfBounds("Invalid index");
        this.vector[i] = val;
    }

    public void multiply(double c) {
        for(int i = 0; i < vector.length; i++) {
            vector[i] *=  c;
        }
    }

    public LinearVector multiplyAndCreate(double c) {
        LinearVector vector = new LinearVector(this.vector.clone());
        vector.multiply(c);
        return vector;
    }

    public boolean isLinearDependentWith(LinearVector other) throws IllegalOperation {
        if (other.length() != this.length()) throw new IllegalOperation("Cant preform operations on vectors with different dimensions");
        double linearRelation = (other.vector[0] == 0) ? 0
                : this.vector[0] / other.vector[0];
        for(int i = 1; i < this.vector.length; i++) {
            if (other.vector[i] != 0 && this.vector[i] / other.vector[i] != linearRelation) return false;
        }
        return true;
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
