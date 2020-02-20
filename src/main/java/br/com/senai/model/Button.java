package br.com.senai.model;

import br.com.senai.controller.Pressed;
import processing.core.PApplet;

import java.awt.*;

public class Button {
    private float x;
    private float y;
    private int radius;
    private PApplet view;
    private Color color;

    public Button(float x, float y, int radius, PApplet view, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.view = view;
        this.color = color;
    }

    public void draw() {
        view.fill(color.getRGB());
        view.ellipse(x, y, radius, radius);
    }

    public boolean clicked() {
        return Pressed.pointCircle(view.mouseX, view.mouseY, x , y , radius);
    }
}
