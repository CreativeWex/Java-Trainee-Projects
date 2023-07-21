import java.util.UUID;

public class Transaction {
    enum transferType {
        INCOME("Income", "+"), OUTCOME("Outcome", "-");

        private String name;
        private String sign;

        transferType(String text, String sign) {
            this.name = text;
            this.sign = sign;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getSign() {
            return sign;
        }
    }

    private final UUID identifier;
    private Integer recipient;
    private Integer sender;
    private transferType transferType;
    private Integer transferAmount;

    private Transaction() {
        identifier = UUID.randomUUID();
    }
    private Transaction(Transaction original) {
        identifier = original.identifier;
        this.recipient = original.recipient;
        this.sender = original.sender;
        this.transferType = original.transferType;
        this.transferAmount = original.transferAmount;
    }


    public Transaction(int recipient, int sender, transferType transferType, int transferAmount) {
        identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferType = transferType;
        this.transferAmount = transferAmount;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public Transaction.transferType getTransferType() {
        return transferType;
    }

    public void setTransferType(Transaction.transferType transferType) {
        this.transferType = transferType;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        if ((transferAmount > 0 && transferType == transferType.INCOME)
            || (transferAmount < 0 && transferType == transferType.OUTCOME)){
            this.transferAmount = transferAmount;
        }
    }

    public Transaction createMirrorCopy() {
        Transaction result = new Transaction(this);
        if (this.transferType == transferType.INCOME) {
            result.transferType = transferType.OUTCOME;
        } else {
            result.transferType = transferType.INCOME;
        }
        return result;
    }

    public void printInConsole() {
        System.out.println("ID->" + this.getIdentifier()
                + " TYPE->" + this.transferType.toString()
                + " RECIPIENT->" + this.getRecipient()
                + " SENDER->" + this.getSender()
                + " TRANSFER AMOUNT->" + this.transferType.getSign() + this.getTransferAmount());
    }
}
