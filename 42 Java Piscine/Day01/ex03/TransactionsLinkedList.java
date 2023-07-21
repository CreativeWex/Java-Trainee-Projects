import java.util.UUID;

public interface TransactionsLinkedList {
    public void add(Transaction transaction);
    public void removeById(UUID uuid);
    public Transaction[] toArray();

}
