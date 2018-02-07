package LinearAlgebra;
import LinearAlgebra.Exceptions.IllegalOperation;

public class Main {

    public static void main(String [] args) {
        double[] vec = {1,2,3,4,5};
        LinearVector vector = new LinearVector(vec);
       // System.out.println(vector);
        try {
            LinearVector[] vectors = {vector};
            Matrix m = new Matrix(vectors);
        }
        catch (Exception i) { System.out.println(i.getMessage());
        }
    }
}
