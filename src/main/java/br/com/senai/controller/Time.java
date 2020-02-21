package br.com.senai.controller;

import br.com.senai.model.Particle;
import processing.core.PVector;

import java.util.ArrayList;

class ParticleVel {
    public PVector velocity;
    public int id;

    ParticleVel(PVector velocity, int id) {
        this.velocity = velocity;
        this.id = id;
    }
}

public class Time {

    private static ArrayList<ParticleVel> velocities;

    public static void pause(ArrayList<Particle> particles) {
        //Saves the current array of velocities
        velocities = new ArrayList<>();


        for (int i = 0; i < particles.size(); i++) {

            PVector zero = new PVector(0, 0);

            if (particles.get(i).getVel() == zero) break;

            try {
                velocities.get(i).velocity = particles.get(i).getVel();
            } catch (IndexOutOfBoundsException e) {
                velocities.add(new ParticleVel(particles.get(i).getVel(), particles.get(i).getID()));
            }
            particles.get(i).setVel(zero);
        }

    }

    public static void unpause(ArrayList<Particle> particles) {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).getVel().equals(new PVector(0, 0))) {
                try {
                    if (particles.get(i).getID() == velocities.get(i).id)
                        particles.get(i).setVel(velocities.get(i).velocity);
                } catch (IndexOutOfBoundsException e) {
                    particles.get(i).setVel(particles.get(i).getVel());
                }
            }
        }
    }

}

