package br.com.senai.main;

import processing.core.PApplet;

public class Main {
    public static void main(String[] args) {
        MainLoop ml = new MainLoop();
        PApplet.runSketch(new String[]{"AtomSimulator"}, ml);
    }
}
