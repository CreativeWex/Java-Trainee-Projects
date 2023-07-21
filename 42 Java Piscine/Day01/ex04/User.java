public class User {
    private final Integer identifier;
    private String name;
    private int balance;
    private TransactionsList transactionsList;
    {
        identifier = UserIdsGenerator.getInstance().generateId();
        transactionsList = new TransactionsLinkedList();
    }

    private User () {
    }

    public User (String name, int balance) {

        this.name = name;
        this.balance = balance;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance >= 0) {
            this.balance = balance;
        }
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public void printInConsole() {
        System.out.println("ID->" + this.getIdentifier()
                + " NAME->" + this.getName()
                + " BALANCE->" + this.getBalance());
    }
}
