import java.io.*;
import java.util.*;

public class Program {

    private static final HashMap<String, Integer> firstFileWords = new HashMap<>();
    private static final HashMap<String, Integer> secondFileWords = new HashMap<>();
    private static TreeSet<String> sortedDictionary;
    private static final String DICTIONARY_FILE_NAME = "dictionary.txt";

    private static  ArrayList<Integer> frequencyOfOccurrence(HashMap<String, Integer> map) {
        ArrayList<Integer> arrayList = new ArrayList<>(sortedDictionary.size());
        for (String word : sortedDictionary) {
            arrayList.add(map.getOrDefault(word, 0));
        }
        return arrayList;
    }

    private static double calculateSimilarity() {
        ArrayList<Integer> firstFrequency = frequencyOfOccurrence(firstFileWords);
        ArrayList<Integer> secondFrequency = frequencyOfOccurrence(secondFileWords);

        long sumA = 0;
        long sumB = 0;
        for (Integer integer : firstFrequency) {
            sumA += (long) integer * integer;
        }
        for (Integer integer : secondFrequency) {
            sumB += (long) integer * integer;
        }
        double denominator = Math.sqrt(sumA) * Math.sqrt(sumB);
        if (denominator == 0) {
            System.err.println("denominator equals zero");
            System.exit(-1);
        }
        long numerator = 0;
        for (int i = 0; i < firstFrequency.size(); i++) {
            numerator += (long) firstFrequency.get(i) * secondFrequency.get(i);
        }
        return numerator/denominator;
    }

    private static void parseInputValues(FileReader fileReader, HashMap<String, Integer> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.split("\\W"); {
                for (String word : words) {
                    if (word.equals("")) {
                        continue;
                    }
                    if (!map.containsKey(word)) {
                        map.put(word, 1);
                    } else {
                        map.put(word, map.get(word) + 1);
                    }
                }
            }
        }
        bufferedReader.close();
    }

    private static void createDictionary() throws IOException {
        FileWriter fileWriter = new FileWriter(DICTIONARY_FILE_NAME);
        HashSet<String> dictionary = new HashSet<>();
        for (Map.Entry entry : firstFileWords.entrySet()) {
            dictionary.add(entry.getKey().toString());
        }
        for (Map.Entry entry : secondFileWords.entrySet()) {
            dictionary.add(entry.getKey().toString());
        }
        sortedDictionary = new TreeSet<>(dictionary);
        for (String word : sortedDictionary) {
            fileWriter.write(word + " ");
        }
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Error: Wrong arguments amount");
            System.exit(-1);
        }
        FileReader firstInputFile = new FileReader(args[0]);
        FileReader secondInputFile = new FileReader(args[1]);
        parseInputValues(firstInputFile, firstFileWords);
        parseInputValues(secondInputFile, secondFileWords);
        createDictionary();
        System.out.printf("Similarity =  %.2f", calculateSimilarity());
    }
}
