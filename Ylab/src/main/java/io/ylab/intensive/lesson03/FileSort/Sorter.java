/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 19/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

package io.ylab.intensive.lesson03.FileSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import java.util.*;

public class Sorter {
    private static final int BLOCK_SIZE = 10000;

    private record NumberWithReader(Long number, BufferedReader reader) implements Comparable<NumberWithReader> {
        public int compareTo(NumberWithReader other) {
            return number.compareTo(other.number);
        }
    }

    public File sortFile(File dataFile) throws IOException {
        List<File> sortedBlocks = createSortedBlocks(dataFile);
        return mergeSortedBlocks(dataFile, sortedBlocks);
    }

    private List<File> createSortedBlocks(File dataFile) throws IOException {
        List<Long> blockList = new ArrayList<>(BLOCK_SIZE);
        List<File> sortedBlockFiles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                blockList.add(Long.parseLong(line));
                if (blockList.size() == BLOCK_SIZE) {
                    Collections.sort(blockList);
                    sortedBlockFiles.add(createSortedBlocksFile(blockList));
                    blockList.clear();
                }
            }
        }
        if (!blockList.isEmpty()) {
            Collections.sort(blockList);
            sortedBlockFiles.add(createSortedBlocksFile(blockList));
        }
        return sortedBlockFiles;
    }

    private File createSortedBlocksFile(List<Long> blockList) throws IOException {
        File file = File.createTempFile("blocks", ".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Long number : blockList) {
                writer.write(number.toString());
                writer.newLine();
            }
        }
        return file;
    }

    private File mergeSortedBlocks(File dataFile, List<File> sortedBlockFiles) throws IOException {
        Queue<BufferedReader> readers = new LinkedList<>();
        for (File blockFile : sortedBlockFiles) {
            readers.offer(new BufferedReader(new FileReader(blockFile)));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            PriorityQueue<NumberWithReader> heap = new PriorityQueue<>();
            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    heap.offer(new NumberWithReader(Long.parseLong(line), reader));
                }
            }
            while (!heap.isEmpty()) {
                NumberWithReader nwr = heap.poll();
                writer.write(nwr.number.toString());
                writer.newLine();
                BufferedReader reader = nwr.reader;
                String line = reader.readLine();
                if (line != null) {
                    heap.offer(new NumberWithReader(Long.parseLong(line), reader));
                } else {
                    reader.close();
                }
            }
        }
        return dataFile;
    }
}