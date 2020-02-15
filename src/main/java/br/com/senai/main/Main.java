package br.com.senai.main;

import processing.core.PApplet;

public class Main {
    public static void main(String[] args) {
        App ml = new App();
        PApplet.runSketch(new String[]{"AtomSimulator"}, ml);
    }
}
