public class Program {
    public static void main(String[] args) {
        TransactionsService mainService = new TransactionsService();
        User bobaFett = new User ("Boba Fett", 678);
        User darthVader = new User ("Darth Vaider", 5683);
        User hanSolo = new User ("Han Solo", 235);
        User emperorPalpatine = new User ("Darth Sidius", 9674521);
        mainService.addUser(bobaFett);
        mainService.addUser(darthVader);
        mainService.addUser(hanSolo);
        mainService.addUser(emperorPalpatine);
        System.out.println("USERS BEFORE ONE TRANSACTION:");
        bobaFett.printInConsole();
        darthVader.printInConsole();

        mainService.transferTransaction(darthVader.getIdentifier(), bobaFett.getIdentifier(), 4000);
        System.out.println("\nUSERS BALANCE AFTER ONE TRANSACTION:");
        System.out.println(mainService.retrieveUserBalance(bobaFett.getIdentifier()) + " ");
        System.out.println(mainService.retrieveUserBalance(darthVader.getIdentifier()) + " ");
        System.out.println("\nSENDER TRANSACTIONS AFTER ONE TRANSACTION:");
        printTransactionArray(mainService.retrieveUserTransaction(bobaFett.getIdentifier()));
        System.out.println("\nRECIPIENT TRANSACTIONS AFTER ONE TRANSACTION:");
        printTransactionArray(mainService.retrieveUserTransaction(darthVader.getIdentifier()));

        System.out.println("\nTRANSACTIONS WITH NO MONEY:");
        try {
            mainService.transferTransaction(hanSolo.getIdentifier(), bobaFett.getIdentifier(), 4000);
        } catch (IllegalTransactionException illegalTransactionException) {
            System.out.println("EXCEPTION MESSAGE: " + illegalTransactionException);
        }

        System.out.println("\nUSERS BALANCE BEFORE MULTIPLY TRANSACTIONS:");
        bobaFett.printInConsole();
        darthVader.printInConsole();
        emperorPalpatine.printInConsole();
        hanSolo.printInConsole();
        for (int counter = 0; counter < 2; ++counter) {
            mainService.transferTransaction(emperorPalpatine.getIdentifier(), bobaFett.getIdentifier(), 23 * (counter + 3));
            mainService.transferTransaction(emperorPalpatine.getIdentifier(), darthVader.getIdentifier(), 32 * (counter + 2));
            mainService.transferTransaction(emperorPalpatine.getIdentifier(), hanSolo.getIdentifier(), 12 * (counter + 1));
            mainService.transferTransaction(bobaFett.getIdentifier(), darthVader.getIdentifier(), 58 * (counter + 3));
            mainService.transferTransaction(hanSolo.getIdentifier(), emperorPalpatine.getIdentifier(), 2 * (counter + 5));
        }
        System.out.println("\n" + bobaFett.getName() + " TRANSACTIONS:");
        printTransactionArray(mainService.retrieveUserTransaction(bobaFett.getIdentifier()));
        System.out.println("\n" + darthVader.getName() + " TRANSACTIONS:");
        printTransactionArray(mainService.retrieveUserTransaction(darthVader.getIdentifier()));
        System.out.println("\n" + hanSolo.getName() + " TRANSACTIONS:");
        printTransactionArray(mainService.retrieveUserTransaction(hanSolo.getIdentifier()));
        System.out.println("\n" + emperorPalpatine.getName() + " TRANSACTIONS:");
        printTransactionArray(mainService.retrieveUserTransaction(emperorPalpatine.getIdentifier()));

        System.out.println("\nFOLLOWING TRANSITIONS WILL BE \"LOST\":");
        int transactionIndex = 3;
        User currUser = bobaFett;
        mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].printInConsole();
        mainService.removeTransactionFromUser(currUser.getIdentifier(), mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].getIdentifier());
        transactionIndex = 2;
        currUser = darthVader;
        mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].printInConsole();
        mainService.removeTransactionFromUser(currUser.getIdentifier(), mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].getIdentifier());
        transactionIndex = 1;
        currUser = hanSolo;
        mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].printInConsole();
        mainService.removeTransactionFromUser(currUser.getIdentifier(), mainService.retrieveUserTransaction(currUser.getIdentifier())[transactionIndex].getIdentifier());
        System.out.println("\nUNPAIR TRANSITIONS:");
        printTransactionArray(mainService.checkValidityOfTransactions());
    }

    public static void printTransactionArray(Transaction[] transactionArray) {
        if (transactionArray.length == 0)
            System.out.println("Empty Array\n");
        for (int counter = 0; counter < transactionArray.length; ++counter) {
            transactionArray[counter].printInConsole();
        }
    }
}
