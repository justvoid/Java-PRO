package com.company;

public class Main {

    public static void main(String[] args) {
        TextContainer textCont = new TextContainer("Hello Reflection");
        Saver.textContainerToFile(textCont);
    }
}
