package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Saver {
    public static void textContainerToFile(TextContainer tc) {
        Class<?> cl = tc.getClass();
        String fileName = cl.getAnnotation(SaveTo.class).value();
        Method[] meths = cl.getMethods();
        for (Method meth : meths){
            if (meth.isAnnotationPresent(SaveBy.class)) {
                try {
                    meth.invoke(tc, fileName);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
