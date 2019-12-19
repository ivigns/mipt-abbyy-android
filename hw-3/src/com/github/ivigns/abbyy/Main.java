package com.github.ivigns.abbyy;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            FrequencyCounter frequencyCounter = new FrequencyCounter("hamlet.txt");
            frequencyCounter.countFrequency();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
