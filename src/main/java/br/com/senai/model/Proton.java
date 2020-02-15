package br.com.senai.model;

import br.com.senai.controller.Pressed;
import processing.core.PApplet;
import processing.core.PVector;

import static processing.core.PApplet.*;

public class Proton extends Particle {

    private static final float radius = 40;
    private static final float speedControl = (float) 0.05;
    private static final float maxSpeed = (float) 120; //Na BR201
    private float x, y;
    private float gravity;
    private float friction;
    private int id;
    private Particle[] others;
    private PApplet view;
    private int numBalls;
    private boolean hold = false;
    private boolean released = false;

    public Proton(float xin, float yin, int id, Particle[] particles, PApplet view, float gravity, float friction) {
        this.x = xin;
        this.y = yin;
        this.id = id;
        this.others = particles;
        this.gravity = gravity;
        this.friction = friction;
        this.view = view;

        this.vel = new PVector((float) Math.random() * maxSpeed * 2 - maxSpeed, (float) Math.random() * maxSpeed * 2 - maxSpeed);
        System.out.println();
    }

    public void update(Particle[] particles, float gravity) {
        this.others = particles;
        this.numBalls = particles.length;
        this.gravity = gravity;
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
            vel = new PVector(0, 0);
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
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public PVector getVel() {
        return vel;
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

        vel.add(ax, ay);
    }

    public void collide() {
        for (int i = id + 1; i < numBalls; i++) {
            float otherTargetX = others[i].getX();
            float otherTargetY = others[i].getY();

            System.out.println(x + " " + y);
            System.out.println(otherTargetX + " " + otherTargetY);

            float distance = dist(x, y, otherTargetX, otherTargetY);
            float minDist = others[i].getRadius() + radius;

            if (distance < minDist) {
                System.out.println("Colision");
                float angle = atan2(otherTargetY - y, otherTargetX - x);
                PVector target = new PVector(x + cos(angle) * minDist, y + sin(angle) * minDist);
                float ax = (target.x - others[i].getX() * speedControl);
                float ay = (target.y - others[i].getY() * speedControl);
                vel.sub(ax, ay);

                others[i].setVel(others[i].getVel().add(ax, ay));
            }
        }
        //MOVE
        if (x + radius > view.width) {
            x = view.width - radius;
            vel.x *= friction;
        } else if (x - radius < 0) {
            x = radius;
            vel.x *= friction;
        }
        if (y + radius > view.height) {
            y = view.height - radius;
            vel.y *= friction;
        } else if (y - radius < 0) {
            y = radius;
            vel.y *= friction;
        }
    }

    public void moveGravity() {
        /*Unused for now
        vy += gravity;
        x += vx;
        y += vy;

        }*/
    }

    @Override
    public void move() {

    }

    public void display() {
        x += vel.x;
        y += vel.y;
        view.ellipseMode(RADIUS);
        view.ellipse(this.x, this.y, radius, radius);
    }

    @Override
    public float getRadius() {
        return radius;
    }
}