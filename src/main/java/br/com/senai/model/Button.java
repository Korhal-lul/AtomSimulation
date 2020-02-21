package br.com.senai.model;

import br.com.senai.controller.Pressed;
import processing.core.PApplet;

import java.awt.*;

public class Button {
    private float x;
    private float y;
    private int radius;
    private int width, height;
    private PApplet view;
    private Color color;

    public Button( int radius, PApplet view, Color color) {
        this.radius = radius;
        this.view = view;
        this.color = color;
    }

    public Button( int width, int height, PApplet view, Color color) {
        this.width = width;
        this.height = height;
        this.view = view;
        this.color = color;
    }

    public void draw(int x, int y) {
        this.x = x;
        this.y = y;

        view.fill(color.getRGB());
        if (radius != 0) {
            view.ellipse(x, y, radius, radius);
        } else view.rect(x, y, width, height);
    }

    public boolean clicked() {
        return Pressed.pointCircle(view.mouseX, view.mouseY, x, y, radius);
    }
}
