package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String json = stringFromFile("json.txt");
        Gson gson = new GsonBuilder().create();
        BussinessCard bc = gson.fromJson(json, BussinessCard.class);
        System.out.println(bc);

    }

    public static String stringFromFile(String fileName){
        String str;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            while ((str = br.readLine())!= null) {
                sb.append(str).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
