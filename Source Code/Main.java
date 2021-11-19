package com.company;

public class Main {

    public static void main(String[] args) {

        boolean operationActive = true; //this signals the beginning of our defense operation

        int currentLauncher = 1;// we start with the first launcher

        int handledThreats = 0;// declare our number of handled threats

        System.out.println("Missile defense system engaged...\nIron Dome is operational");
        //we create our radar and enemy objects as well as start their threads respectively
        Radar Radar = new Radar();

        Thread radarThread = new Thread(Radar);

        radarThread.start();

        Enemy theEmpire = new Enemy();

        Thread enemyThread = new Thread(theEmpire);

        enemyThread.start();
        //then we proceed to create each of our launchers and assign them their ID and start their threads
        Launcher l1 = new Launcher(1);
        Thread launcher1= new Thread(l1);
        launcher1.start();

        Launcher l2 = new Launcher(2);
        Thread launcher2= new Thread(l2);
        launcher2.start();

        Launcher l3 = new Launcher(3);
        Thread launcher3= new Thread(l3);
        launcher3.start();

        Launcher l4 = new Launcher(4);
        Thread launcher4= new Thread(l4);
        launcher4.start();
        //this is where we handle all the information until the operation has "ended"
        while (operationActive){

            Radar.setThreatDetected(theEmpire.isAttacking());//first our radar checks if theres an enemy attack

            if (Radar.isThreatDetected() && theEmpire.getAttacksToday() > 0 ){//if we find an enemy attack and the number of attacks the enemy has is more than 0 we act to intercept
                Radar.setTargetVector(theEmpire.getAngle(), theEmpire.getVelocity(), currentLauncher);//we get the angle and velocity of the projectile
                if (Radar.isNewT()){//then we validate this is indeed a new projectile and not one we have already detected
                    if (l1.getRemainingSalvo() > 1 && handledThreats < Radar.gettHandled() && !l1.isThreatDetected()){//to assign a launcher first we check its remainning salvo if the current threat needs to be handled and if this launcher doesnt have an assigned threat
                        currentLauncher = 1; //we assign the current launcher to the radar
                        l1.setThreatDetected(true);//mark the threat as detected on the launcher
                        l1.timeToIntercept(Radar.getAngle(), Radar.getVelocity());//then we pass the data from the radar to the launcher for calculations
                        handledThreats = Radar.gettHandled();//after that we mark the threat as handled
                    }//this process is repeated until we reach the end of each salvo then we move to the next launcher
                    else if (l2.getRemainingSalvo() > 1 && !l2.isThreatDetected()  && handledThreats < Radar.gettHandled()){
                        currentLauncher = 2;
                        l2.setThreatDetected(true);
                        l2.timeToIntercept(Radar.getAngle(), Radar.getVelocity());
                        handledThreats = Radar.gettHandled();
                    }
                    else if (l3.getRemainingSalvo() > 1 && !l2.isThreatDetected()  && handledThreats < Radar.gettHandled()){
                        currentLauncher = 3;
                        l3.setThreatDetected(true);
                        l3.timeToIntercept(Radar.getAngle(), Radar.getVelocity() );
                        handledThreats = Radar.gettHandled();

                    }
                    else if (l4.getRemainingSalvo() > 1 && !l2.isThreatDetected()  && handledThreats < Radar.gettHandled()){
                        currentLauncher = 4;
                        l4.setThreatDetected(true);
                        l4.timeToIntercept(Radar.getAngle(), Radar.getVelocity());
                        handledThreats = Radar.gettHandled();
                    }

                }
            }//after handilng the threat we check if our enemy has any attacks left in order to move on in case they dont or repeat the process in case they do
            if (theEmpire.getAttacksToday() <= 0 ){
                operationActive = false;//if they no longer have any left we mark the operation as over
                l1.setBarrageComplete(true);//then we mark the end of the barrage for each launcher
                l2.setBarrageComplete(true);
                l3.setBarrageComplete(true);
                l4.setBarrageComplete(true);
            }

        }
        enemyThread.interrupt();//once the threat has been handled we start terminating our threads
        launcher1.interrupt();
        launcher2.interrupt();
        launcher3.interrupt();
        launcher4.interrupt();
        Radar.setOperationActive(operationActive);//finally we deactivate the radar marking the end of the operation
        radarThread.interrupt();
        System.out.println("Attack over with "+ Radar.gettHandled() + " handled threats");//we show how many threats were handled by the radar

    }
}
