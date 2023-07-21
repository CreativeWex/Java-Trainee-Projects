import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private TransactionNode first;
    @Override
    public void addTransaction(Transaction newTransaction) {
        TransactionNode newTransactionNode = new TransactionNode(newTransaction);

        if (first != null) {
            first.setBack(newTransactionNode);
        }
        newTransactionNode.setBack(null);
        newTransactionNode.setNext(first);
        first = newTransactionNode;
    }

    @Override
    public void removeTransactionByIdentifier(UUID identifier) throws TransactionNotFoundException {
        TransactionNode currentTransaction = first;
        while (currentTransaction != null) {
            if (currentTransaction.getTransaction().getIdentifier() == identifier) {
                if (currentTransaction.getBack() != null) {
                    currentTransaction.getBack().setNext(currentTransaction.getNext());
                } else {
                    first = currentTransaction.getNext();
                }
                if (currentTransaction.getNext() != null) {
                    currentTransaction.getNext().setBack(currentTransaction.getBack());
                }
                return;
            }
            currentTransaction = currentTransaction.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] toArray() {
        TransactionNode currentTransaction = first;
        int counter = 0;
        while (currentTransaction != null) {
            ++counter;
            currentTransaction = currentTransaction.getNext();
        }
        Transaction[] returnArray = new Transaction[counter];
        counter = 0;
        currentTransaction = first;
        for (; counter < returnArray.length; ++counter) {
            returnArray[counter] = currentTransaction.getTransaction();
            currentTransaction = currentTransaction.getNext();
        }
        return returnArray;
    }
}
