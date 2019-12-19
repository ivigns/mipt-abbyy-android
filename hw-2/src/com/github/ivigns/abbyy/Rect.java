package com.github.ivigns.abbyy;

public class Rect {
    public Rect(Point tl, Point rb) {
        // Будем считать, что ось х направлена вправо, а ось у вниз
        if (tl.x >= rb.x || tl.y >= rb.y) {
            throw new IllegalArgumentException();
        }
        topLeft = tl;
        rightBottom = rb;
    }

    public final Point topLeft;
    public final Point rightBottom;
}
