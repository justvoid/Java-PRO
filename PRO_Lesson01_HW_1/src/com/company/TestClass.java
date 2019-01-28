package com.company;


public class TestClass {
    @Test(a = 2, b = 5)
    public void test(int a, int b){
        System.out.println(a);
        System.out.println(b);
    }
}
