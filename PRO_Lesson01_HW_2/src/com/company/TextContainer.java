package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@SaveTo("file.txt")
public class TextContainer {
    private String text = "Default String";

    public TextContainer(String text) {
        this.text = text;
    }

    public TextContainer() {
    }

    @SaveBy
    public void save(String fileName) {
        try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            pw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
