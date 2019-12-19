package com.github.ivigns.abbyy;

import java.util.Collections;
import java.util.Random;

public class Arrays {
    public static int[][] createArray(Random rnd) {
        int[][] array = new int[6][7];
        for (int[] line : array) {
            for (int i = 0; i < line.length; ++i) {
                line[i] = rnd.nextInt(10);
            }
        }
        return array;
    }

    public static void printArray(int[][] array) {
        for (int[] line : array) {
            for (Integer num : line) {
                System.out.print(num.toString() + " ");
            }
            System.out.println();
        }
    }

    public static void maxToFront(int[][] array) {
        for (int[] line : array) {
            int maxValIdx = 0;
            for (int i = 0; i < line.length; ++i) {
                if (line[i] > line[maxValIdx]) {
                    maxValIdx = i;
                }
            }
            int tmp = line[0];
            line[0] = line[maxValIdx];
            line[maxValIdx] = tmp;
        }
    }
}
