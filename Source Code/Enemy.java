package com.company;

public class Enemy implements Runnable{
    //here we represent the enemy entity
    public boolean attacking;//this marks whether he's attacking or not
    public double angle;//this represents the angle of the projectile
    public double velocity;//this represents the velocity of the projectile
    public int attacksToday;//this holds how many attacks it will launch today

    public Enemy(){
        int total = (int) ((Math.random() * (80 - 10)) + 10);//when we create our enemy object we designate a random range of attacks from 10 to 80
        this.attacksToday = total;
    }

    public synchronized boolean isAttacking() {
        return attacking;
    }

    public synchronized double getAngle() {
        return angle;
    }

    public synchronized double getVelocity() {
        return velocity;
    }

    public synchronized  int getAttacksToday() {
        return attacksToday;
    }

    @Override
    public void run() {

        System.out.println("Enemy is ready to attack!!! with " +attacksToday+" attacks");

        while (attacksToday > 0){//our thread will run as long as we have attacks left
            attacking = true;
            angle = ((Math.random() * (90 - 10)) + 10);//here we randomly assign values to the projectile properties
            velocity = ((Math.random() * (720 - 100)) + 100);//thus enabling us to have a wide range of scenarios
            attacksToday--;
            try {//this represents delay in the system
                int sleep = (int) ((Math.random() * (200 - 100)) + 100);
                Thread.sleep(sleep);

            } catch (InterruptedException e) {

                System.out.println("Enemy stands down");
                attacksToday = 0;
                return;
            }
            attacking = false;
        }
    }
}
