package br.com.senai.main;

import br.com.senai.model.Particle;
import br.com.senai.model.Proton;
import processing.core.PApplet;
import processing.core.PVector;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class MainLoop extends PApplet {

    private int numBalls = 50;
    private float nuclearChange = 0;
    private double atomicMass = 0;
    private ArrayList<Particle> particles = new ArrayList<>();

    public void settings() {
        size(1266, 720);
    }

    public void setup() {
        frameRate(60);

        for (int i = 0; i < numBalls; i++) {
            particles.add(new Proton(i, particles, this));
        }
        noStroke();

    }

    public void draw() {
        nuclearChange = 0;
        atomicMass = 0;
        background(0);
        for (Particle particle : particles) {
            particle.update(particles);
            particle.display();
            if (particle.isHolden()) {
                particle.follow();
            }

            if (particle instanceof Proton) {
                nuclearChange++;
                atomicMass += particle.getMass();
            }
        }

        DecimalFormat df = new DecimalFormat("#.#####");

        fill(255, 180);
        textSize(32);
        text("Z = " + nuclearChange, 10, 30);
        fill(255, 180);
        textSize(32);
        text("Mass = " + df.format(atomicMass), 10, 64);
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