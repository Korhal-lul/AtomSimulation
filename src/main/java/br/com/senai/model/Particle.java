package br.com.senai.model;

import processing.core.PVector;

import java.util.ArrayList;

public abstract class Particle {
    protected float x, y, friction, radius;
    protected PVector vel = new PVector(0, 0);
    public static double mass;
    public int id;


    public abstract void move(boolean moving);

    public abstract void display(boolean moving);

    public abstract void strongForce(boolean moving);

    public abstract void collide();

    public abstract void update(ArrayList<Particle> particles);

    public abstract float getRadius();

    public abstract void inertia();

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

    public abstract int getID();
}
