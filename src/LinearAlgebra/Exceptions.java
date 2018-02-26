package LinearAlgebra;

public class Exceptions {

    static class IllegalOperation extends Exception {

        public IllegalOperation(String exceptionMessage) {
            super(exceptionMessage);
        }

        public IllegalOperation() { super();}
    }

    static class IndexOutOfBounds extends ArrayIndexOutOfBoundsException {

        public IndexOutOfBounds(String exceptionMessage) {
            super(exceptionMessage);
        }

        public IndexOutOfBounds() { super();}
    }

}