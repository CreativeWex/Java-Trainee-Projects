public class Program {
    public static void main(String[] args) {
        Menu mainMenu = new Menu();
        try {
            if (args.length == 1) {
                String[] promptArgs = args[0].split("=");
                if (!promptArgs[0].equals("--current-folder")) {
                    System.out.println("Error: Wrong arguments\n");
                    System.exit(-1);
                } else {
                    promptArgs = args[0].split("--current-folder=");
                }
                if (promptArgs.length != 2) {
                    System.out.println("Error: Wrong arguments\n");
                    System.exit(-1);
                }
                mainMenu.setDirectory(promptArgs[1]);
            } else if (args.length != 0) {
                System.out.println("Error: Wrong arguments\n");
                System.exit(-1);
            }
            mainMenu.startMenu();
        }
        catch (HandlingWrongPath handlingWrongPath) {
            System.out.println("Error: Wrong initial directory");
            System.exit(-1);
        }
    }
}
