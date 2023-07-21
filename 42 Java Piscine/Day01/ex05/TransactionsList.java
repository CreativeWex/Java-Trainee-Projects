import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction newTransaction);
    public void removeTransactionByIdentifier(UUID identifier) throws TransactionNotFoundException;

    public Transaction findTransactionByIdentifier(UUID identifier) throws TransactionNotFoundException;
    public Transaction[] toArray();
}
