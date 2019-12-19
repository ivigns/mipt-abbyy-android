package com.github.ivigns.abbyy;

public class Util {
    private Util() {}

    public static int getPerimeter(Rect rect) {
        return 2 * (rect.rightBottom.x - rect.topLeft.x + rect.rightBottom.y - rect.topLeft.y);
    }

    public static int getArea(Rect rect) {
        return (rect.rightBottom.x - rect.topLeft.x) * (rect.rightBottom.y - rect.topLeft.y);
    }

    /**
     * @param rect  Изначальный прямоугольник
     * @param scale Коэффициент увеличения
     * @return      Прямоугольник с таким же левым верхним углом, но со сторонами, уеличеными в scale раз
     */
    public static Rect scaleBy(Rect rect, int scale) {
        Point newRightBottom = new Point(rect.topLeft.x + scale * (rect.rightBottom.x - rect.topLeft.x), rect.topLeft.y + scale * (rect.rightBottom.y - rect.topLeft.y));
        return new Rect(rect.topLeft, newRightBottom);
    }
}
