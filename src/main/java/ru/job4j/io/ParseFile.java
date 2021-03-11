package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return getContent(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(data -> data < 0x80);
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String getContent(Predicate<Integer> cond) {
        StringBuilder output = new StringBuilder();
        try (FileInputStream reader = new FileInputStream(file)) {
            int data;
            while ((data = reader.read()) > 0) {
                if (cond.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

}