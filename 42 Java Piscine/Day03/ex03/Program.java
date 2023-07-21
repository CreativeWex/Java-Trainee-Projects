import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashMap;

public class Program {
    public static final String FILE_URLS_NAME = "ex03/files_urls.txt";
    public static ConcurrentLinkedQueue<String> uris = new ConcurrentLinkedQueue<>();
    public static HashMap<String, Integer> keys = new HashMap<>();

    private  static void validateArgs(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong arguments count");
            System.exit(-1);
        }

        if(!args[0].matches("--threadsCount=\\d+")) {
            System.err.println("Wrong arguments count");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        validateArgs(args);
        int threadsCount = Integer.parseInt(args[0].split("=")[1]);
        File file = new File(FILE_URLS_NAME);
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        try(Scanner scanner = new Scanner(file)) {
            int k = 1;
            while(scanner.hasNext()) {
                String str = scanner.nextLine();
                uris.add(str);
                keys.put(str, k);
                k++;
            }
            for (int i = 0; i < threadsCount; i++) {
                executorService.submit(new Download(i, uris, keys));
            }
            executorService.shutdown();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
