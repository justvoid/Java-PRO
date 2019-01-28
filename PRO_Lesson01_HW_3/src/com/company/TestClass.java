package com.company;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class TestClass implements Serializable {
    @Save
    private int a;
    @Save
    private String b;
    private double c;
    @Save
    private  Map<Integer, String> map;

    public TestClass(int a, String b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.map = Collections.singletonMap(a, b);
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return b;
    }

    public void setC(double c) {
        this.c = c;
    }
    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", map=" + map +
                '}';
    }
}
