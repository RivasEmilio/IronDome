package com.company;

public class Missile implements Runnable{

    public int id;
    public double time;
    public double enemyPosX;
    public double enemyPosY;

    public Missile(double enemyPosX, double enemyPosY, int id, double time){

        this.enemyPosX = enemyPosX;
        this.enemyPosY = enemyPosY;
        this.time = time;
        this.id = id;

    }

    @Override
    public void run() {

        System.out.println("missile " +id+ " is now in the air");

        try {
            Thread.sleep((long)time);

        } catch (InterruptedException e) {

            System.out.println("missile malfunction");
            return;
        }

        System.out.println("missile " +id+ " has intercepted its target at: " +enemyPosX+ "x "+enemyPosY+"y");

    }
}
