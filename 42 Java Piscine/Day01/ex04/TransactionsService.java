import java.util.UUID;

public class TransactionsService {
    private UsersList userDataBase;

    {
        userDataBase = new UsersArrayList();
    }

    TransactionsService() {}

    public void addUser(User user) {
        userDataBase.addUser(user);
    }

    public int retrieveUserBalance(int userIdentifier) {
        User user = userDataBase.findByIdentifier(userIdentifier);
        return user.getBalance();
    }

    public Transaction [] retrieveUserTransaction(int userIdentifier) {
        User user = userDataBase.findByIdentifier(userIdentifier);
        return user.getTransactionsList().toArray();
    }

    public void removeTransactionFromUser(int userIdentifier, UUID transactionIdentifier) {
        User user = userDataBase.findByIdentifier(userIdentifier);
        user.getTransactionsList().removeTransactionByIdentifier(transactionIdentifier);
    }

    public void transferTransaction(int senderIdentifier, int recipientIdentifier, int transferAmount) throws IllegalTransactionException {
        User sender = userDataBase.findByIdentifier(senderIdentifier);
        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException();
        }
        User recipient = userDataBase.findByIdentifier(recipientIdentifier);
        Transaction senderTransaction = new Transaction(recipientIdentifier,
                senderIdentifier, Transaction.transferType.OUTCOME, transferAmount);
        Transaction recipientTransaction = senderTransaction.createMirrorCopy();
        sender.getTransactionsList().addTransaction(senderTransaction);
        sender.setBalance(sender.getBalance() - transferAmount);
        recipient.getTransactionsList().addTransaction(recipientTransaction);
        recipient.setBalance(recipient.getBalance() + transferAmount);
    }

    public Transaction [] checkValidityOfTransactions () {
        int unpairedTransactionsCounter = 0;
        for (int userCounter = 0; userCounter < userDataBase.getUserAmount(); ++userCounter) {
            unpairedTransactionsCounter += countUserUnpairedTransactions(userDataBase.findByIndex(userCounter));
            }
        Transaction [] unpairedTransactionsArray = new Transaction[unpairedTransactionsCounter];
        int position = 0;
        for (int userCounter = 0; userCounter < userDataBase.getUserAmount(); ++userCounter) {
            position = addTransactionsFromUserToArrayFromPosition(userDataBase.findByIndex(userCounter),
                    unpairedTransactionsArray, position);
        }
        return unpairedTransactionsArray;
    }

    private int addTransactionsFromUserToArrayFromPosition(User user, Transaction [] transactions, int position) {
        Transaction[] userTransactionArray = user.getTransactionsList().toArray();
        for (int userTransactionsCounter = 0; userTransactionsCounter < userTransactionArray.length; ++userTransactionsCounter) {
            Transaction[] partnerTransactionArray;
            if(userTransactionArray[userTransactionsCounter].getRecipient() == user.getIdentifier()) {
                partnerTransactionArray =
                        userDataBase.findByIdentifier(userTransactionArray[userTransactionsCounter].getSender())
                                .getTransactionsList().toArray();
            } else {
                partnerTransactionArray = userDataBase.findByIdentifier(userTransactionArray[userTransactionsCounter].getRecipient())
                        .getTransactionsList().toArray();
            }
            boolean notFound = true;
            for (int partnerCounter = 0; partnerCounter < partnerTransactionArray.length; ++partnerCounter) {
                if (userTransactionArray[userTransactionsCounter].getIdentifier()
                        == partnerTransactionArray[partnerCounter].getIdentifier()) {
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                transactions[position] = userTransactionArray[userTransactionsCounter];
                ++position;
            }
        }
        return (position);
    }

    private int countUserUnpairedTransactions(User user) {
        int unpairedTransactionsCounter = 0;
        Transaction[] userTransactionArray = user.getTransactionsList().toArray();
        for (int userTransactionsCounter = 0; userTransactionsCounter < userTransactionArray.length; ++userTransactionsCounter) {
            Transaction[] partnerTransactionArray;
            if(userTransactionArray[userTransactionsCounter].getRecipient() == user.getIdentifier()) {
                partnerTransactionArray =
                        userDataBase.findByIdentifier(userTransactionArray[userTransactionsCounter].getSender())
                                .getTransactionsList().toArray();
            } else {
                partnerTransactionArray = userDataBase.findByIdentifier(userTransactionArray[userTransactionsCounter].getRecipient())
                                .getTransactionsList().toArray();
            }
            boolean notFound = true;
            for (int partnerCounter = 0; partnerCounter < partnerTransactionArray.length; ++partnerCounter) {
                if (userTransactionArray[userTransactionsCounter].getIdentifier()
                        == partnerTransactionArray[partnerCounter].getIdentifier()) {
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                ++unpairedTransactionsCounter;
            }
        }
        return unpairedTransactionsCounter;
    }
}

