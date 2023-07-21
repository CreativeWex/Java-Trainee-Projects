import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;


class Download implements Runnable {
    private Integer index;
    private ConcurrentLinkedQueue<String> uris;
    private HashMap<String, Integer> keys;

    Download(int index, ConcurrentLinkedQueue<String> uris, HashMap<String, Integer> keys) {
        this.uris = uris;
        this.keys = keys;
        this.index = index + 1;
    }
    @Override
    public void run() {
        while (!uris.isEmpty()) {
            String url = uris.poll();
            Integer fileNumber = keys.get(url);
            String fileName = url.substring(url.lastIndexOf('/') + 1);

            System.out.printf("Thread-%d start download file number %d\n", index, fileNumber);
            try (InputStream in = URI.create(url).toURL().openStream()) {
                Files.copy(in, Paths.get(fileName));
            } catch (FileAlreadyExistsException e) {
                System.err.println("File already exist");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Thread-%d finish download file number %d\n", index, fileNumber);
        }
    }
}