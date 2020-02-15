package br.com.senai.model;

import br.com.senai.controller.Pressed;
import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import org.example.App;
import processing.core.PApplet;
import processing.core.PVector;

import static processing.core.PApplet.*;

public class Proton extends Particle {
    private float x, y;
    private float diameter;
    private float vx = 0;
    private float vy = 0;
    private float spring;
    private float gravity;
    private float friction;
    private int id;
    private Particle[] others;
    private PApplet view;
    private int numBalls;
    private boolean hold = false;

    public Proton(float xin, float yin, float din, int id, Particle[] particles, PApplet view, float spring, float gravity, float friction) {
        this.x = xin;
        this.y = yin;
        this.diameter = din;
        this.id = id;
        this.others = particles;
        this.gravity = gravity;
        this.friction = friction;
        this.view = view;
    }

    public void update(Particle[] particles, float gravity, float spring) {

        this.others = particles;
        this.numBalls = particles.length;
        this.gravity = gravity;
        this.spring = spring;
    }

    @Override
    public void clicked(boolean is) {

        if (Pressed.pointCircle(view.mouseX, view.mouseY, x, y, diameter / 2)) {
            hold = true;
        }
        if (!is) {
            hold = false;
        }
    }

    public void follow() {
        this.x = view.mouseX;
        this.y = view.mouseY;
    }

    @Override
    public boolean isHolden() {
        if (hold) {
            vx = 0;
            vy = 0;
        }
        return hold;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getVX() {
        return vx;
    }

    @Override
    public float getVY() {
        return vy;
    }

    @Override
    public float getDiameter() {
        return diameter;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setVX(float vx) {
        this.vx = vx;
    }

    @Override
    public void setVY(float vy) {
        this.vy = vy;
    }

    @Override
    public void inertia() {
        if (hold) return;

        PVector dir = PVector.sub(new PVector(view.mouseX, view.mouseY), new PVector(view.pmouseX, view.pmouseY));
        float magnitude = dir.mag();
        String info = "Degrees: " + (int) degrees(dir.heading()) + "\nMagnitude: " + (int) magnitude;
        System.out.println(info);

    }

    public void collide() {
        for (int i = 0; i < numBalls; i++) {
            if (id == i) break;
            float dx = others[i].getX() - x;
            float dy = others[i].getY() - y;
            float distance = sqrt(dx * dx + dy * dy);
            float minDist = others[i].getDiameter() / 2 + diameter / 2;
            if (distance < minDist) {
                float angle = atan2(dy, dx);
                float targetX = x + cos(angle) * minDist;
                float targetY = y + sin(angle) * minDist;
                float ax = (targetX - others[i].getX()) * spring;
                float ay = (targetY - others[i].getY()) * spring;
                vx -= ax;
                vy -= ay;
                others[i].setVX(others[i].getVX() + ax);
                others[i].setVY(others[i].getVY() + ay);
            }
        }
    }

    public void moveGravity() {
        /*Unused for now
        vy += gravity;
        x += vx;
        y += vy;

        }*/
    }

    public void move() {
        if (x + diameter / 2 > view.width) {
            x = view.width - diameter / 2;
            vx *= friction;
        } else if (x - diameter / 2 < 0) {
            x = diameter / 2;
            vx *= friction;
        }
        if (y + diameter / 2 > view.height) {
            y = view.height - diameter / 2;
            vy *= friction;
        } else if (y - diameter / 2 < 0) {
            y = diameter / 2;
            vy *= friction;
        }
    }

    public void display() {
        x += vx;
        y += vy;
        view.ellipse(this.x, this.y, diameter, diameter);
    }
}