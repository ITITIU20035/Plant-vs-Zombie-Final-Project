import javax.swing.*;
import java.awt.event.ActionEvent;

public class Zombie {

    public int health = 1000;
    public double speed = 1;

    private GameBG gp;

    public int posX = 1000;
    public int myLane;
    public boolean isMoving = true;

    public Zombie(GameBG parent,int lane){
        this.gp = parent;
        myLane = lane;
    }

    public void move(){
        if(isMoving) {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.colliders[i].indexPlant != null && gp.colliders[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.colliders[i];
                }
            }
            if (!isCollides) {
                if(slowInt>0){
                    if(slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                }else {
                    posX -= 1;
                }
            } else {
                collided.indexPlant.health -= 10;
                if (collided.indexPlant.health < 0) {
                    collided.removePlant();
                }
            }
            if (posX < 0) {
                isMoving = false;
                JOptionPane.showMessageDialog(gp,"ZOMBIES ATE YOUR BRAIN !" + '\n' + "Restarting game");
                Game.cleargame();
            }
        }
    }

    int slowInt = 0;
    public void slow(){
        slowInt = 1000;
    }
    //update zombie here
    public static Zombie getZombie(String type,GameBG parent, int lane) {
        Zombie z = new Zombie(parent,lane);
       switch(type) {
           case "NormalZombie" : z = new NormalZombie(parent,lane);
                                 break;
           case "ConeHeadZombie" : z = new TrafficConeZombie(parent,lane);
                                 break;
           case "FootballZombie" : z = new FootballZombie(parent,lane);
    }
       return z;
    }

}
