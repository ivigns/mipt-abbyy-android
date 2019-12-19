package com.github.ivigns.abbyy;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class FrequencyCounter {
    public FrequencyCounter(String inputFileName) throws IOException {
        in = new BufferedReader(new FileReader(inputFileName));
        out = new BufferedWriter(new FileWriter("output.txt"));
        charMap = new TreeMap<>();
    }

    public void countFrequency() throws IOException {
        char[] buf = new char[1];
        while (in.read(buf) != -1) {
            if (!charMap.containsKey(buf[0])) {
                charMap.put(buf[0], 0L);
            }
            charMap.put(buf[0], charMap.get(buf[0]) + 1L);
        }

        for (Map.Entry<Character, Long> entry : charMap.entrySet()) {
            out.write(entry.getKey() + " " + entry.getValue() + "\n");
            out.flush();
        }
        out.close();
    }

    BufferedReader in;
    BufferedWriter out;
    private Map<Character, Long> charMap;
}
