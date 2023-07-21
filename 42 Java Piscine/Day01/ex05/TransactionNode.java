public class TransactionNode {
    Transaction transaction;
    TransactionNode next;
    TransactionNode back;

    private TransactionNode() {

    }

    TransactionNode(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionNode getNext() {
        return next;
    }

    public void setNext(TransactionNode next) {
        this.next = next;
    }

    public TransactionNode getBack() {
        return back;
    }

    public void setBack(TransactionNode back) {
        this.back = back;
    }
}
