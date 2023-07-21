public class TransactionNotFoundException extends RuntimeException{
    @Override
    public String toString() {
        return "No such transaction";
    }
}
