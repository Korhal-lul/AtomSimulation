package br.com.senai.model;

import processing.core.PVector;

public abstract class Particle {
    protected float x, y, diameter, vx, vy;
    public int id;

    public abstract void move();

    public abstract void display();

    public abstract void collide();

    public abstract void update(Particle[] particles, float gravity, float spring);

    public abstract void clicked(boolean is);

    public abstract void follow();

    public abstract boolean isHolden();

    public abstract float getX();

    public abstract float getY();

    public abstract void setX(float x);

    public abstract void setY(float y);

    public abstract void setVX(float vx);

    public abstract void setVY(float vy);

    public abstract float getVX();

    public abstract float getVY();

    public abstract float getDiameter();

    public abstract void inertia();

    public abstract void moveGravity();
}
