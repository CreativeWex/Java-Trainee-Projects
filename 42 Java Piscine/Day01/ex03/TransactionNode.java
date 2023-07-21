public class TransactionNode {
    private Transaction data;
    TransactionNode next;
    TransactionNode previous;

    TransactionNode(Transaction data) {
        this.data = data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public Transaction getData() {
        return data;
    }

    public void setNext(TransactionNode next) {
        this.next = next;
    }

    public TransactionNode getNext() {
        return next;
    }

    public void setPrevious(TransactionNode previous) {
        this.previous = previous;
    }

    public TransactionNode getPrevious() {
        return previous;
    }
}
