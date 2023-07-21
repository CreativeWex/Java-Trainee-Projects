import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Menu {
    private String directory;
    private Scanner console;
    {
        console = new Scanner(System.in);
    }

    public Menu() {

    }

    public void setDirectory(String directory) throws HandlingWrongPath {

        this.directory = directory;
        if(!Files.exists(Paths.get(this.directory)) || !Files.isDirectory(Paths.get(this.directory))) {
            throw new HandlingWrongPath();
        }
    }

    private void mv(String fromPath, String toPath) throws HandlingWrongPath {
        Path current = Paths.get(this.directory);
        Path from = current.resolve(Paths.get(fromPath));
        Path to = current.resolve(Paths.get(toPath));
        if (!Files.exists(from) || Files.isDirectory(from)) {
            throw new HandlingWrongPath();
        }
        if(to.getFileName().equals(to)) {
            File oldName = new File(fromPath);
            File newName = new File(toPath);
            oldName.renameTo(newName);
        } else {
            try {
                Files.move(from, to);
            }
            catch (java.io.IOException ioException) {
                System.out.println("Error: Can't move file");
            }
        }
    }
    private void ls() {
        File currentDir = new File(directory);
        File[] directoryFiles = currentDir.listFiles();
        for(File file : directoryFiles) {
            System.out.println(file.getName() + " " + getFileSize(file) / 1024 + " KB");
        }
    }

    private void cd(String directory) throws HandlingWrongPath {
        Path current = Paths.get(this.directory);
        Path destination = current.resolve(Paths.get(directory));
        if (!Files.exists(destination.toAbsolutePath()) || !Files.isDirectory(destination)) {
            throw new HandlingWrongPath();
        } else {
                this.directory = destination.toAbsolutePath().toString();
        }
    }
    public void startMenu() {
        String currLine = console.nextLine();
        while (!currLine.equals("exit")) {
            try
            {
                String args[] = currLine.split(" ");
                switch (args.length) {
                    case 1:
                        if (args[0].equals("ls")) {
                            ls();
                        }
                        break;
                    case 2:
                        if (args[0].equals("cd")) {
                            cd(args[1]);
                            System.out.println("Done!");
                        }
                        break;
                    case 3:
                        if (args[0].equals("mv")) {
                            mv(args[1], args[2]);
                            System.out.println("Done!");
                        }
                        break;
                }
            }
            catch (HandlingWrongPath handlingWrongPath) {
                System.out.println("Error: Wrong path");
            }
            currLine = console.nextLine();
        }
        System.exit(-1);
    }

    private static long getFileSize(File file) {
        if (file.isFile()) {
            return file.length();
        }
        long fileSize = 0;
        File[] directoryFiles = file.listFiles();
        for(File currentFile : directoryFiles) {
            fileSize += getFileSize(currentFile);
        }
        return fileSize;
    }
}
