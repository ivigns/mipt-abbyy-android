package com.github.ivigns.abbyy;

import java.util.Random;

public class Main {
    private static void printPoint(Point point) {
        System.out.print("(" + point.x + ", " + point.y + ")");
    }

    private static void printRect(Rect rect) {
        printPoint(rect.topLeft);
        System.out.print(", ");
        printPoint(rect.rightBottom);
        System.out.println();
    }

    public static void main(String[] args) {
        // First task
        Random rnd = new Random();
        int[][] array = Arrays.createArray(rnd);
        System.out.println("Before:");
        Arrays.printArray(array);
        Arrays.maxToFront(array);
        System.out.println("After:");
        Arrays.printArray(array);

        // Second task
        Point a = new Point(5, 4);
        System.out.print("Point a: ");
        printPoint(a);
        System.out.println();
        Point b = new Point(10, 8);
        System.out.print("Point b: ");
        printPoint(b);
        System.out.println();
        Rect rect = new Rect(a, b);
        System.out.print("Rect: ");
        printRect(rect);
        System.out.println("Perimeter: " + Util.getPerimeter(rect));
        System.out.println("Area: " + Util.getArea(rect));
        System.out.print("Scaled by 5: ");
        printRect(Util.scaleBy(rect, 5));
    }
}
