package br.com.senai.main;

import br.com.senai.model.Particle;
import br.com.senai.model.Proton;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Hello world!
 */
public class MainLoop extends PApplet {

    int numBalls = 10;
    float spring = (float) 0.05;
    float gravity = 0;//(float) 0.01;
    float friction = (float) -0.9;
    float atomicMass = 0;
    float nuclearChange = 0;
    Particle[] particles = new Particle[numBalls];

    public void settings() {
        size(1266, 720);
    }

    public void setup() {
        frameRate(60);

        for (int i = 0; i < numBalls; i++) {
            particles[i] = new Proton(random(width), random(height - 120), i,
                    particles, this, spring, gravity, friction);
        }
        noStroke();
        fill(255, 180);

    }

    public void draw() {
        nuclearChange = 0;
        background(0);
        for (Particle particle : particles) {
            particle.update(particles, gravity, spring);
            particle.collide();
            particle.move();
            particle.display();
            if (particle.isHolden()) {
                particle.follow();
            }

            if (particle instanceof Proton) {
                nuclearChange++;
            }
        }

        textSize(32);
        text("Z = " + nuclearChange, 10, 30);
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
            if (particle.isHolden()) {
                particle.clicked(false);
                particle.inertia();
            }
        }
    }

}