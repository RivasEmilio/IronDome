package com.company;

public class Radar implements Runnable{
    //here in the Radar we handle most information without any calculation and use it to relay from the enemy to the launcher
    public boolean threatDetected;//threat indicator
    public boolean operationActive;//this indicates if the operation is still ongoing
    public int assignLauncher;//the current assigned launcher
    public double angle;//the projectile angle
    public double velocity;// the projectile velocity
    public double lastAngle=0;//our validators to ensure we are not operating on the same threat
    public double lastVel=0;
    public boolean newT = true;//this signal that this is a new operator
    public int tHandled=0;//how many threats have been handled

    public Radar(){//when we create our object we set the op to active and the launcher to 1
        operationActive = true;
        assignLauncher = 1;
    }

    public synchronized void setTargetVector(double angle, double velocity, int assignLauncher){
        //here we set the values for our target vector and we validate if its already being handled
        this.angle = angle;
        this.velocity = velocity;
        this.assignLauncher = assignLauncher;

        if(lastAngle != angle && lastVel!= velocity){
            this.newT = true;
        }else{
            this.newT = false;
        }
    }

    public synchronized double getAngle() {
        return angle;
    }

    public synchronized double getVelocity() {
        return velocity;
    }

    public synchronized void setOperationActive(boolean operationActive) {
        this.operationActive = operationActive;
    }

    public synchronized void setThreatDetected(boolean threatDetected) {
        this.threatDetected = threatDetected;
    }

    public boolean isThreatDetected() {
        return threatDetected;
    }

    public boolean isNewT() {
        return newT;
    }

    public int gettHandled() {
        return tHandled;
    }

    @Override
    public void run() {

        System.out.println("Radar Operational, scanning for threats");

        while (operationActive){//our thread will run until the operation has been deemed as over

            if(threatDetected && lastAngle != angle && lastVel!= velocity){//if this is a new threat we act to stop it and signal the laucher
                System.out.println("Threat detected! reported velocity: "+velocity+" with and angle of "+angle+" degrees");
                System.out.println("Launcher "+assignLauncher+" to intercept");
                lastAngle = angle;
                lastVel = velocity;
                newT = true;
                threatDetected = false;
                tHandled++;
            }//this sleep function helps simulate delay between attacks
            try {
                int sleep = (int) ((Math.random() * (20 - 10)) + 10);// thread sleeps for 20ms to represent delay
                threatDetected = false;
                Thread.sleep(sleep);

            } catch (InterruptedException e) {

                System.out.println("Radar shutting down...");
                operationActive = false;
                return;
            }

        }
        System.out.println("Radar shutting down...");

    }
}
