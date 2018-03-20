package com.company.util;

public class Info {

    public static void print(Class<?> clazz, String message) {
        System.out.println(String.format("-> %s - %s", clazz.getSimpleName(), message));
    }
}