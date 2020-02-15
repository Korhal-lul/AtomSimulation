package br.com.senai.main;

import br.com.senai.model.Particle;
import br.com.senai.model.Proton;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Hello world!
 */
public class App extends PApplet {

    int numBalls = 1;
    float spring = (float) 0.05;
    float gravity = 0;//(float) 0.01;
    float friction = (float) -0.9;
    Particle[] particles = new Particle[numBalls];

    public void settings() {
        size(1266, 720);
    }

    public void setup() {
        frameRate(60);
        for (int i = 0; i < numBalls; i++) {
            particles[i] = new Proton(random(width), random(height - 120), 50, i,
                    particles, this, spring, gravity, friction);
        }
        noStroke();
        fill(255, 180);

        changeGravity();
    }

    public void draw() {

        background(0);
        for (Particle particle : particles) {
            particle.update(particles, gravity, spring);
            particle.collide();
            particle.move();
            particle.display();
            if (particle.isHolden()) {
                particle.follow();
            }
        }
    }

    public void changeGravity() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gravity = gravity * -1;
                        changeGravity();
                    }
                },
                1000
        );
    }

    @Override
    public void mousePressed() {
        for (Particle particle : particles) {
            particle.clicked(true);
        }
    }

    @Override
    public void mouseReleased() {
        for (Particle particle : particles) {
            particle.clicked(false);
            particle.inertia();
        }
    }

}