package com.company;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

    public static void main(String[] args) {
        TestClass tc1 = new TestClass(1, "Text", 2.0);
        System.out.println("Original: " + tc1);
        System.out.println("Saving to file...");
        saveFields(tc1, "fields.txt");
        System.out.println("Making changes...");
        tc1.setA(2);
        tc1.setB("Not Text");
        tc1.setC(3.0);
        tc1.setMap(null);
        System.out.println("Changed: " + tc1);
        System.out.println("Loading from file...");
        loadFields(tc1, "fields.txt");
        System.out.println("After loading: " + tc1);

    }

    public static void saveFields(TestClass testClass, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)))) {
            Deque<Object> deque = new ArrayDeque<>();
            Field[] fields = TestClass.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    if (!field.canAccess(testClass)) {
                        field.setAccessible(true);
                        deque.addLast(field.get(testClass));
                        field.setAccessible(false);
                    } else {
                        deque.addLast(field.get(testClass));
                    }
                }
            }
            oos.writeObject(deque);
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean loadFields(TestClass testClass, String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)))) {
            Deque<Object> deque = (Deque<Object>) ois.readObject();
            Field[] fields = TestClass.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    if (!field.canAccess(testClass)) {
                        field.setAccessible(true);
                        field.set(testClass, deque.pollFirst());
                        field.setAccessible(false);
                    } else {
                        field.set(testClass, deque.pollFirst());
                    }
                }
            }
            return true;
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
