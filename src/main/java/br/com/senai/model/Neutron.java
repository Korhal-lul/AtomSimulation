package br.com.senai.model;

import br.com.senai.controller.Pressed;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static processing.core.PApplet.*;

public class Neutron extends Particle {

    private static final float maxSpeed = (float) 12.5; //Na BR201
    private float x, y;
    private int id;
    private ArrayList<Particle> particles;
    private PApplet view;
    private int numBalls;
    private boolean hold = false;

    public Neutron(float xin, float yin, int id, ArrayList<Particle> particles, PApplet view) {
        this.x = xin;
        this.y = yin;
        this.id = id;
        this.particles = particles;
        this.view = view;

        this.vel = new PVector((float) Math.random() * maxSpeed * 2 - maxSpeed, (float) Math.random() * maxSpeed * 2 - maxSpeed);
        mass = 1.00866491588;
        radius = (float) 9.5;
    }

    public void update(ArrayList<Particle> particles) {
        this.particles = particles;
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
    public double getMass() {
        return mass;
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

        float angle = dir.heading();
        int pmouseX = view.pmouseX;
        int pmouseY = view.pmouseY;

        float targetX = pmouseX + cos(angle) * magnitude;
        float targetY = pmouseY + sin(angle) * magnitude;
        float ax = (targetX - x);
        float ay = (targetY - y);

        //Debug purposes
        /*String info = "Radians: " + (int) dir.heading() + " Sin = " + sin(angle) + " Cos = " + cos(angle) + "\nMagnitude: " + (int) magnitude;
        System.out.println(info);
        System.out.println("ax = " + ax + " ay = " + ay);
        System.out.println("pmouseX = " + view.pmouseX + " pmouseY = " + view.pmouseY);
        System.out.println("mouseX = " + view.mouseX + " mouseY = " + view.mouseY);*/

        vel.add(ax, ay);
    }

    public void collide() {
        for (int i = 0; i < numBalls; i++) {

            if(particles.get(i).getID() == id) continue;

            Particle other = particles.get(i);

            float targetX = other.getX();
            float targetY = other.getY();
            float distance = dist(x, y, targetX, targetY);
            float minDist = other.getRadius() + radius;

            if (distance < minDist) {
                double f1 = mass * vel.mag();
                double f2 = other.getMass() * other.getVel().mag();
                float mag = max(maxSpeed, (float) ((f1 + f2) / (mass + other.getMass())));

                PVector aux = vel;
                vel = other.getVel().sub(vel);
                vel.normalize().mult(mag);
                other.setVel(aux.sub(other.getVel()));
                other.vel.normalize().mult(mag);
            }
        }

        //Wall colision
        if (x + radius > view.width - 100) vel.x *= -1;
        else if (x - radius < 0) vel.x *= -1;
        if (y + radius > view.height) vel.y *= -1;
        else if (y - radius < 0) vel.y *= -1;
    }

    @Override
    public void strongForce(boolean moving) {
        if (!moving)return;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void move(boolean moving) {

        if (!moving)return;

        x += vel.x;
        y += vel.y;
    }

    @Override
    public void display(boolean moving) {
        collide();
        move(moving);
        view.ellipseMode(RADIUS);
        if (hold) view.stroke(255);
        else view.noStroke();
        view.fill(Color.lightGray.getRGB());
        view.ellipse(this.x, this.y, radius, radius);
    }

    @Override
    public float getRadius() {
        return radius;
    }
}