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
    private int protons = 0, neutrons = 0, electrons = 0;
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
        surface.setResizable(true);

        for (int i = 0; i < numBalls; i++) {
            particles.add(new Proton(random(width - 100), random(height), i, particles, this));
        }

        btnProton = new Button(20, this, new Color(100, 100, 237));
        btnElectron = new Button(20, this, new Color(255, 70, 70));
        btnNeutron = new Button(20, this, Color.LIGHT_GRAY);
    }

    public void draw() {
        background(0);

        stroke(255, 255, 255, 60);
        int step = 50;
        for (int i = 0; i < width / step; i++) {
            fill(250, 150);
            if (!(i == 0)) {
                textSize(12);
                text(i + " fm", 1, (i + 1) * step);
            }

            textSize(12);
            text(i + " fm", i * step, 10);

            //X line
            line(i * step, 0, i * step, height);
            //Y line
            line(0, i * step, width, i * step);
        }

        DecimalFormat df = new DecimalFormat("#.#####");

        stroke(Color.WHITE.getRGB());
        fill(Color.BLACK.getRGB());
        rect(width - 120, 0, 119, height - 1);

        fill(250);
        textSize(18);
        text("Z = " + protons, width - 99, height - 60);
        textSize(18);
        text("Mass = " + df.format(atomicMass) + " u", 10, 46);
        textSize(18);
        text("P= " + protons, width - 99, 64);
        textSize(18);
        text("N= " + neutrons, width - 99, 184);

        noStroke();
        btnProton.draw(width - 25, 60);
        btnElectron.draw(width - 25, 120);
        btnNeutron.draw(width - 25, 180);

        atomicMass = 0;
        protons = 0;
        neutrons = 0;
        electrons = 0;
        Particle removePart = null;
        for (Particle particle : particles) {
            particle.update(particles);
            particle.display();
            if (particle.isHolden()) {
                particle.follow();
            }
            if (!particle.isHolden() && particle.getX() >= width - 100) {
                removePart = particle;

            }
            if (particle instanceof Proton) {
                protons++;
            }
            if (particle instanceof Neutron) {
                neutrons++;
            }
            atomicMass += particle.getMass();
        }
        if (particles != null) particles.remove(removePart);
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