package br.com.senai.model;

import br.com.senai.controller.Pressed;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class Proton extends Particle {

    private static final float speedControl = (float) 0.09;
    private static final float maxSpeed = (float) 120; //Na BR201

    private ArrayList<Particle> others;
    private PApplet view;
    private int numBalls;
    private boolean hold = false;

    public Proton(float xin, float yin, int id, ArrayList particles , PApplet view, float friction) {
        this.x = xin;
        this.y = yin;
        this.id = id;
        this.others = particles;
        this.friction = friction;
        this.view = view;
        mass = 1.00727647;
        radius = 8;
    }

    public void update(ArrayList particles) {
        this.others = particles;
        this.numBalls = particles.size();
    }

    @Override
    public void clicked(boolean is) {
        if (Pressed.pointCircle(view.mouseX, view.mouseY, x, y, radius)) {
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
    public void inertia() {
        if (hold) return;

        PVector dir = PVector.sub(new PVector(view.mouseX, view.mouseY), new PVector(view.pmouseX, view.pmouseY));

        float magnitude = dir.mag();

        float angle = degrees(dir.heading());
        int pmouseX = view.pmouseX;
        int pmouseY = view.pmouseY;

        float targetX = pmouseX + cos(angle) * magnitude;
        float targetY = pmouseY + sin(angle) * magnitude;
        float ax = (targetX - x);
        float ay = (targetY - y);

        /*Debug purposes
        String info = "Degrees: " + (int) degrees(dir.heading()) + "\nMagnitude: " + (int) magnitude;
        System.out.println(info);
        System.out.println("ax = " + ax + " ay = " + ay + " Spring " + spring);
        System.out.println("pmouseX = " + view.pmouseX + " pmouseY = " + view.pmouseY);
        */

        vx += ax;
        vy += ay;

    }

    public void collide() {

        for (int i = id; i < numBalls; i++) {
            float otherTargetX = others.get(i).getX() + others.get(i).getVX();
            float otherTargetY = others.get(i).getY() + others.get(i).getVY();
            float dx = otherTargetX - x;
            float dy = otherTargetY - y;
            float distance = sqrt(dx * dx + dy * dy);
            float minDist = others.get(i).getRadius() + radius;
            if (distance < minDist) {
                float angle = atan2(dy, dx);
                float targetX = x + cos(angle) * minDist;
                float targetY = y + sin(angle) * minDist;
                float ax = (targetX - others.get(i).getX()) * speedControl;
                float ay = (targetY - others.get(i).getY()) * speedControl;
                vx -= ax;
                vy -= ay;
                others.get(i).setVX(others.get(i).getVX() + ax);
                others.get(i).setVY(others.get(i).getVY() + ay);
            }
        }
        //MOVE
        if (x + radius > view.width) {
            x = view.width - radius;
            vx *= friction;
        } else if (x - radius < 0) {
            x = radius;
            vx *= friction;
        }
        if (y + radius > view.height) {
            y = view.height - radius;
            vy *= friction;
        } else if (y - radius < 0) {
            y = radius;
            vy *= friction;
        }
    }

    public void move() {

    }

    public void display() {
        x += vx;
        y += vy;
        view.ellipseMode(RADIUS);
        if(hold){
            view.stroke(255);
        }else {
            view.noStroke();
        }
        view.fill(255, 75, 75);
        view.ellipse(this.x, this.y, radius, radius);
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
    public double getMass() {
        return mass;
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
    public void setVX(float vx) {
        this.vx = vx;
    }

    @Override
    public void setVY(float vy) {
        this.vy = vy;
    }

    @Override
    public float getRadius() {
        return radius;
    }
}