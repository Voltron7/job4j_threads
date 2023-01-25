package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.util.NoSuchElementException;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String file;
    private final long pause = 1000;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadingSpeed = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadingSpeed += bytesRead;
                if (downloadingSpeed >= speed) {
                    long currentTime = System.currentTimeMillis();
                    long downloadingTime = currentTime - startTime;
                    if (downloadingTime < pause) {
                        Thread.sleep(pause - downloadingTime);
                    }
                    downloadingSpeed = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length < 1) {
            throw new NoSuchElementException("Arguments doesn't exist!");
        }
        if (args[0].isEmpty()) {
            throw new IllegalArgumentException("URL doesn't exist!");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Speed must be more than 0!");
        }
        if (!args[2].endsWith(".xml")) {
            throw new IllegalArgumentException("File doesn't exist!");
        }
    }

    public static void main(String[] args) {
        try {
            validate(args);
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String file = args[2];
            Thread wget = new Thread(new Wget(url, speed, file));
            wget.start();
            wget.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


