package LinearAlgebra;
import LinearAlgebra.Exceptions.*;
public class LinearRow extends LinearVector {

    static final String PLUS = "+";
    static final String MINUS = "-";


    public LinearRow(double[] row) {
        super(row);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for(double value : this.vector) { sb.append(value).append(","); }
        return sb.toString().substring(0, sb.length() - 1) + "|";
    }

    public void RowReplacement(LinearRow other, String operator, double times) throws IllegalOperation {
        if (!operator.equals(PLUS) && (!operator.equals(MINUS))) throw new IllegalOperation("Invalid row operation");
        LinearRow temp = new LinearRow(other.getVector());
        temp.multiply(times);
        switch (operator) {
            case MINUS:
                temp.multiply(-1);
            case PLUS:
                add(temp);
        }
    }

    public void RowReplacement(LinearRow other, String operator) throws IllegalOperation {
        if (!operator.equals(PLUS) && (!operator.equals(MINUS))) throw new IllegalOperation("Invalid row operation");
        LinearRow temp = new LinearRow(other.getVector());
        switch (operator) {
            case MINUS:
                temp.multiply(-1);
            case PLUS:
                add(temp);
        }
    }

    public void RowScaling(double times) {
        multiply(times);
    }
}
