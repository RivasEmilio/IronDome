package com.company;

public class Launcher implements Runnable {
    //here we do the calculations for the missile with the processed info from the radar
    private int ID;//the launcher id
    public int remainingSalvo;//our remaining missiles
    public boolean barrageComplete;//the flag to indicate the end of the barrage
    public boolean threatDetected;//detection of a threat
    public double timeI = 0 ;// time to intercept
    public double yInt;// interception point on the Y axis
    public double xInt;//interception point on the X axis


    public Launcher(int ID){//when we create the launcher we assign its ID as well as set an initial salvo and mark the barrage as ongoing
        this.ID = ID;
        this.remainingSalvo = 20;
        this.barrageComplete = false;
        this.threatDetected = false;
    }

    public synchronized void setThreatDetected(boolean threatDetected) {
        this.threatDetected = threatDetected;
    }

    public synchronized void setBarrageComplete(boolean barrageComplete) {
        this.barrageComplete = barrageComplete;
    }

    public synchronized int getRemainingSalvo() {
        return remainingSalvo;
    }
    //here we calculate each of the elements to indicate the point of interception of the missile
    //things such as the angle and the velocity are not mentioned because they are the same
    public synchronized void timeToIntercept(double angle, double velocity){

        double radians = Math.toRadians(angle);
        //here we use the projectile motion formulas mentioned in the document
        this.timeI = ((2* velocity) * Math.sin(radians))/9.81;

        this.yInt =  ((velocity*velocity) * (Math.sin(radians)*Math.sin(radians)))/ 2 * 9.81;

        this.xInt =  ((velocity*velocity) * Math.sin(2*radians))/ 9.81;
    }

    public boolean isThreatDetected() {
        return threatDetected;
    }

    @Override
    public void run() {

        System.out.println("Launcher " +ID+ " is now active");

        while (!barrageComplete){//our thread will run until the barrage is marked as over
            if (remainingSalvo > 1  && threatDetected){//if we still have ammo and theres a threat detected we fire a missile
                remainingSalvo--;
                System.out.println("Launcher " +ID+ " firing, remaining salvo is " +remainingSalvo);
                //here we represent the launched missile as a print to show how each launcher handles a missile
                System.out.println("Missile " +(ID * 20 - remainingSalvo)+ " in the air interception coordinates: " +xInt+"X, "+ yInt +"Y");
                threatDetected = false;
            }
            //should our remaining salvo be 0 we shut down the launcher
            if (remainingSalvo <=0){
                barrageComplete = true;
                System.out.println("Launcher " +ID+ " is empty");
            }
            //this sleep represents delay in the system
            try {
                int sleep = (int) ((Math.random() * (20 - 10)) + 10);// thread sleeps for 20ms to represent delay

                Thread.sleep(sleep);

            } catch (InterruptedException e) {

                System.out.println("Launcher " +ID+ " shutting down");
                barrageComplete = true;
                return;
            }

        }

        if (remainingSalvo <=0){

            System.out.println("Launcher " +ID+ " is empty");
        }

        System.out.println("Launcher " +ID+ " shutting down");

    }
}
