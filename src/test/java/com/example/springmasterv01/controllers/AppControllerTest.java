package com.example.springmasterv01.controllers;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    @BeforeAll
    static void setUp() {
        System.out.println("Before all tests");
    }

    @Test
    void testToString() {
        int[] numbers = {9, 90, 40, 34};
        boolean val = search(numbers, 9);
        assertTrue(val);
    }

    public boolean search(int[] numbers , int number){
        for (int j : numbers) {
            if (j == number) {
                return true;
            }
        }
        return false;
    }
}