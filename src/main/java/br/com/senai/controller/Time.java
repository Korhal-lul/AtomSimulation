package br.com.senai.controller;

import br.com.senai.model.Particle;
import processing.core.PVector;

import java.util.ArrayList;

public class Time {

    protected static ArrayList<PVector> velocities;

    public static void pause(ArrayList<Particle> particles) {
        //Saves the current array of velocities

        velocities = new ArrayList<>();

        for (int i = 0; i < particles.size(); i++) {

            PVector zero = new PVector(0, 0);

            if (particles.get(i).getVel() == zero) break;

            try {
                velocities.get(i).set(particles.get(i).getVel());
            }catch (IndexOutOfBoundsException e){
                velocities.add(particles.get(i).getVel());
            }
            particles.get(i).setVel(zero);
        }

    }

    public static void unpause(ArrayList<Particle> particles) {
        for (int i = 0; i < particles.size(); i++) {
            if (!particles.get(i).getVel().equals(new PVector(0, 0))) {
                particles.get(i).setVel(particles.get(i).getVel());
            } else {
                try {
                    particles.get(i).setVel(velocities.get(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    particles.get(i).setVel(particles.get(i).getVel());
                }
            }
        }
    }

}
