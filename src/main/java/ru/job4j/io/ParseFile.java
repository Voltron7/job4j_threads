package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(character -> character > 0);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
