package br.com.senai.model;

import br.com.senai.controller.Pressed;
import br.com.senai.controller.Time;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.*;

public class Button {
    private float x;
    private float y;
    private int radius;
    private int width, height;
    private PApplet view;
    private Color color;
    private PImage img;
    private boolean toggle = false;

    public Button(int radius, PApplet view, Color color) {
        this.radius = radius;
        this.view = view;
        this.color = color;
    }

    public Button(int width, int height, PApplet view, Color color) {
        this.width = width;
        this.height = height;
        this.view = view;
        this.color = color;
    }

    public Button(int width, int height, PApplet view, PImage img) {
        this.width = width;
        this.height = height;
        this.view = view;
        this.img = img;
    }

    public void draw(int x, int y) {
        this.x = x;
        this.y = y;

        if (color != null)
            view.fill(color.getRGB());

        if (radius != 0) {
            view.ellipse(x, y, radius, radius);
        } else if (img == null) {
            view.rect(x, y, width, height);
        } else {
            view.image(img, x, y, width, height);
        }

    }

    public void setImg(PImage img){
        this.img = img;
    }

    public boolean clicked() {
        if (radius != 0)
            return Pressed.pointCircle(view.mouseX, view.mouseY, x, y, radius);
        else return Pressed.pointRect(view.mouseX, view.mouseY, x, y, width, height);
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }
}
