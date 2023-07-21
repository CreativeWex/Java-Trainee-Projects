import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private boolean devMode;
    private TransactionsService transactionsService;
    private Scanner console;
    {
        devMode = false;
        transactionsService = new TransactionsService();
        console = new Scanner(System.in);
    }
    public Menu() {

    }

    public void showMenu() {
        int optionCounter = 1;
        System.out.println(optionCounter++ + ". Add a user");
        System.out.println(optionCounter++ + ". View user balances");
        System.out.println(optionCounter++ + ". Perform a transfer");
        System.out.println(optionCounter++ + ". View all transactions for a specific user");
        if (devMode) {
            System.out.println(optionCounter++ + ". DEV - remove a transfer by ID");
            System.out.println(optionCounter++ + ". DEV - check transfer validity");
        }
        System.out.println(optionCounter + ". Finish execution");
    }

    private void checkTransferValidity () {
        System.out.println("Check results:");
        Transaction [] unpairedTransactions = transactionsService.checkValidityOfTransactions();
        String string;
        User first;
        User second;
        for (int counter = 0; counter < unpairedTransactions.length; ++counter) {
            String direction;
            if (unpairedTransactions[counter].getTransferType() == Transaction.transferType.OUTCOME) {
                first = transactionsService.getUserDataBase().findByIdentifier(unpairedTransactions[counter].getSender());
                second = transactionsService.getUserDataBase().findByIdentifier(unpairedTransactions[counter].getRecipient());
                direction = " to ";
            } else {
                first = transactionsService.getUserDataBase().findByIdentifier(unpairedTransactions[counter].getRecipient());
                second = transactionsService.getUserDataBase().findByIdentifier(unpairedTransactions[counter].getSender());
                direction = " from ";
            }
            string = first.getName() + "(id = " + first.getIdentifier()
                    + ") has an unacknowledged transfer id = "
                    + unpairedTransactions[counter].getIdentifier()
                    + direction + second.getName() + "(id = " + second.getIdentifier()
                    + ") for " + unpairedTransactions[counter].getTransferAmount();
            System.out.println(string);
        }
    }

    private void removeTransferByIdentifier() {
        String temp = new String("");
        Integer userIdentifier = -1;
        UUID transactionIdentifier = UUID.randomUUID();
        boolean success = false;
        System.out.println("Enter a user ID and a transfer ID");
        while (!success) {
            temp = console.nextLine();
            while (!isAnyMeaningSymbol(temp)) {
                temp = console.nextLine();
            }
            String[] args = temp.split(" ");
            if (args.length != 2) {
                System.out.println("Wrong argument amount");
                continue;
            }
            try {
                userIdentifier = Integer.parseInt(args[0]);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("User ID is not a numeric");
                continue;
            }
            try {
                transactionIdentifier = UUID.fromString(args[1]);
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("Transfer ID in bad format");
                continue;
            }
            if (userIdentifier < 0) {
                System.out.println("User ID is negative");
                continue;
            }
            success = true;
        }
        try {
            Transaction transaction = transactionsService.getUserDataBase()
                    .findByIdentifier(userIdentifier).getTransactionsList()
                    .findTransactionByIdentifier(transactionIdentifier);
            System.out.println("Transfer " + transactionDetails(transaction)
                    + transaction.getTransferAmount() + " removed");
            transactionsService.removeTransactionFromUser(userIdentifier, transactionIdentifier);
        }
        catch (UserNotFoundException userNotFoundException) {
            System.out.println("User is not found");
        }
        catch (TransactionNotFoundException transactionNotFoundException) {
            System.out.println("Transaction is not found");
        }
    }

    private void viewAllTransactionForUser() {
        String temp;
        Integer idetifier = -1;
        System.out.println("Enter a user ID");
        while (true) {
            temp = console.nextLine();
            while (!isAnyMeaningSymbol(temp)) {
                temp = console.nextLine();
            }
            String[] args = temp.split(" ");
            if (args.length != 1) {
                System.out.println("Wrong argument amount");
                continue;
            }
            try {
                idetifier = Integer.parseInt(args[0]);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID is not a numeric");
                continue;
            }
            if (idetifier < 0) {
                System.out.println("ID is negative");
                continue;
            }
            try {
                printTransactionArray(transactionsService.retrieveUserTransaction(idetifier));
            }
            catch (UserNotFoundException userNotFoundException) {
                System.out.println("User not found");
            }
            return;
        }
    }

    private void performTransfer() {
        String temp;
        int sender = -1;
        int recipient = -1;
        int transferAmount = -1;
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        while (true) {
            temp = console.nextLine();
            while (!isAnyMeaningSymbol(temp)) {
                temp = console.nextLine();
            }
            String[] args = temp.split(" ");
            if (args.length != 3) {
                System.out.println("Wrong argument amount");
                continue;
            }
            try {
                sender = Integer.parseInt(args[0]);
                recipient = Integer.parseInt(args[1]);
                transferAmount = Integer.parseInt(args[2]);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("One of the arguments is not a numeric");
                continue;
            }
            if (sender == recipient) {
                System.out.println("User can't made transaction to himself");
                continue;
            }
            if (transferAmount < 0) {
                System.out.println("Transfer amount is negative");
                continue;
            }
            try {
                transactionsService.transferTransaction(sender, recipient, transferAmount);
            }
            catch (UserNotFoundException userNotFoundException) {
                System.out.println("User not found");
                return;
            }
            catch (IllegalTransactionException illegalTransactionException) {
                System.out.println("Not enough money");
                return;
            }
            System.out.println("The transfer is completed");
            return;
        }
    }
    private void vievUserBalances() {
        String temp;
        Integer idetifier = -1;
        System.out.println("Enter a user ID");
        while (true) {
            temp = console.nextLine();
            while (!isAnyMeaningSymbol(temp)) {
                temp = console.nextLine();
            }
            String[] args = temp.split(" ");
            if (args.length != 1) {
                System.out.println("Wrong argument amount");
                continue;
            }
            try {
                idetifier = Integer.parseInt(args[0]);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID is not a numeric");
                continue;
            }
            if (idetifier < 0) {
                System.out.println("ID is negative");
                continue;
            }
            try {
                System.out.println(transactionsService.getUserDataBase().findByIdentifier(idetifier).getName()
                        + " - " + transactionsService.retrieveUserBalance(idetifier));
            }
            catch (UserNotFoundException userNotFoundException) {
                System.out.println("User not found");
            }
            return;
        }
    }
    private void addNewUser() {
        String temp = new String("");
        Integer balance = 0;
        boolean success = false;
        System.out.println("Enter a user name and a balance");
        while (!success) {
            temp = console.nextLine();
            while (!isAnyMeaningSymbol(temp)) {
                temp = console.nextLine();
            }
            String[] args = temp.split(" ");
            if (args.length != 2) {
                System.out.println("Wrong argument amount");
                continue;
            }
            try {
                balance = Integer.parseInt(args[1]);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Balance is not a numeric");
                continue;
            }
            if (balance < 0) {
                System.out.println("Balance is negative");
                continue;
            }
            temp = args[0];
            success = true;
        }
        User user = new User(temp, balance);
        transactionsService.addUser(user);
        System.out.println("User with id = " + user.getIdentifier() + " is added");
    }

    public void handelEnteredMenuOption(int menuOption) {
        int innerMenuOption = menuOption;
        if (!devMode && innerMenuOption > 4) {
            innerMenuOption += 2;
        }
        switch (innerMenuOption) {
            case 1:
                addNewUser();
                break;
            case 2:
                vievUserBalances();
                break;
            case 3:
                performTransfer();
                break;
            case 4:
                viewAllTransactionForUser();
                break;
            case 5:
                removeTransferByIdentifier();
                break;
            case 6:
                checkTransferValidity();
                break;
            case 7:
                System.exit(0);
            default:
                System.out.println(menuOption + " is not a option in the menu.");
        }
    }

    public void startMenu() {
        while (true) {
            this.showMenu();
            while (!console.hasNextInt()) {
                if (isAnyMeaningSymbol(console.nextLine())) {
                    System.out.println("Enter a numeric.");
                }
            }
            this.handelEnteredMenuOption(console.nextInt());
            System.out.println("---------------------------------------------------------");
        }

    }

    private boolean isAnyMeaningSymbol(String string) {
        char [] stringArray = string.toCharArray();
        for (char c : stringArray) {
            if (c != ' '
                    && c != '\t'
                    && c != '\r'
                    && c != '\n') {
                return true;
            }
        }
        return false;
    }
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public void printTransactionArray(Transaction[] transactionArray) {
        if (transactionArray.length == 0)
            System.out.println("Empty Array\n");
        String string;
        for (int counter = 0; counter < transactionArray.length; ++counter) {
            string = transactionDetails(transactionArray[counter])
                    + transactionArray[counter].getTransferType().getSign()
                    + transactionArray[counter].getTransferAmount()
                    + " with id = " + transactionArray[counter].getIdentifier();
            System.out.println(string);
        }
    }

    public void printUnpairedTransaction(Transaction transaction) {

    }

    String transactionDetails(Transaction transaction) {
        String string = new String("");
        User partner;
        if(transaction.getTransferType() == Transaction.transferType.OUTCOME) {
            string += "To ";
            partner = transactionsService.getUserDataBase().findByIdentifier(transaction.getRecipient());
        }
        else {
            string += "From ";
            partner =  transactionsService.getUserDataBase().findByIdentifier(transaction.getSender());
        }
        string += partner.getName();
        string += "(id = " + partner.getIdentifier() + ") ";
        return string;
    }
}
