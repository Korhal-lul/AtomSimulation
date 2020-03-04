package br.com.senai.controller;

import br.com.senai.model.Particle;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.PI;

public class Collision {

    public static void collide(PApplet view, ArrayList<Particle> particles, Particle particle) {
        int id = particle.getID();
        float radius = particle.getRadius();
        float x = particle.getX();
        float y = particle.getY();
        double mass = particle.getMass();
        PVector vel = particle.getVel();


        for (int i = id + 1; i < particles.size(); i++) {

            Particle other = particles.get(i);

            float targetX = other.getX() + other.getRadius();
            float targetY = other.getY() + other.getRadius();
            float distance = dist(x + radius, y + radius, targetX, targetY);
            float minDist = other.getRadius() + radius;

            if (distance < minDist) {

                float atrito = (float) 1; //Just to not create a forever loop
                float angle1 = atan2(vel.y, vel.x);
                float angle2 = atan2(other.getVel().y, other.getVel().x);
                float contactAng = atan2(other.getY() - y, other.getX() - x);

                double v1x = (vel.mag() * cos(angle1 - contactAng) * (mass - other.getMass())
                        + (2 * other.getMass()) * other.getVel().mag() * cos(angle2 - contactAng)) / (mass + other.getMass())
                        * cos(contactAng) + vel.mag() * sin(angle1 - contactAng) * cos(contactAng + (PI / 2));
                double v1y = (vel.mag() * cos(angle1 - contactAng) * (mass - other.getMass())
                        + (2 * other.getMass()) * other.getVel().mag() * cos(angle2 - contactAng)) / (mass + other.getMass())
                        * sin(contactAng) + vel.mag() * sin(angle1 - contactAng) * sin(contactAng + (PI / 2));
                double v2x = (other.getVel().mag() * cos(angle2 - contactAng) * (other.getMass() - mass)
                        + (2 * mass) * vel.mag() * cos(angle1 - contactAng)) / (other.getMass() + mass)
                        * cos(contactAng) + other.getVel().mag() * sin(angle2 - contactAng) * cos(contactAng + (PI / 2));
                double v2y = (other.getVel().mag() * cos(angle2 - contactAng) * (other.getMass() - mass)
                        + (2 * mass) * vel.mag() * cos(angle1 - contactAng)) / (other.getMass() + mass)
                        * sin(contactAng) + other.getVel().mag() * sin(angle2 - contactAng) * sin(contactAng + (PI / 2));

                vel.x = (float) v1x * atrito;

                System.out.println(v1x + "   " + atrito);

                vel.y = (float) v1y * atrito;

                other.getVel().x = (float) v2x * atrito;

                other.getVel().y = (float) v2y * atrito;


                /*Machado
                double f1 = mass * vel.mag();
                double f2 = other.getMass() * other.getVel().mag();

                float mag = max(maxSpeed, (float) ((f1 + f2) / (mass + other.getMass())));

                PVector aux = vel;
                vel = other.getVel().sub(vel);
                vel.normalize().mult(mag);
                other.setVel(aux.sub(other.getVel()));
                other.vel.normalize().mult(mag);*/
            }
        }

        //Wall colision
        if (x + radius > view.width - 120) vel.x *= -1;
        else if (x - radius < 0) vel.x *= -1;
        if (y + radius > view.height) vel.y *= -1;
        else if (y - radius < 0) vel.y *= -1;

    }
}
