package br.com.senai.model;

import processing.core.PVector;

public abstract class Particle {
    protected float x, y, friction, radius;
    protected PVector vel = new PVector(0, 0);
    public static double mass;
    public int id;

    public abstract void move();

    public abstract void display();

    public abstract void collide();

    public abstract void update(Particle[] particles);

    public abstract void clicked(boolean is);

    public abstract void follow();

    public abstract boolean isHolden();

    public abstract float getX();

    public abstract float getY();

    public abstract double getMass();

    public abstract PVector getVel();

    public void setVel(PVector vel) {
        this.vel = vel;
    }

    public abstract float getRadius();

    public abstract void inertia();
}
