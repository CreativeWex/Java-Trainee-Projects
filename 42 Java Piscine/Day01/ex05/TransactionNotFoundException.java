public class TransactionNotFoundException extends RuntimeException {
    public String toString()
    {
        return "Can't find Transaction with this Identifier.";
    }
}
