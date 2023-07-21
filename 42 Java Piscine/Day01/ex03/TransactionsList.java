import java.util.UUID;

public class TransactionsList implements TransactionsLinkedList{
    private TransactionNode head;

    @Override
    public void add(Transaction newTransaction) {
        TransactionNode newNode = new TransactionNode(newTransaction);
        if (head != null) {
            head.setPrevious(newNode);
        }
        newNode.setPrevious(null);
        newNode.setNext(head);
        head = newNode;
    }

    @Override
    public void removeById(UUID uuid) {
        TransactionNode currentTransaction = head;
        while (currentTransaction != null) {
            if (currentTransaction.getData().getUuid() == uuid) {
                if (currentTransaction.getPrevious() != null) {
                    currentTransaction.getPrevious().setNext(currentTransaction.getNext());
                } else {
                    head = currentTransaction.getNext();
                }
                if (currentTransaction.getNext() != null) {
                    currentTransaction.getNext().setPrevious(currentTransaction.getPrevious());
                }
                return;
            }
            currentTransaction = currentTransaction.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] toArray() {
        TransactionNode currentTransaction = head;
        int len = 0;
        while (currentTransaction != null) {
            ++len;
            currentTransaction = currentTransaction.getNext();
        }
        Transaction[] returnArray = new Transaction[len];
        len = 0;
        currentTransaction = head;
        for (; len < returnArray.length; ++len) {
            returnArray[len] = currentTransaction.getData();
            currentTransaction = currentTransaction.getNext();
        }
        return returnArray;
    }

    public void display() {
        Transaction[] arr = this.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].toString());
        }
    }
}
