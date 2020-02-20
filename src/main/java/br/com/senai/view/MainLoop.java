package br.com.senai.view;

import br.com.senai.model.Button;
import br.com.senai.model.Neutron;
import br.com.senai.model.Particle;
import br.com.senai.model.Proton;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class MainLoop extends PApplet {

    private int numBalls = 1;
    private float nuclearChange = 0;
    private double atomicMass = 0;
    private ArrayList<Particle> particles = new ArrayList<>();
    private Button btnProton;
    private Button btnElectron;
    private Button btnNeutron;

    public void settings() {
        size(1266, 700);
    }

    public void setup() {
        frameRate(60);

        for (int i = 0; i < numBalls; i++) {
            particles.add(new Proton(random(width - 100), random(height), i, particles, this));
        }

        btnProton = new Button(1220, 60, 20, this, new Color(100, 100, 237));
        btnElectron = new Button(1220, 120, 20, this, new Color(255, 70, 70));
        btnNeutron = new Button(1220, 180, 20, this, Color.LIGHT_GRAY);
    }

    public void draw() {
        background(0);

        stroke(255, 255, 255, 100);
        int step = 50;
        for (int i = 0; i < width / step + 1; i++) {
            line(i * step, 0, i * step, height);
            line(0, i * step, width, i * step);
        }

        DecimalFormat df = new DecimalFormat("#.#####");

        fill(255, 180);
        textSize(32);
        text("Z = " + nuclearChange, 10, 30);
        fill(255, 180);
        textSize(32);
        text("Mass = " + df.format(atomicMass) + " u", 10, 64);

        stroke(Color.WHITE.getRGB());
        fill(Color.BLACK.getRGB());
        rect(1166, 0, 144, 700);

        noStroke();
        btnProton.draw();
        btnElectron.draw();
        btnNeutron.draw();

        nuclearChange = 0;
        atomicMass = 0;
        Particle removePart = null;
        for (Particle particle : particles) {
            particle.update(particles);
            particle.display();
            if (particle.isHolden()) {
                particle.follow();
            }
            if (!particle.isHolden() && particle.getX() >= 1170) {
                removePart = particle;

            }
            if (particle instanceof Proton) {
                nuclearChange++;
            }

            atomicMass += particle.getMass();
        }
        if (particles != null)particles.remove(removePart);
    }

    @Override
    public void mousePressed() {
        int id;
        if (particles.size() == 0) {
            id = 1;
        } else {
            id = particles.get(particles.size() - 1).getID() + 1;
        }
        if (btnNeutron.clicked()) {

            Particle newNeutron = new Neutron(mouseX, mouseY, id, particles, this);
            newNeutron.clicked(true);
            particles.add(newNeutron);

            return;
        } else if (btnElectron.clicked()) {

            return;
        } else if (btnProton.clicked()) {
            Particle newProton = new Proton(mouseX, mouseY, id, particles, this);
            newProton.clicked(true);
            particles.add(newProton);
            return;
        }

        for (Particle particle : particles) {
            particle.clicked(true);

            if (particle.isHolden()) return;

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