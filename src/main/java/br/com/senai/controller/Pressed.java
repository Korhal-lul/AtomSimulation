package br.com.senai.controller;

import static processing.core.PApplet.sqrt;

public class Pressed {

    public static boolean pointCircle(float mouseX, float mouseY, float x, float y, float radius) {

        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        float distX = mouseX - x;
        float distY = mouseY - y;
        float distance = sqrt( (distX*distX) + (distY*distY) );

        // if the distance is less than the circle's
        // radius the point is inside!
        return distance <= radius;
    }

    public static boolean pointRect(float mouseX, float mouseY, float x, float y, float width, float height){

        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;

    }

}
