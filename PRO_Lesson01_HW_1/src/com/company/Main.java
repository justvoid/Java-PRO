package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        Class<?> cls = TestClass.class;
        Method[] meths = cls.getDeclaredMethods();
        for (Method meth : meths) {
            if (meth.isAnnotationPresent(Test.class)) {
                try {
                    TestClass testClass = new TestClass();
                    Test an = meth.getAnnotation(Test.class);
                    meth.invoke(testClass, an.a(), an.b());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
